package custom.cyd.EpgpHelperBackend.Repository;

import custom.cyd.EpgpHelperBackend.Entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findFirst100ByOrderByTimeDesc();
}
