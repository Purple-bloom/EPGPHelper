package custom.cyd.epgphelperbackend.Repository;

import custom.cyd.epgphelperbackend.Entity.Raid;
import custom.cyd.epgphelperbackend.Entity.RaidReward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaidRewardRepository extends JpaRepository<RaidReward, Long> {

    public List<RaidReward> findByRaid(Raid raid);
}
