package custom.cyd.GuildHelper.Entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "bosses")
public class Boss implements Serializable {
    @Id
    @SequenceGenerator(name="bosses_seq", sequenceName="bosses_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="bosses_seq")
    private Long id;
    private String name;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "raid", referencedColumnName = "id")
    private Raid raid;

    @Override
    public String toString(){
        return String.format("Boss ID: %d, name: %s, raid: %d",
                id, name, raid.getId());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Raid getRaid() {
        return raid;
    }

    public void setRaid(Raid raid) {
        this.raid = raid;
    }
}
