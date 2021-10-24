package rad.mining.ethermine.repository;

import java.sql.Timestamp;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rad.mining.ethermine.domain.MinerHistory;

public interface MinerHistoryRepository extends JpaRepository<MinerHistory, String> {
	@Query("select m from MinerHistory m where m.timestamp = ?1")
	Optional<MinerHistory> findByTimestamp(Timestamp timestamp);

}
