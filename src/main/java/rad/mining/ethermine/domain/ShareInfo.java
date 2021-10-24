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
@Table(name = "share_info")
@EqualsAndHashCode()
@ToString()
@Entity
@Slf4j
public class ShareInfo {
	@Id
	private String id;
	private int validShares;
	private int invalidShares;
	private int staleShares;

	public ShareInfo(int validShares, int invalidShares, int staleShares)
	{
		this.validShares=validShares;
		this.invalidShares=invalidShares;
		this.staleShares=staleShares;
	}

	@PrePersist
	protected void onCreate() {
		if(id==null) {
			id = UUID.randomUUID().toString();
		}

	}
}
