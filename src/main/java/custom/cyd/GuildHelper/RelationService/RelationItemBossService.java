package custom.cyd.GuildHelper.RelationService;

import custom.cyd.GuildHelper.Entity.Boss;
import custom.cyd.GuildHelper.Entity.Item;
import custom.cyd.GuildHelper.Entity.Raid;
import custom.cyd.GuildHelper.RelationRepository.RelationItemBossRepository;
import custom.cyd.GuildHelper.Repository.BossRepository;
import custom.cyd.GuildHelper.Repository.ItemRepository;
import custom.cyd.GuildHelper.Repository.RaidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
