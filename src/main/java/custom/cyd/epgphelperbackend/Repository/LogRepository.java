package custom.cyd.epgphelperbackend.Repository;

import custom.cyd.epgphelperbackend.Entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findFirst100ByOrderByTimeDesc();
}
