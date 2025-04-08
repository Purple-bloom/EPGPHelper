package custom.cyd.GuildHelper.Repository;

import custom.cyd.GuildHelper.Entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    public Optional<Player> findByNameIgnoreCase(String name);
}
