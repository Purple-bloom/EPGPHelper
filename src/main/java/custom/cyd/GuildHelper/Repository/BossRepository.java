package custom.cyd.GuildHelper.Repository;

import custom.cyd.GuildHelper.Entity.Boss;
import custom.cyd.GuildHelper.Entity.Raid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BossRepository extends JpaRepository<Boss, Long> {

    public List<Boss> findByRaid(Raid raid);
    public Optional<Boss> findByName(String bossName);
}
