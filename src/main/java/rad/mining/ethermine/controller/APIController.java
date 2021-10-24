package rad.mining.ethermine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rad.mining.ethermine.domain.MinerHistory;
import rad.mining.ethermine.domain.Payload;
import rad.mining.ethermine.service.EthermineApiService;
import rad.mining.ethermine.util.PayloadUtil;

@RestController
@RequestMapping("/api/ethermine")
@AllArgsConstructor
@Slf4j
public class APIController {
	@Autowired EthermineApiService service;

	@GetMapping("")
	public ResponseEntity<Payload> minerEndpoint() {
		try {
			service.minerEndpoint();

			return ResponseEntity.status(HttpStatus.OK).body(PayloadUtil.createPayload("alive"));
		} catch (Exception e) {
			log.error("Error in minerEndpoint. {}",
					e.getCause());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/update")
	public ResponseEntity<Payload> update() {
		try {
			MinerHistory data = service.updateData();
			if (data == null)
			{
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(PayloadUtil.createPayload(""));
			}
			return ResponseEntity.status(HttpStatus.OK).body(PayloadUtil.createPayload("Added new data for " + data.getTimestamp()));
		} catch (Exception e) {
			log.error("Error in minerEndpoint. {}",
					e.getCause());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
