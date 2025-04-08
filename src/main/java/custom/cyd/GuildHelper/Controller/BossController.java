package custom.cyd.GuildHelper.Controller;

import custom.cyd.GuildHelper.Entity.Boss;
import custom.cyd.GuildHelper.Entity.Item;
import custom.cyd.GuildHelper.Service.BossService;
import custom.cyd.GuildHelper.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/boss")
public class BossController {
    Logger logger = Logger.getLogger(BossController.class.getName());

    @Autowired
    private BossService bossService;

    @GetMapping("/get")
    public List<Boss> getAllBosses(){
        logger.info("Getting all bosses");
        return bossService.getAllBosses();
    }

    @GetMapping("/get/{id}")
    public Boss getBoss(@PathVariable("id") Long id){
        logger.info("Boss: /get/{id} called with id " + id);
        Optional<Boss> boss = bossService.getBoss(id);
        return boss.orElse(null);
    }

    @GetMapping("/get/forRaid/{id}")
    public List<Boss> getBossForRaid(@PathVariable("id") Long id){
        logger.info(String.format("Boss: /get/forRaid/{raid} called with input %s.", id));
        return bossService.getAllBossesForRaid(id);
    }

    @PostMapping(
            value = "/create",
            consumes = "application/json",
            produces = "application/json"
    )
    public Boss createBoss(@RequestBody Boss boss){
        logger.info("Received POST Request to create a boss.");
        logger.info(boss.getName());
        logger.info(boss.getRaid().toString());
        return bossService.createBoss(boss);
    }
}
