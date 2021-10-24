package rad.mining.ethermine.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor @Table(name = "miner", indexes = {
		@Index(name = "idx_minerhistory_timestamp", columnList = "timestamp") })
@EqualsAndHashCode()
@ToString
@Entity
@Slf4j
public class MinerHistory {
	@Id
	private String id;
	private long unpaid;
	private double unconfirmed;
	private double coinsPerMin;
	private double usdPerMin;
	private double btcPerMin;

	@Column(nullable = false, unique = true) private Timestamp timestamp;
	@ToString.Exclude @OneToOne(fetch = javax.persistence.FetchType.LAZY, cascade = javax.persistence.CascadeType.ALL) @JoinColumn(name = "hash_rate_info_id")
	private HashRateInfo hashRateInfo;
	@ToString.Exclude @JoinColumn @OneToOne(fetch = javax.persistence.FetchType.LAZY, cascade = javax.persistence.CascadeType.ALL)
	private ShareInfo shareInfo;
	@ToString.Exclude
	@OneToMany(mappedBy = "minerHistory", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<WorkerHistory> workerHistorySet;

	public Set<WorkerHistory> getWorkerHistorySet() {
		if(workerHistorySet == null)
		{
			workerHistorySet = new HashSet<>();
		}
		return workerHistorySet;
	}

	public void addWorkerHistory(WorkerHistory wh)
	{
		if(workerHistorySet == null)
		{
			workerHistorySet = new HashSet<>();
		}
		workerHistorySet.add(wh);
	}

	@PrePersist
	protected void onCreate() {
		if(id==null) {
			id = UUID.randomUUID().toString();
		}

	}
}
