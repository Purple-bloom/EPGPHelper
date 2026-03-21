package custom.cyd.epgphelperbackend.RelationService;

import custom.cyd.epgphelperbackend.Entity.Boss;
import custom.cyd.epgphelperbackend.Entity.Item;
import custom.cyd.epgphelperbackend.RelationRepository.RelationItemBossRepository;
import custom.cyd.epgphelperbackend.Repository.BossRepository;
import custom.cyd.epgphelperbackend.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class RelationItemBossService {
    Logger logger = Logger.getLogger(RelationItemBossService.class.getName());

    @Autowired
    private RelationItemBossRepository relationItemBossRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private BossRepository bossRepository;

    public List<Item> getItemsForBoss(Boss boss){
        return null;
    }
}
