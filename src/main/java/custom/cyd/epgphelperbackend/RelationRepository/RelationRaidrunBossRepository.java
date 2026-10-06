package custom.cyd.epgphelperbackend.RelationRepository;

import custom.cyd.epgphelperbackend.Entity.Raidrun;
import custom.cyd.epgphelperbackend.RelationEntity.RelationRaidrunBoss;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationRaidrunBossRepository extends JpaRepository<RelationRaidrunBoss, Long> {
    public List<RelationRaidrunBoss> findByRaidrunAndDefeated(Raidrun raid, boolean defeated);
}
