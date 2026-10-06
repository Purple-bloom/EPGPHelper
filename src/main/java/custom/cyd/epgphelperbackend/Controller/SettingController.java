package custom.cyd.epgphelperbackend.Controller;

import custom.cyd.epgphelperbackend.Entity.Setting;
import custom.cyd.epgphelperbackend.Service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
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
