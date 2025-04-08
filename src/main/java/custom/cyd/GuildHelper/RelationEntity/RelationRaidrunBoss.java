package custom.cyd.GuildHelper.RelationEntity;

import custom.cyd.GuildHelper.Entity.Boss;
import custom.cyd.GuildHelper.Entity.Item;
import custom.cyd.GuildHelper.Entity.Raidrun;
import jakarta.persistence.*;

@Entity
@Table(name = "raidruns_bosses")
public class RelationRaidrunBoss {

    @Id
    @SequenceGenerator(name="raidruns_bosses_seq", sequenceName="raidruns_bosses_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="raidruns_bosses_seq")
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "raidrun", referencedColumnName = "id")
    private Raidrun raidrun;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "boss", referencedColumnName = "id")
    private Boss boss;
    private boolean defeated;

    public Long getId() {
        return id;
    }

    public Raidrun getRaidrun() {
        return raidrun;
    }

    public void setRaidrun(Raidrun raidrun) {
        this.raidrun = raidrun;
    }

    public Boss getBoss() {
        return boss;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    public boolean isDefeated() {
        return defeated;
    }

    public void setDefeated(boolean defeated) {
        this.defeated = defeated;
    }
}
