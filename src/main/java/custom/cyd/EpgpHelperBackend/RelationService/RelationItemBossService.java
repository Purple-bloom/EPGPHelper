package custom.cyd.EpgpHelperBackend.RelationService;

import custom.cyd.EpgpHelperBackend.Entity.Boss;
import custom.cyd.EpgpHelperBackend.Entity.Item;
import custom.cyd.EpgpHelperBackend.RelationRepository.RelationItemBossRepository;
import custom.cyd.EpgpHelperBackend.Repository.BossRepository;
import custom.cyd.EpgpHelperBackend.Repository.ItemRepository;
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
