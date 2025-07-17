package custom.cyd.GuildHelper.Service;

import custom.cyd.GuildHelper.Entity.Log;
import custom.cyd.GuildHelper.Repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Service
public class LogService {
    Logger logger = Logger.getLogger(ItemService.class.getName());

    @Autowired
    LogRepository logRepository;

    public void addLogToDb(String logMessage){
        Log log = new Log();
        log.setTime(Timestamp.valueOf(LocalDateTime.now()));
        log.setMessage(logMessage);
        logRepository.save(log);
    }

    public List<Log> getRecentLogs(){
        return logRepository.findFirst100ByOrderByTimeAsc();
    }

    public List<Log> getAllLogs(){
        return logRepository.findAll();
    }
}
