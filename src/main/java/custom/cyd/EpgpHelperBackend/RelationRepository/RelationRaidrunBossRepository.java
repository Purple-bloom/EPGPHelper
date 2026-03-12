package custom.cyd.EpgpHelperBackend.RelationRepository;

import custom.cyd.EpgpHelperBackend.Entity.Raidrun;
import custom.cyd.EpgpHelperBackend.RelationEntity.RelationRaidrunBoss;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationRaidrunBossRepository extends JpaRepository<RelationRaidrunBoss, Long> {
    public List<RelationRaidrunBoss> findByRaidrunAndDefeated(Raidrun raid, boolean defeated);
}
