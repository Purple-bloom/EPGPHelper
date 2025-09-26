package custom.cyd.GuildHelper.Service;

import custom.cyd.GuildHelper.Dto.CharacterDto;
import custom.cyd.GuildHelper.Entity.Boss;
import custom.cyd.GuildHelper.Entity.Character;
import custom.cyd.GuildHelper.Entity.Player;
import custom.cyd.GuildHelper.Repository.BossRepository;
import custom.cyd.GuildHelper.Repository.CharacterRepository;
import custom.cyd.GuildHelper.Repository.PlayerRepository;
import custom.cyd.GuildHelper.Repository.RaidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CharacterService {
    Logger logger = Logger.getLogger(CharacterService.class.getName());

    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private PlayerRepository playerRepository;

    public List<Character> getAllCharacters(){
        return characterRepository.findAll();
    }

    public List<Character> getCharactersByPlayer(Long id){
        Player player = playerRepository.findById(id).orElse(null);
        if(player == null){
            logger.severe("Invalid player ID: "+id);
        }
        List<Character> characters = characterRepository.findByPlayer(player);
        logger.info("Returning " + characters.size() + " Characters.");
        return characters;
    }

    public Character getCharacterById(Long id){
        return characterRepository.findById(id).orElse(null);
    }

    public Character createCharacter(Character character){
        assert character.getName() != null; //TODO: Properly check this shit
        return characterRepository.save(character);
    }

    public void deleteCharacter(Long id){
        logger.info("Deleting Character " + Objects.requireNonNull(characterRepository.findById(id).orElse(null)));
        characterRepository.deleteById(id);
    }

    public Character updateCharacter(CharacterDto characterDto){
        Character originalCharacter = characterRepository.findById(characterDto.getId()).orElse(null);
        Player player = playerRepository.findById(characterDto.getPlayerId()).orElse(null);
        if(originalCharacter == null){
            logger.severe("Request to update character was made with invalid character object " + characterDto);
            return null;
        }
        if(player == null){
            logger.severe("Request to update character was made with invalid player Id " + characterDto.getPlayerId());
            return null;
        }
        originalCharacter.setName(characterDto.getName());
        originalCharacter.setPlayer(player);
        originalCharacter.setClassification(characterDto.getClassification());
        return characterRepository.save(originalCharacter);
    }
}
