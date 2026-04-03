package custom.cyd.epgphelperbackend.Dto;

import custom.cyd.epgphelperbackend.Entity.Character;

import java.io.Serializable;

public class CharacterDto implements Serializable {
    private Long id;
    private Long playerId;
    private String name;
    private String classification;
    private String characterClass;

    public CharacterDto(Character character){
        this.id = character.getId();
        this.playerId = character.getPlayer().getId();
        this.name = character.getName();
        this.classification = character.getClassification();
        this.characterClass = character.getCharacterClass();
    }

    public CharacterDto(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }
}
