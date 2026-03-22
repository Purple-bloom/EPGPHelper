package custom.cyd.epgphelperbackend.Service;

import custom.cyd.epgphelperbackend.Entity.Setting;
import custom.cyd.epgphelperbackend.Repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * This service is not supposed to be exposed through any API. The other services will use this service to update values.
 * */

@Service
public class SettingService {
    public static final String MINIMUM_GP_SETTING_NAME = "basegp";
    public static final String WEEKLY_DECAY_SETTING_NAME = "weeklydecay";
    public static final String LOW_BID_SETTING_NAME = "lowcost";
    public static final String MID_BID_SETTING_NAME = "midcost";
    public static final String HIGH_BID_SETTING_NAME = "highcost";
    public static final String ALT_REDUCTION_SETTING_NAME = "altreduction";
    public static final String OS_GP_DISCOUNT_SETTING_NAME = "offspecgpdiscount";

    private static final String[] validSettings = {MINIMUM_GP_SETTING_NAME, WEEKLY_DECAY_SETTING_NAME, LOW_BID_SETTING_NAME, MID_BID_SETTING_NAME,
            HIGH_BID_SETTING_NAME, ALT_REDUCTION_SETTING_NAME, OS_GP_DISCOUNT_SETTING_NAME};

    @Autowired
    private SettingRepository settingRepository;
    @Autowired
    LogService logService;

    public List<Setting> getAllSettings(){
        return settingRepository.findAll();
    }

    public ResponseEntity<String> addSetting(String settingName, Integer newValue){
        Optional<Setting> setting = settingRepository.findBySettingNameIgnoreCase(settingName);
        if(setting.isPresent()){
            return ResponseEntity.badRequest().body("Setting by name \"" + settingName + "\" already exists.");
        }
        if(Arrays.stream(validSettings).noneMatch(validSetting -> validSetting.equalsIgnoreCase(settingName))){
            return ResponseEntity.badRequest().body("Setting by name \"" + settingName + "\" is not allowed to exist.");
        }
        Setting newSetting = new Setting();
        newSetting.setSettingName(settingName);
        newSetting.setSettingValue(newValue);
        settingRepository.save(newSetting);
        logService.addLogToDb("Setting by name \"" + settingName + "\" added with value " + newValue + ".");
        return ResponseEntity.ok("Added setting " + settingName + " with Value " + newValue + ".");
    }

    public ResponseEntity<String> changeSetting(String settingName, Integer newValue){
        Optional<Setting> setting = settingRepository.findBySettingNameIgnoreCase(settingName);
        if(setting.isEmpty()){
            return ResponseEntity.badRequest().body("Failed to find Setting by name of \"" + settingName + "\".");
        }
        if(newValue < 0) return ResponseEntity.badRequest().body(settingName + " value must be positive.");
        if(settingName.equalsIgnoreCase(WEEKLY_DECAY_SETTING_NAME) | settingName.equalsIgnoreCase(ALT_REDUCTION_SETTING_NAME) | settingName.equalsIgnoreCase(OS_GP_DISCOUNT_SETTING_NAME)) {
            if( newValue >= 100 ){
                return ResponseEntity.badRequest().body(settingName + " value must be in range 0 to 100.");
            }
        }
        Integer oldValue = setting.get().getSettingValue();
        setting.get().setSettingValue(newValue);
        settingRepository.save(setting.get());
        logService.addLogToDb("Setting by name \"" + settingName + "\" changed. Old value: " + oldValue + " New Value: " + newValue + ".");
        return ResponseEntity.ok("Updated " + settingName + " to new Value " + newValue + ".");
    }

    public Integer loadSetting(String settingName){
        Optional<Setting> setting = settingRepository.findBySettingNameIgnoreCase(settingName);
        return setting.map(Setting::getSettingValue).orElse(0);
    }
}
