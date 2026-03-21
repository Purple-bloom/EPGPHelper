package custom.cyd.epgphelperbackend.RelationRepository;

import custom.cyd.epgphelperbackend.Entity.Boss;
import custom.cyd.epgphelperbackend.Entity.Item;
import custom.cyd.epgphelperbackend.RelationEntity.RelationItemBoss;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationItemBossRepository extends JpaRepository<RelationItemBoss, Long> {
    public List<RelationItemBoss> findByBoss(Boss boss);
    public List<RelationItemBoss> findByItem(Item item);
}
