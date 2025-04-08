package custom.cyd.GuildHelper.Controller;

import custom.cyd.GuildHelper.Entity.RaidReward;
import custom.cyd.GuildHelper.Service.RaidRewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/raidreward")
public class RaidRewardsController {
    Logger logger = Logger.getLogger(RaidRewardsController.class.getName());

    @Autowired
    private RaidRewardsService raidRewardsService;

    @GetMapping("/get")
    public List<RaidReward> getAllRaids(){
        return raidRewardsService.getAllRaidRewards();
    }

    @GetMapping("/get/{id}")
    public RaidReward getRaidReward(@PathVariable("id") Long id){
        Optional<RaidReward> raidReward = raidRewardsService.getRaidReward(id);
        return raidReward.orElse(null);
    }

    @PostMapping(
            value = "/create",
            consumes = "application/json",
            produces = "application/json"
    )
    public RaidReward createRaidReward(@RequestBody RaidReward raidReward){
        logger.info("Create raid reward endpoint called for " + raidReward);
        return raidRewardsService.createRaidReward(raidReward);
    }

    @PostMapping(
            value = "/update",
            consumes = "application/json",
            produces = "application/json"
    )
    public RaidReward updateRaidReward(@RequestBody RaidReward raidReward){
        logger.info("Update raid reward endpoint called with " + raidReward);
        return raidRewardsService.updateRaidReward(raidReward);
    }

    @PostMapping(
            value = "/delete",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<String> deleteRaidReward(@RequestBody RaidReward raidReward){
        logger.info("Delete raid reward endpoint called with " + raidReward);
        return raidRewardsService.deleteRaidReward(raidReward);
    }
}
