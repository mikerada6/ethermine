package rad.mining.ethermine.domain;

import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@AllArgsConstructor
@Table(name = "worker")
@EqualsAndHashCode(exclude = {"minerHistory"})
@ToString
@Entity
@Slf4j public class WorkerHistory {
	public MinerHistory getMinerHistory() {
		return minerHistory;
	}

	public void setMinerHistory(MinerHistory minerHistory) {
		this.minerHistory = minerHistory;
	}

	@Id private String id;
	private String worker;
	@OneToOne(fetch = javax.persistence.FetchType.LAZY, cascade = javax.persistence.CascadeType.ALL)
	@JoinColumn(name = "hash_rate_info_id")
	private HashRateInfo hashRateInfo;
	@OneToOne(fetch = javax.persistence.FetchType.LAZY, cascade = javax.persistence.CascadeType.ALL)
	@JoinColumn(name = "share_info_id")
	private ShareInfo shareInfo;

	@ToString.Exclude
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "miner_history_id")
	private MinerHistory minerHistory;

	@PrePersist protected void onCreate() {
		if (id == null) {
			id = UUID.randomUUID().toString();
		}

	}
}
