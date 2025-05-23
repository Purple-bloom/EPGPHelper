package custom.cyd.GuildHelper.Service;

import custom.cyd.GuildHelper.Dto.PlayerDto;
import custom.cyd.GuildHelper.Entity.Character;
import custom.cyd.GuildHelper.Entity.Player;
import custom.cyd.GuildHelper.Entity.RaidReward;
import custom.cyd.GuildHelper.Repository.CharacterRepository;
import custom.cyd.GuildHelper.Repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class PlayerService {
    Logger logger = Logger.getLogger(PlayerService.class.getName());
    public static final double minimumGp = 10;
    public static final double weeklyDecay = 0.2;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private RaidRewardsService raidRewardsService;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private LogService logService;

    @Autowired
    private CharacterRepository characterRepository;

    public List<PlayerDto> getAllPlayers(){
        List<Player> players = playerRepository.findAll();
        return convertPlayersToDtos(players);
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
        return playerRepository.save(player);
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


        for(Character character : characters){
            double modifier = (double) characterCountMap.get(character.getName().toLowerCase()) / maxCount;
            Player player = character.getPlayer();
            player.setEp(player.getEp() + (raidReward.getRewardValue() * modifier));
            playerRepository.save(player);
            String logMessage = "Awarded " + (raidReward.getRewardValue() * modifier) + " EP to player " + player.getName() + "for \"" + raidReward.getRewardType() + "\"";
            logService.addLogToDb(logMessage);
        }
        return ResponseEntity.ok("Successfully awarded EP to all Players of the characters. Reminder: EP is calculated based on the # of character appearances, not the rows.");
    }

    public ResponseEntity<String> awardGpToPlayerOfCharacter(Long id, int gpValue){
        if(gpValue != 5 && gpValue != 15 && gpValue != 45){
            return ResponseEntity.badRequest().body("Fuck off");
        }
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
                player.setGp(10 + (player.getGp() - 10) * (1 - weeklyDecay));
                player.setEp(player.getEp() * (1 - weeklyDecay));
            }
        }
        playerRepository.saveAll(players);
        logService.addLogToDb("Applied weeklay decay of " + weeklyDecay);
        return ResponseEntity.ok("Successfully decayed all non-frozen players.");
    }


    private List<PlayerDto> convertPlayersToDtos(List<Player> players){
        List<PlayerDto> out = new ArrayList<>();
        for (Player player : players){
            out.add(new PlayerDto(player));
        }
        return out;
    }
}
