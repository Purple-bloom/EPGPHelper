package custom.cyd.GuildHelper.Repository;

import custom.cyd.GuildHelper.Entity.RaidReward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RaidRewardRepository extends JpaRepository<RaidReward, Long> {

}
