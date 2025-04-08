package custom.cyd.GuildHelper.Controller;

import custom.cyd.GuildHelper.Entity.Item;
import custom.cyd.GuildHelper.Entity.Player;
import custom.cyd.GuildHelper.Service.ItemService;
import custom.cyd.GuildHelper.Service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/item")
public class ItemController {
    Logger logger = Logger.getLogger(ItemController.class.getName());

    @Autowired
    private ItemService itemService;

    @GetMapping("/get")
    public List<Item> getAllItems(){
        return itemService.getAllItems();
    }

    @GetMapping("/get/{id}")
    public Item getItem(@PathVariable("id") Long id){
        Optional<Item> item = itemService.getItem(id);
        return item.orElse(null);
    }

    @GetMapping("/get/forBoss/{id}")
    public List<Item> getItemsForBoss(@PathVariable("id") Long bossId){
        logger.info("ItemController /get/forBoss/{id} called with bossID: " + bossId);
        List<Item> item = itemService.getAllItemsForBoss(bossId);
        logger.info("Returning " + item.size() + " items ");
        return item;
    }

    @PostMapping(
            value = "/create",
            consumes = "application/json",
            produces = "application/json"
    )

    public Item createItem(@RequestBody Item item){
        return itemService.createItem(item);
    }
}
