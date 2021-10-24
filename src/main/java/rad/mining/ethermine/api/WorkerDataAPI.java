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
public class WorkerDataAPI {
	public String worker;
	public int time;
	public int lastSeen;
	public long reportedHashrate;
	public long currentHashrate;
	public int validShares;
	public int invalidShares;
	public int staleShares;
	public double averageHashrate;
}

