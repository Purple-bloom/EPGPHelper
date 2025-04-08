package custom.cyd.GuildHelper.Entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Items")
public class Item implements Serializable {
    @Id
    @SequenceGenerator(name="items_seq", sequenceName="items_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="items_seq")
    private Long id;
    private String name;
    @Enumerated()
    private Rarity rarity;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }
}
