package custom.cyd.GuildHelper.RelationEntity;

import custom.cyd.GuildHelper.Entity.Boss;
import custom.cyd.GuildHelper.Entity.Item;
import custom.cyd.GuildHelper.Entity.Raid;
import jakarta.persistence.*;

@Entity
@Table(name = "items_bosses")
public class RelationItemBoss {

    @Id
    @SequenceGenerator(name="item_bosses_seq", sequenceName="item_bosses_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="item_bosses_seq")
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "item", referencedColumnName = "id")
    private Item item;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "boss", referencedColumnName = "id")
    private Boss boss;

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Boss getBoss() {
        return boss;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }
}
