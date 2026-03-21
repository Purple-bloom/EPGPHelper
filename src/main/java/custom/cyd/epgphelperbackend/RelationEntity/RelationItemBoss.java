package custom.cyd.epgphelperbackend.RelationEntity;

import custom.cyd.epgphelperbackend.Entity.Boss;
import custom.cyd.epgphelperbackend.Entity.Item;
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
