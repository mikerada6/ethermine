package rad.mining.ethermine.api;

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
public class CurrentStatisticsAPI {
	public int time;
	public int lastSeen;
	public int reportedHashrate;
	public double currentHashrate;
	public int validShares;
	public int invalidShares;
	public int staleShares;
	public int activeWorkers;
	public long unpaid;
}
