package custom.cyd.EpgpHelperBackend.Repository;

import custom.cyd.EpgpHelperBackend.Entity.Boss;
import custom.cyd.EpgpHelperBackend.Entity.Raid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BossRepository extends JpaRepository<Boss, Long> {

    public List<Boss> findByRaid(Raid raid);
    public Optional<Boss> findByName(String bossName);
}
