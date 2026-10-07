package custom.cyd.epgphelperbackend.Controller;

import custom.cyd.epgphelperbackend.Entity.Raid;
import custom.cyd.epgphelperbackend.Service.RaidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRaid(@PathVariable("id") Long id) {
        raidService.deleteRaid(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage() != null ? e.getMessage() : "Raid not found"); // HTTP 404
    }
}
