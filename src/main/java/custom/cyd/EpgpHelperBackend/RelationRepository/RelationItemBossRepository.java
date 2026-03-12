package custom.cyd.EpgpHelperBackend.RelationRepository;

import custom.cyd.EpgpHelperBackend.Entity.Boss;
import custom.cyd.EpgpHelperBackend.Entity.Item;
import custom.cyd.EpgpHelperBackend.RelationEntity.RelationItemBoss;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationItemBossRepository extends JpaRepository<RelationItemBoss, Long> {
    public List<RelationItemBoss> findByBoss(Boss boss);
    public List<RelationItemBoss> findByItem(Item item);
}
