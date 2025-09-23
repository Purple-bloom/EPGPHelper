package custom.cyd.GuildHelper.Controller;

import custom.cyd.GuildHelper.Entity.Setting;
import custom.cyd.GuildHelper.Service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/settings")
public class SettingController {
    @Autowired
    private SettingService settingService;

    @GetMapping(
            value = "/get",
            produces = "application/json"
    )
    public List<Setting> getAllSettings(){
        return settingService.getAllSettings();
    }
}
