package custom.cyd.GuildHelper.Controller;

import custom.cyd.GuildHelper.Dto.CharacterDto;
import custom.cyd.GuildHelper.Entity.Character;
import custom.cyd.GuildHelper.Service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/character")
public class CharacterController {
    Logger logger = Logger.getLogger(CharacterController.class.getName());

    @Autowired
    private CharacterService characterService;

    @GetMapping("/get")
    public List<Character> getAllCharacters(){
        return characterService.getAllCharacters();
    }

    @GetMapping("/get/{id}")
    public Character getCharacter(@PathVariable("id") Long id){
        return characterService.getCharacterById(id);
    }

    @GetMapping(
            value = "/get/forplayer/{id}",
            produces = "application/json"
    )
    public List<Character> getCharactersForPlayer(@PathVariable("id") Long id){
        logger.info("Getting characters for player ID: " + id);
        return characterService.getCharactersByPlayer(id);
    }

    @PostMapping(
            value = "/create",
            consumes = "application/json",
            produces = "application/json"
    )
    public Character createCharacter(@RequestBody Character character){
        logger.info(String.format("Received POST Request to create a Character. Char name: %s Player: %s",
                character.getName(), character.getPlayer().toString()));
        return characterService.createCharacter(character);
    }

    @PostMapping(
            value = "/delete/{id}"
    )
    public void deleteCharacter(@PathVariable("id") Long id){
        logger.info("Received POST Request to delete a Character with ID " + id);
        characterService.deleteCharacter(id);
    }

    @PostMapping(
            value = "/update",
            consumes = "application/json",
            produces = "application/json"
    )
    public Character updateCharacter(@RequestBody CharacterDto characterDto){
        logger.info(String.format("Received POST Request to update a Character. Char name: %s Player: %d",
                characterDto.getName(), characterDto.getPlayerId()));
        return characterService.updateCharacter(characterDto);
    }
}
