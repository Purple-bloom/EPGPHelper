package custom.cyd.GuildHelper.Service;

import custom.cyd.GuildHelper.Entity.Boss;
import custom.cyd.GuildHelper.Entity.Item;
import custom.cyd.GuildHelper.Entity.Raid;
import custom.cyd.GuildHelper.RelationEntity.RelationItemBoss;
import custom.cyd.GuildHelper.RelationRepository.RelationItemBossRepository;
import custom.cyd.GuildHelper.Repository.BossRepository;
import custom.cyd.GuildHelper.Repository.ItemRepository;
import custom.cyd.GuildHelper.Repository.RaidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ItemService {
    Logger logger = Logger.getLogger(ItemService.class.getName());

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private BossRepository bossRepository;
    @Autowired
    RaidRepository raidRepository;
    @Autowired
    private RelationItemBossRepository relationItemBossRepository;

    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    public List<Item> getAllItemsForBoss(Long bossId){
        Boss boss = bossRepository.findById(bossId).orElse(null);
        if(boss == null){
            logger.severe("No Boss found for ID: " + bossId);
            return null;
        }
        List<RelationItemBoss> itemBossRelations = relationItemBossRepository.findByBoss(boss);
        List<Item> items = new ArrayList<Item>();
        for(RelationItemBoss relationItemBoss : itemBossRelations){
            items.add(relationItemBoss.getItem());
        }
        if(items.isEmpty()){
            logger.severe("No Items found for Boss ID: " + bossId);
            return null;
        }
        return items;
    }

    public List<Item> getAllItemsForRaid(Long raidId){
        Raid raid = raidRepository.findById(raidId).orElse(null);
        if(raid == null){
            logger.severe("No Raid found for ID: " + raidId);
            return null;
        }
        List<Boss> bosses = bossRepository.findByRaid(raid);
        List <Item> items = new ArrayList<>();
        for(Boss boss : bosses) {
            List <Item> itemsForThisBoss = getAllItemsForBoss(boss.getId());
            items.addAll(itemsForThisBoss);
        }
        return items;
    }

    public List<Boss> getAllBossesForItem(Long itemId){
        Item item = itemRepository.findById(itemId).orElse(null);
        if(item == null){
            logger.severe("No Item found for ID: " + itemId);
            return null;
        }
        List<RelationItemBoss> itemBossRelations = relationItemBossRepository.findByItem(item);
        List<Boss> bosses = new ArrayList<>();
        for(RelationItemBoss relationItemBoss : itemBossRelations){
            if(relationItemBoss.getBoss() == null){
                logger.info(relationItemBoss.getItem().getName());
            }
            bosses.add(relationItemBoss.getBoss());
        }
        if(bosses.isEmpty()){
            logger.severe("No Bosses found for Item ID: " + itemId);
            return null;
        }
        return bosses;
    }

    public Optional<Item> getItem(Long id){
        return itemRepository.findById(id);
    }

    public Item createItem(Item item){
        return itemRepository.save(item);
    }
}
