package custom.cyd.GuildHelper.Service;

import custom.cyd.GuildHelper.Dto.PlayerDto;
import custom.cyd.GuildHelper.Entity.Character;
import custom.cyd.GuildHelper.Entity.Player;
import custom.cyd.GuildHelper.Entity.RaidReward;
import custom.cyd.GuildHelper.Repository.CharacterRepository;
import custom.cyd.GuildHelper.Repository.PlayerRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class PlayerService {
    Logger logger = Logger.getLogger(PlayerService.class.getName());
    private static double minimumGp;
    private static double weeklyDecay;
    private static int lowBidCost;
    private static int midBidCost;
    private static int highBidCost;
    private static int altReduction;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private RaidRewardsService raidRewardsService;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private LogService logService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private CharacterRepository characterRepository;

    public List<PlayerDto> getAllPlayers(){
        List<Player> players = playerRepository.findAll();
        return convertPlayersToDtos(players);
    }

    @PostConstruct
    private void initializePlayerService(){
        minimumGp = settingService.loadSetting(SettingService.MINIMUM_GP_SETTING_NAME);
        weeklyDecay = (double) settingService.loadSetting(SettingService.WEEKLY_DECAY_SETTING_NAME) / 100;
        lowBidCost = settingService.loadSetting(SettingService.LOW_BID_SETTING_NAME);
        midBidCost = settingService.loadSetting(SettingService.MID_BID_SETTING_NAME);
        highBidCost = settingService.loadSetting(SettingService.HIGH_BID_SETTING_NAME);
        altReduction = settingService.loadSetting(SettingService.ALT_REDUCTION);

        if(minimumGp == 0){
            settingService.addSetting(SettingService.MINIMUM_GP_SETTING_NAME, 10);
            minimumGp = 10;
        }
        if(weeklyDecay == 0){
            settingService.addSetting(SettingService.WEEKLY_DECAY_SETTING_NAME, 20);
            weeklyDecay = (double) 20 /100;
        }
        if(lowBidCost == 0){
            settingService.addSetting(SettingService.LOW_BID_SETTING_NAME, 5);
            lowBidCost = 5;
        }
        if(midBidCost == 0){
            settingService.addSetting(SettingService.MID_BID_SETTING_NAME, 15);
            midBidCost = 15;
        }
        if(highBidCost == 0){
            settingService.addSetting(SettingService.HIGH_BID_SETTING_NAME, 45);
            highBidCost = 45;
        }
        if(altReduction == 0){
            settingService.addSetting(SettingService.ALT_REDUCTION, 0);
            altReduction = 0;
        }
        System.out.printf("Initialized PlayerService with following Attributes and values %s:%f %s:%f %s:%d %s:%d %s:%d %s:%d",
                SettingService.MINIMUM_GP_SETTING_NAME, minimumGp,
                SettingService.WEEKLY_DECAY_SETTING_NAME, weeklyDecay,
                SettingService.LOW_BID_SETTING_NAME, lowBidCost,
                SettingService.MID_BID_SETTING_NAME, midBidCost,
                SettingService.HIGH_BID_SETTING_NAME, highBidCost,
                SettingService.ALT_REDUCTION, altReduction);
    }

    public Optional<Player> getPlayer(Long id){
        return playerRepository.findById(id);
    }

    public Optional<Player> getPlayerByName(String name){
        return playerRepository.findByNameIgnoreCase(name);
    }

    public Player createPlayer(Player player) throws Exception {
        player.setGp(minimumGp);
        logger.info("Creating player: " + player);
        Player out = playerRepository.save(player);
        Character defaultCharacter = new Character();
        defaultCharacter.setPlayer(player);
        defaultCharacter.setName(player.getName());
        defaultCharacter.setClassification("main");
        characterService.createCharacter(defaultCharacter);
        return out;
    }

    public void deletePlayer(Long id){
        logger.info("Deleting Player " + Objects.requireNonNull(playerRepository.findById(id).orElse(null)));
        playerRepository.deleteById(id);
    }

    public Player updatePlayer(Player player) throws Exception{
        logger.info("Updating player now.");
        Player originalPlayer = playerRepository.findById(player.getId()).orElse(null);
        if(originalPlayer == null){
            logger.severe("Request to update player was made with non-existent player object: " + player.toString());
            return null;
        }
        if(originalPlayer.getGp() != player.getGp() || originalPlayer.getEp() != player.getEp()){
            logService.addLogToDb("Manually overriding player " + player.getName() +
                    " from EP: " + originalPlayer.getEp() + " and GP: " + originalPlayer.getGp() +
                    " to new EP: " + player.getEp() + " and new GP: " + player.getGp());
        }
        originalPlayer.setName(player.getName());
        originalPlayer.setRank(player.getRank());
        originalPlayer.setEp(player.getEp());
        originalPlayer.setGp(player.getGp());
        originalPlayer.setActive(player.getActive());
        return playerRepository.save(originalPlayer);
    }

    public ResponseEntity<String> rewardPlayers(String[][] characternames, Long raidRewardId){
        RaidReward raidReward = raidRewardsService.getRaidReward(raidRewardId).orElse(null);
        if(raidReward == null){
            logger.severe("Attempted update with invalid Raidreward. " + raidRewardId);
            return null;
        }
        Map<String, Integer> characterCountMap = new HashMap<>();
        for(String[] row : characternames){
            for(String characterString : row){
                characterString = characterString.toLowerCase();
                if(characterCountMap.containsKey(characterString)){
                    characterCountMap.put(characterString, characterCountMap.get(characterString) + 1);
                } else {
                    characterCountMap.put(characterString, 1);
                }
            }
        }

        Integer maxCount = 0;
        for(Integer count : characterCountMap.values()){
            if(count > maxCount) maxCount = count;
        }

        ArrayList<String> missingCharacters = new ArrayList<>();
        List<Character> characters = new ArrayList<>();

        for(String characterName : characterCountMap.keySet()){
            Character character = characterRepository.findByNameIgnoreCase(characterName).orElse(null);
            if(character == null){
                logger.severe("unable to find character for name " + characterName + ". This will result in no players being updated!");
                missingCharacters.add(characterName);
            }
            characters.add(character);
        }
        if(!missingCharacters.isEmpty()){
            return ResponseEntity.badRequest().body("Unable to find the following Characters: " + missingCharacters.toString() + ".");
        }

        ArrayList<Long> alreadyRewardedPlayers = new ArrayList<Long>();
        for(Character character : characters){
            double modifier = (double) characterCountMap.get(character.getName().toLowerCase()) / maxCount;
            Player player = character.getPlayer();
            if(alreadyRewardedPlayers.contains(player.getId())){
                logService.addLogToDb("Not rewarding player " + player.getName() + " another time - they appeared more than once, probably due to multiboxing or an incorrect character-player relation.");
            } else {
                alreadyRewardedPlayers.add(player.getId());
                double reward = (raidReward.getRewardValue() * modifier);
                if(!character.getClassification().equalsIgnoreCase("main")){
                    int altModifier = settingService.loadSetting(SettingService.ALT_REDUCTION);
                    reward = reward * (1.0 - altModifier);
                }
                player.setEp(player.getEp() + reward);
                playerRepository.save(player);
                if(raidReward.getRewardValue() != 0) {
                    String logMessage = "Awarded " + String.format("%.2f", (raidReward.getRewardValue() * modifier)) + " EP to player " + player.getName() + " for \"" + raidReward.getRaid().getName() + ": " + raidReward.getRewardType() + "\"";
                    logService.addLogToDb(logMessage);
                }
            }

        }
        return ResponseEntity.ok("Successfully awarded EP to all Players of the characters. Reminder: EP is calculated based on the # of character appearances, not the rows.");
    }

    public ResponseEntity<String> unrewardPlayers(String[][] characternames, Long raidRewardId){
        RaidReward raidReward = raidRewardsService.getRaidReward(raidRewardId).orElse(null);
        if(raidReward == null){
            logger.severe("Attempted update with invalid Raidreward. " + raidRewardId);
            return null;
        }
        Map<String, Integer> characterCountMap = new HashMap<>();
        for(String[] row : characternames){
            for(String characterString : row){
                characterString = characterString.toLowerCase();
                if(characterCountMap.containsKey(characterString)){
                    characterCountMap.put(characterString, characterCountMap.get(characterString) + 1);
                } else {
                    characterCountMap.put(characterString, 1);
                }
            }
        }

        Integer maxCount = 0;
        for(Integer count : characterCountMap.values()){
            if(count > maxCount) maxCount = count;
        }

        ArrayList<String> missingCharacters = new ArrayList<>();
        List<Character> characters = new ArrayList<>();

        for(String characterName : characterCountMap.keySet()){
            Character character = characterRepository.findByNameIgnoreCase(characterName).orElse(null);
            if(character == null){
                logger.severe("unable to find character for name " + characterName + ". This will result in no players being updated!");
                missingCharacters.add(characterName);
            }
            characters.add(character);
        }
        if(!missingCharacters.isEmpty()){
            return ResponseEntity.badRequest().body("Unable to find the following Characters: " + missingCharacters.toString() + ".");
        }


        for(Character character : characters){
            double modifier = (double) characterCountMap.get(character.getName().toLowerCase()) / maxCount;
            Player player = character.getPlayer();
            player.setEp(player.getEp() + (raidReward.getRewardValue() * modifier));
            playerRepository.save(player);
            if(raidReward.getRewardValue() != 0) {
                String logMessage = "Awarded " + String.format("%.2f", (raidReward.getRewardValue() * modifier)) + " EP to player " + player.getName() + " for \"" + raidReward.getRaid().getName() + ": " + raidReward.getRewardType() + "\"";
                logService.addLogToDb(logMessage);
            }
        }
        return ResponseEntity.ok("Successfully awarded EP to all Players of the characters. Reminder: EP is calculated based on the # of character appearances, not the rows.");
    }

    public ResponseEntity<String> awardGpToPlayerOfCharacter(Long id, int bidType){
        int gpValue = 0;
        if(bidType == 1) gpValue = lowBidCost;
        if(bidType == 2) gpValue = midBidCost;
        if(bidType == 3) gpValue = highBidCost;

        Character targetCharacter = characterService.getCharacterById(id);
        Player targetPlayer = targetCharacter.getPlayer();
        targetPlayer.setGp(targetPlayer.getGp() + gpValue);
        playerRepository.save(targetPlayer);
        logger.info("Awarded " + gpValue + " GP to player " + targetPlayer.getId());
        logService.addLogToDb("Awarded " + gpValue + " GP to player " + targetPlayer.getName());
        return ResponseEntity.ok("Successfully added GP.");
    }

    public ResponseEntity<String> applyWeeklyDecay(){
        List<Player> players = playerRepository.findAll();
        for(Player player : players){
            if(player.getActive()) {
                player.setGp(minimumGp + (player.getGp() - minimumGp) * (1 - weeklyDecay));
                player.setEp(player.getEp() * (1 - weeklyDecay));
            }
        }
        playerRepository.saveAll(players);
        logService.addLogToDb("Applied weeklay decay of " + weeklyDecay);
        return ResponseEntity.ok("Successfully decayed all non-frozen players.");
    }


    private List<PlayerDto> convertPlayersToDtos(List<Player> players){
        List<PlayerDto> out = new ArrayList<>();
        List<Character> characters = characterService.getAllCharacters();
        for (Player player : players){
            ArrayList<String> playerCharacters = new ArrayList<>();
            for(Character character : characters){
                if(Objects.equals(character.getPlayer().getId(), player.getId())){
                    playerCharacters.add(character.getName());
                }
            }
            if(!playerCharacters.isEmpty()){
                String[] outCharacters = new String[playerCharacters.size()];
                outCharacters = playerCharacters.toArray(outCharacters);
                out.add(new PlayerDto(player, outCharacters));
            } else {
                out.add(new PlayerDto(player, new String[]{""}));
            }
        }
        return out;
    }

    private void changeAllGpValuesBy(int diff){
        List<Player> allPlayers = playerRepository.findAll();
        for (Player player : allPlayers){
            player.setGp(player.getGp() - diff);
        }
        playerRepository.saveAll(allPlayers);
    }

    public ResponseEntity<String> updateMinimumGp(int newValue){
        if (newValue <= 0){
            return ResponseEntity.badRequest().body("Invalid setting value.");
        }
        int oldGpMinimum = settingService.loadSetting(SettingService.MINIMUM_GP_SETTING_NAME);
        int gpDiff = oldGpMinimum - newValue;
        changeAllGpValuesBy(gpDiff);
        settingService.changeSetting(SettingService.MINIMUM_GP_SETTING_NAME, newValue);
        minimumGp = newValue;
        return ResponseEntity.ok("Setting changed and ALL players updated with new GP values (difference to minimum is retained).");
    }

    public ResponseEntity<String> updateWeeklyDecay(int newValue){
        if (newValue < 0 || newValue > 100){
            return ResponseEntity.badRequest().body("Invalid setting value.");
        }
        settingService.changeSetting(SettingService.WEEKLY_DECAY_SETTING_NAME, newValue);
        weeklyDecay = newValue;
        return ResponseEntity.ok("Setting changed.");
    }

    public ResponseEntity<String> updateLowBidCost(int newValue){
        if (newValue < 0){
            return ResponseEntity.badRequest().body("Invalid setting value.");
        }
        settingService.changeSetting(SettingService.LOW_BID_SETTING_NAME, newValue);
        lowBidCost = newValue;
        return ResponseEntity.ok("Setting changed.");
    }

    public ResponseEntity<String> updateMidBidCost(int newValue){
        if (newValue < 0){
            return ResponseEntity.badRequest().body("Invalid setting value.");
        }
        settingService.changeSetting(SettingService.MID_BID_SETTING_NAME, newValue);
        midBidCost = newValue;
        return ResponseEntity.ok("Setting changed.");
    }

    public ResponseEntity<String> updateHighBidCost(int newValue){
        if (newValue < 0){
            return ResponseEntity.badRequest().body("Invalid setting value.");
        }
        settingService.changeSetting(SettingService.HIGH_BID_SETTING_NAME, newValue);
        highBidCost = newValue;
        return ResponseEntity.ok("Setting changed.");
    }
}
