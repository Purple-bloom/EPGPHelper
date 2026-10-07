package custom.cyd.epgphelperbackend.Controller;

import custom.cyd.epgphelperbackend.Service.AddonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/addon")
public class AddonController {
    Logger logger = Logger.getLogger(AddonController.class.getName());

    @Autowired
    AddonService addonService;

    @GetMapping("/getAddonExport")
    public ResponseEntity<String> getAddonExportString(){
        return ResponseEntity.ok(addonService.generateAddonExportString());
    }

    @PostMapping(value = "/applyAddonExport",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<String> importAddonExportString(@RequestBody String addonExportedString){
        addonService.applyAddonActions(addonExportedString);
        return ResponseEntity.ok("Applied all changes.");
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage() != null ? e.getMessage() : "Placeholder");
    }
}
