package custom.cyd.GuildHelper.Controller;

import custom.cyd.GuildHelper.Entity.Log;
import custom.cyd.GuildHelper.Service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/log")
public class LogController {

    @Autowired
    LogService logService;

    @GetMapping("/getRecent")
    public List<Log> getRecentLogs(){
        return logService.getRecentLogs();
    }

    @GetMapping("/getAll")
    public List<Log> getAllLogs(){
        return logService.getAllLogs();
    }
}