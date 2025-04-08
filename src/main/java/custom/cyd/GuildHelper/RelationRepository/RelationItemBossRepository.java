package custom.cyd.GuildHelper.RelationRepository;

import custom.cyd.GuildHelper.Entity.Boss;
import custom.cyd.GuildHelper.Entity.Item;
import custom.cyd.GuildHelper.Entity.Raid;
import custom.cyd.GuildHelper.RelationEntity.RelationItemBoss;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RelationItemBossRepository extends JpaRepository<RelationItemBoss, Long> {
    public List<RelationItemBoss> findByBoss(Boss boss);
    public List<RelationItemBoss> findByItem(Item item);
}
