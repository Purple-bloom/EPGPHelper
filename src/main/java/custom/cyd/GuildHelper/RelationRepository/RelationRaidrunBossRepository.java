package custom.cyd.GuildHelper.RelationRepository;

import custom.cyd.GuildHelper.Entity.Boss;
import custom.cyd.GuildHelper.Entity.Raid;
import custom.cyd.GuildHelper.Entity.Raidrun;
import custom.cyd.GuildHelper.RelationEntity.RelationRaidrunBoss;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationRaidrunBossRepository extends JpaRepository<RelationRaidrunBoss, Long> {
    public List<RelationRaidrunBoss> findByRaidrunAndDefeated(Raidrun raid, boolean defeated);
}
