package custom.cyd.GuildHelper.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "raidruns")
public class Raidrun {
    @Id
    @SequenceGenerator(name="raidruns_seq", sequenceName="raidruns_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="raidruns_seq")
    private Long id;
    @ManyToOne
    private Raid raid;

    @Override
    public String toString(){
        return String.format("Raidrun ID: %d, raid: %s",
                id, raid.toString());
    }

    public Long getId() {
        return id;
    }

    public Raid getRaid() {
        return raid;
    }

    public void setRaid(Raid raid) {
        this.raid = raid;
    }
}
