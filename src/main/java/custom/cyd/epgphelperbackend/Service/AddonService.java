package custom.cyd.epgphelperbackend.Service;

import custom.cyd.epgphelperbackend.Entity.Character;
import custom.cyd.epgphelperbackend.Entity.Player;
import custom.cyd.epgphelperbackend.Entity.RaidReward;
import custom.cyd.epgphelperbackend.Entity.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AddonService {
    Logger logger = Logger.getLogger(AddonService.class.getName());

    @Autowired
    private RaidRewardsService raidRewardsService;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private LogService logService;

    @Autowired
    private SettingService settingService;

    public void applyAddonActions(String addonExportedString){

        addonExportedString = addonExportedString.strip();
        addonExportedString = addonExportedString.substring(1, addonExportedString.length()-1);

        Pattern epPattern = Pattern.compile("\\{EP}([^{}]*)", Pattern.CASE_INSENSITIVE);
        Pattern gpPattern = Pattern.compile("\\{GP}([^{}]*)", Pattern.CASE_INSENSITIVE);

        Matcher epMatcher = epPattern.matcher(addonExportedString);
        Matcher gpMatcher = gpPattern.matcher(addonExportedString);

        ArrayList<String> epActions = new ArrayList<>();
        ArrayList<String> gpActions = new ArrayList<>();

        logger.info("ADDON STRING: " + addonExportedString);
        while (epMatcher.find()) {
            epActions.add(epMatcher.group(1));
        }
        while (gpMatcher.find()) {
            gpActions.add(gpMatcher.group(1));
        }

        Set<String> uniqueCharacterNames = new HashSet<>();
        for(String epAction : epActions){
            String[] names = epAction.split(";")[1].split(",");
            uniqueCharacterNames.addAll(Arrays.asList(names));
        }
        for(String gpAction : gpActions){
            String name = gpAction.split(";")[1];
            uniqueCharacterNames.add(name);
        }

        String missingCharacters = findMissingCharacters(uniqueCharacterNames);
        if(!missingCharacters.isEmpty()) throw new NoSuchElementException("Characters not found: " + missingCharacters + ". Add them to apply changes!");

        for(String epAction : epActions){
            //EP action format: {EP}rewardId;charactername1,charactername2,...
            Long rewardId = Long.valueOf(epAction.split(";")[0]);
            String[] names = epAction.split(";")[1].split(",");
            playerService.rewardPlayers(names, rewardId);
        }

        for(String gpAction : gpActions){
            //gp action format: {GP}type(number - either 1, 2, 3 for ms low to high or 4,5,6 for os low to high;charName;itemName
            int bidType = Integer.parseInt(gpAction.split(";")[0]);
            String characterName = gpAction.split(";")[1];
            String itemName = gpAction.split(";")[2];

            Character character = characterService.getCharacterByName(characterName);
            boolean offspecdiscount = false;

            if(bidType >= 4){
                offspecdiscount = true;
                bidType = bidType-3;
            }

            playerService.awardGpToPlayerOfCharacter(character.getId(), bidType, offspecdiscount, itemName);
        }
    }

    public String generateAddonExportString(){

        return generateSettingString() +
                generatePlayerString() +
                generateCharacterString() +
                generateRaidRewardsString();
    }


    private String generateSettingString(){
        List<Setting> settings = settingService.getAllAddonRelevantSettings();
        StringBuilder out = new StringBuilder("{SETTINGS}");

        for (Setting setting : settings){
            out.append(setting.getSettingName()).append(":").append(setting.getSettingValue()).append(";");
        }
        out.deleteCharAt(out.length()-1);

        return out.toString();
    }

    private String generatePlayerString(){
        List<Player> players = playerService.getAllPlayers();
        StringBuilder out = new StringBuilder("{PLAYERS}");

        HashMap<Player, List<Character>> playerCharacterHashMap = new HashMap<>();

        for(Player player : players){
            playerCharacterHashMap.put(player, new ArrayList<>());
        }

        for (Player player : players) {
            out.append(player.getId()).append(":");
            double EP = Math.floor(player.getEp()*100)/100;
            double GP = Math.floor(player.getGp()*100)/100;
            out.append("EP:").append(EP).append(",GP:").append(GP);
            out.append(";");
        }
        out.deleteCharAt(out.length()-1);

        return out.toString();
    }

    private String generateCharacterString(){
        List<Player> players = playerService.getAllPlayers();
        List<Character> characters = characterService.getAllCharacters();
        StringBuilder out = new StringBuilder("{CHARACTERS}");

        HashMap<Player, List<Character>> playerCharacterHashMap = new HashMap<>();

        for(Player player : players){
            playerCharacterHashMap.put(player, new ArrayList<>());
        }

        for(Character character : characters){
            List<Character> characterList = playerCharacterHashMap.get(character.getPlayer());
            characterList.add(character);
        }

        for (Player player : players) {
            out.append(player.getId()).append(":");
            for(Character character : playerCharacterHashMap.get(player)){
                out.append(character.getName()).append("-").append(character.getClassification()).append(",");
            }
            out.deleteCharAt(out.length()-1);
            out.append(";");
        }
        out.deleteCharAt(out.length()-1);

        return out.toString();
    }

    private String generateRaidRewardsString(){
        StringBuilder out = new StringBuilder("{RAIDREWARDS}");
        List<RaidReward> raidRewards = raidRewardsService.getAllRaidRewards();
        for (RaidReward raidReward : raidRewards){
            out.append(raidReward.getId()).append(":")
                    .append(raidReward.getRaid().getName()).append(", ")
                    .append(raidReward.getRewardType()).append(":")
                    .append(raidReward.getRewardValue()).append(";");
        }
        out.deleteCharAt(out.length()-1);
        return out.toString();
    }

    private String findMissingCharacters(Set<String> characterNames){
        String errorString = "";
        for (String name : characterNames){
            if(characterService.getCharacterByName(name) == null) errorString += name+" ";
        }
        return errorString;
    }

}
