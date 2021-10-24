package rad.mining.ethermine.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rad.mining.ethermine.api.CurrentStatsAPI;
import rad.mining.ethermine.api.CurrentStatsAPIData;
import rad.mining.ethermine.api.MinerAPI;
import rad.mining.ethermine.api.StatisticAPI;
import rad.mining.ethermine.api.WorkerDataAPI;
import rad.mining.ethermine.api.WorkerHistoryAPI;
import rad.mining.ethermine.domain.HashRateInfo;
import rad.mining.ethermine.domain.MinerHistory;
import rad.mining.ethermine.domain.ShareInfo;
import rad.mining.ethermine.domain.WorkerHistory;
import rad.mining.ethermine.helper.JSONHelper;
import rad.mining.ethermine.repository.MinerHistoryRepository;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j

public class EthermineApiService {
	@Value( "${rad.ethermine.baseAPI}" )
	private String baseUrl;
	@Value( "${rad.ethermine.wallet}" )
	private String wallet;

	@Autowired
	private JSONHelper jsonHelper;

	@Autowired MinerHistoryRepository repository;

	@Scheduled(fixedDelay = 60000)
	public MinerHistory updateData() throws JsonProcessingException {
		try{
		log.info("updateData");
		String url = baseUrl+"/miner/"+wallet+"/currentStats";
		String result = jsonHelper.getRequest(url);
		ObjectMapper objectMapper = new ObjectMapper();
		CurrentStatsAPIData data = objectMapper.readValue(result, CurrentStatsAPI.class)
				.getData();
		Timestamp timestamp = Timestamp.from(Instant.ofEpochSecond(data.getTime()));
		Optional<MinerHistory> optional = repository.findByTimestamp(timestamp);
		if(optional.isPresent())
		{
			log.info("There is already data for {} not updating.", timestamp.toString());
			return null;
		}
		HashRateInfo h = new HashRateInfo(data.getReportedHashrate(), data.getCurrentHashrate());
		ShareInfo s = new ShareInfo(data.getValidShares(), data.getInvalidShares(), data.getStaleShares());
		Set<WorkerHistory> workerHistorySet = new HashSet<>();

		String workersURL = baseUrl+"/miner/"+wallet+"/workers";
		String workerResult = jsonHelper.getRequest(workersURL);
		List<WorkerDataAPI> workerDataAPIList = objectMapper.readValue(workerResult, WorkerHistoryAPI.class).getData();
		MinerHistory toSave = MinerHistory.builder().hashRateInfo(h).shareInfo(s).timestamp(timestamp)
				.unpaid(data.getUnpaid()).unconfirmed(data.getUnconfirmed())
				.coinsPerMin(data.getCoinsPerMin()).usdPerMin(data.getUsdPerMin()).btcPerMin(data.getBtcPerMin())
				.build();
		for(WorkerDataAPI worker : workerDataAPIList)
		{
			log.info("creating info for worker {}.", worker.getWorker());
			HashRateInfo wh = new HashRateInfo(worker.getReportedHashrate(), worker.getCurrentHashrate());
			ShareInfo ws = new ShareInfo(worker.getValidShares(), worker.getInvalidShares(), worker.getStaleShares());
			workerHistorySet.add(WorkerHistory.builder().minerHistory(toSave).hashRateInfo(wh).shareInfo(ws).worker(worker.getWorker()).build());
		}

		toSave.setWorkerHistorySet(workerHistorySet);

		return repository.save(toSave);


		} catch (Exception e) {
			log.error("Error in updateData. {}",
					e.getCause());
			return null;
		}
	}

	public void minerEndpoint() throws IOException {
		String url = baseUrl+"/miner/"+wallet+"/dashboard";

		String result = jsonHelper.getRequest(url);
		ObjectMapper objectMapper = new ObjectMapper();
		MinerAPI m = objectMapper.readValue(result, MinerAPI.class);

		Set<String> workers = m.getData().getWorkerAPIS().stream().map(w -> w.getWorker()).collect(Collectors.toSet());
		Map<String, List<WorkerDataAPI>> workerDataMap = new HashMap<>();
		for(String worker : workers)
		{
			String workerUrl =  baseUrl+"/miner/"+wallet+"/worker/"+worker+"/history";
			String workerResult = jsonHelper.getRequest(workerUrl);
			WorkerHistoryAPI workerHistoryAPI = objectMapper.readValue(workerResult, WorkerHistoryAPI.class);
			workerDataMap.put(worker, workerHistoryAPI.getData());
		}
		for(StatisticAPI data : m.getData().getStatisticAPIS())
		{
			HashRateInfo h = new HashRateInfo(data.getReportedHashrate(), data.getCurrentHashrate());
			ShareInfo s = new ShareInfo(data.getValidShares(), data.getInvalidShares(), data.getStaleShares());
			Timestamp timestamp = Timestamp.from(Instant.ofEpochSecond(data.getTime()));
			Optional<MinerHistory> optional = repository.findByTimestamp(timestamp);
			MinerHistory minerHistory = null;
			if(optional.isEmpty()) {
				log.info("saving new history");
				minerHistory =
						repository.save(MinerHistory.builder().hashRateInfo(h).shareInfo(s).timestamp(timestamp).build());
			}
			else
			{
				minerHistory = optional.get();
			}
			for(String worker : workerDataMap.keySet())
			{
				List<WorkerDataAPI> workerDataAPIList = workerDataMap.get(worker);
				List<WorkerDataAPI> ans = workerDataAPIList.stream().filter(w -> w.getTime() == data.getTime())
						.collect(Collectors.toList());
				if(ans.size() > 1)
				{
					log.error("Worker {} had {} values for {}.", worker, ans.size(),data.getTime() );
				}
				if(ans.size() == 1)
				{
					WorkerDataAPI workerDataAPI = ans.get(0);
					HashRateInfo wh = new HashRateInfo(workerDataAPI.getReportedHashrate(), workerDataAPI.getCurrentHashrate());
					ShareInfo ws = new ShareInfo(workerDataAPI.getValidShares(), workerDataAPI.getInvalidShares(), workerDataAPI.getStaleShares());
					log.info("adding miner history for {} on {}.)", worker, data.getTime());
					minerHistory.addWorkerHistory(WorkerHistory.builder().worker(worker).hashRateInfo(wh).shareInfo(ws).minerHistory(minerHistory).build());
					repository.save(minerHistory);
					log.info("resaving data");
				}
			}
		}
	}
}
