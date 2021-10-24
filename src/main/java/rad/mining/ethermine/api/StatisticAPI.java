package rad.mining.ethermine.api;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class StatisticAPI {
	public int time;
	public long reportedHashrate;
	public long currentHashrate;
	public int validShares;
	public int invalidShares;
	public int staleShares;
	public int activeWorkers;
}
