package rad.mining.ethermine.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CurrentStatsAPIData {
	public int time;
	public int lastSeen;
	public long reportedHashrate;
	public long currentHashrate;
	public int validShares;
	public int invalidShares;
	public int staleShares;
	public long averageHashrate;
	public int activeWorkers;
	public long unpaid;
	public double unconfirmed;
	public double coinsPerMin;
	public double usdPerMin;
	public double btcPerMin;
}
