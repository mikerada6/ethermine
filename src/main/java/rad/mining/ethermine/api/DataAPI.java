package rad.mining.ethermine.api;

import java.util.List;
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
public class DataAPI {
	public List<StatisticAPI> statisticAPIS;
	public List<WorkerAPI> workerAPIS;
	public CurrentStatisticsAPI currentStatisticsAPI;
	public SettingsAPI settings;
}
