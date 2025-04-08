package custom.cyd.GuildHelper.Dto;

import custom.cyd.GuildHelper.Entity.Player;

import java.io.Serializable;

public class CharacterDto implements Serializable {
    private Long id;
    private Long playerId;
    private String name;
    private String classification;

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
}
