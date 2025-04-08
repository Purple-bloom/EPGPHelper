package custom.cyd.GuildHelper.Controller;

import custom.cyd.GuildHelper.Entity.Player;
import custom.cyd.GuildHelper.Entity.Raid;
import custom.cyd.GuildHelper.Service.PlayerService;
import custom.cyd.GuildHelper.Service.RaidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/raid")
public class RaidController {

    @Autowired
    private RaidService raidService;

    @GetMapping("/get")
    public List<Raid> getAllRaids(){
        return raidService.getAllRaids();
    }

    @GetMapping("/get/{id}")
    public Raid getRaid(@PathVariable("id") Long id){
        Optional<Raid> raid = raidService.getRaid(id);
        return raid.orElse(null);
    }

    @PostMapping (
            value = "/create",
            consumes = "application/json",
            produces = "application/json"
    )
    public Raid createRaid(@RequestBody Raid raid){
        return raidService.createRaid(raid);
    }
}
