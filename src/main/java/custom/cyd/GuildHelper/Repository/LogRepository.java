package custom.cyd.GuildHelper.Repository;

import custom.cyd.GuildHelper.Entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findFirst10ByOrderByTimeDesc();
}
