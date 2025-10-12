package custom.cyd.GuildHelper.Controller;

import custom.cyd.GuildHelper.Dto.PlayerDto;
import custom.cyd.GuildHelper.Dto.PlayerInputDto;
import custom.cyd.GuildHelper.Entity.Player;
import custom.cyd.GuildHelper.Entity.RaidReward;
import custom.cyd.GuildHelper.Entity.Setting;
import custom.cyd.GuildHelper.Service.PlayerService;
import custom.cyd.GuildHelper.Service.SettingService;
import jakarta.websocket.server.PathParam;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    Logger logger = Logger.getLogger(PlayerController.class.getName());

    @Autowired
    private PlayerService playerService;

    @GetMapping("/get")
    public List<PlayerDto> getAllPlayers(){
        return playerService.getAllPlayers();
    }

    @GetMapping("/get/{id}")
    public Player getPlayer(@PathVariable("id") Long id){
        logger.info("Getting player with id " + id);
        Optional<Player> player = playerService.getPlayer(id);
        return player.orElse(null);
    }

    @PostMapping(
            value = "/delete/{id}"
    )
    public void deletePlayer(@PathVariable("id") Long id){
        logger.info("Received POST Request to delete a Player with ID " + id);
        playerService.deletePlayer(id);
    }

    @GetMapping("/get/name/{name}")
    public Player getPlayerByName(@PathVariable("name") String name){
        logger.info("Getting player with name " + name);
        Optional<Player> player = playerService.getPlayerByName(name);
        return player.orElse(null);
    }

    @PostMapping (
            value = "/create",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<String> createPlayer(@RequestBody PlayerInputDto playerInputDto){
        try {
            logger.info("Creating Player of " + playerInputDto);
            Player player = new Player();
            player.setEp(0);
            player.setGp(0);
            player.setName(playerInputDto.getName());
            player.setRank(playerInputDto.getRank());
            player.setId(null);
            player.setActive(true);
            Player playerResponse = playerService.createPlayer(player);
            return new ResponseEntity<>("Successfully created a new player entry. " + playerResponse, HttpStatus.OK);
        } catch (Exception e){
            if(e.getMessage().toLowerCase().contains("duplicate key value violates unique constraint")){
                logger.severe("Tried to create player with name that already exists. Change the name.");
                ResponseEntity<String> response = new ResponseEntity<>("Error while creating new player: Name already exists.", HttpStatus.INTERNAL_SERVER_ERROR);
                return response;
            }
            return new ResponseEntity<>("Error while creating new player: Something went wrong. Check the logs.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping (
            value = "/update",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<Object> updatePlayer(@RequestBody Player player){
        logger.info("updating player: " + player.toString());
        try {
            return new ResponseEntity<>(playerService.updatePlayer(player), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Error while creating new player: Something went wrong. Check the logs.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping (
            value = "/rewardMultiple/{id}",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<String> rewardPlayers(@RequestBody String[][] characternames, @PathVariable("id") Long id){
        logger.info("rewarding players: " + Arrays.toString(characternames) + " for raidReward " + id);
        return playerService.rewardPlayers(characternames, id);
    }

    @PostMapping (
            value = "/awardGp/{id}",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<String> awardGpToPlayer(@RequestBody Integer bidType, @PathVariable("id") Long id){
        logger.info("GP award player endpoint called of character: " + id + " for bid type " + bidType);
        return playerService.awardGpToPlayerOfCharacter(id, bidType);
    }

    @PostMapping(
            value = "/applyWeeklyDecay",
            produces = "application/json"
    )
    public ResponseEntity<String> applyWeeklyDecay(){
        logger.info("Applying weekly decay.");
        return playerService.applyWeeklyDecay();
    }

    @PostMapping(
            value = "/changeSetting",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<String> changeSetting(@RequestBody Setting setting){
        logger.info("Changing setting" + setting.getSettingName() + " to new value " + setting.getSettingValue() + ".");
        if(setting.getSettingName().equalsIgnoreCase(SettingService.MINIMUM_GP_SETTING_NAME)){
            return playerService.updateMinimumGp(setting.getSettingValue());
        } else if (setting.getSettingName().equalsIgnoreCase(SettingService.WEEKLY_DECAY_SETTING_NAME)){
            return playerService.updateWeeklyDecay(setting.getSettingValue());
        } else if (setting.getSettingName().equalsIgnoreCase(SettingService.HIGH_BID_SETTING_NAME)){
            return playerService.updateHighBidCost(setting.getSettingValue());
        } else if (setting.getSettingName().equalsIgnoreCase(SettingService.MID_BID_SETTING_NAME)){
            return playerService.updateMidBidCost(setting.getSettingValue());
        } else if (setting.getSettingName().equalsIgnoreCase(SettingService.LOW_BID_SETTING_NAME)){
            return playerService.updateLowBidCost(setting.getSettingValue());
        } else if (setting.getSettingName().equalsIgnoreCase(SettingService.ALT_REDUCTION)){
            return playerService.updateAltReduction(setting.getSettingValue());
        } else {
            return ResponseEntity.badRequest().body("Could not find setting to edit.");
        }
    }
}
