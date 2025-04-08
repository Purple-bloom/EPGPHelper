package custom.cyd.GuildHelper.RelationEntity;

import custom.cyd.GuildHelper.Entity.Boss;
import custom.cyd.GuildHelper.Entity.Player;
import custom.cyd.GuildHelper.Entity.Raidrun;
import jakarta.persistence.*;

@Entity
@Table(name = "raidruns_players")
public class RelationRaidrunPlayer {

    @Id
    @SequenceGenerator(name="raidruns_players_seq", sequenceName="raidruns_players_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="raidruns_players_seq")
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "raidrun", referencedColumnName = "id")
    private Raidrun raidrun;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "boss", referencedColumnName = "id")
    private Player player;

    public Long getId() {
        return id;
    }

    public Raidrun getRaidrun() {
        return raidrun;
    }

    public void setRaidrun(Raidrun raidrun) {
        this.raidrun = raidrun;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
