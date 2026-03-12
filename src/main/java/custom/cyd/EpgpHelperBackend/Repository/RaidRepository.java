package custom.cyd.EpgpHelperBackend.Repository;

import custom.cyd.EpgpHelperBackend.Entity.Raid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RaidRepository extends JpaRepository<Raid, Long> {
    public Optional<Raid> findByNameIgnoreCase(String name);
}
