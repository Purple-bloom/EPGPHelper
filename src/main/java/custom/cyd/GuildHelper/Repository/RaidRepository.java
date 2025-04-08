package custom.cyd.GuildHelper.Repository;

import custom.cyd.GuildHelper.Entity.Player;
import custom.cyd.GuildHelper.Entity.Raid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RaidRepository extends JpaRepository<Raid, Long> {
    public Optional<Raid> findByNameIgnoreCase(String name);
}
