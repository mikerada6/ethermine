package rad.mining.ethermine.domain;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "hash_rate_info")
@EqualsAndHashCode()
@ToString()
@Entity
@Slf4j
public class HashRateInfo {

	@Id
	private String id;
	private long reportedHashrate;
	private long currentHashrate;

	public HashRateInfo(long reportedHashrate, long currentHashrate)
	{
		this.reportedHashrate=reportedHashrate;
		this.currentHashrate=currentHashrate;
	}

	@PrePersist
	protected void onCreate() {
		if(id==null) {
			id = UUID.randomUUID().toString();
		}

	}
}
