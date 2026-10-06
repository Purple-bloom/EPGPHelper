package custom.cyd.epgphelperbackend.Repository;

import custom.cyd.epgphelperbackend.Entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    public Optional<Player> findByNameIgnoreCase(String name);
}
