package custom.cyd.GuildHelper.Entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "characters")
public class Character implements Serializable {
    @Id
    @SequenceGenerator(name="characters_seq", sequenceName="characters_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="characters_seq")
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "player", referencedColumnName = "id")
    private Player player;
    private String name;
    private String classification;

    public Long getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
