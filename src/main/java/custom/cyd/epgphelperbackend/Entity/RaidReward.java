package custom.cyd.epgphelperbackend.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "raids_rewards")
public class RaidReward {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "raid", referencedColumnName = "id")
    private Raid raid;
    @Column(name = "rewardtype")
    private String rewardType;
    @Column(name = "rewardvalue")
    private Integer rewardValue;

    @Override
    public String toString(){
        return id + " " + raid.getName() + " " + rewardType + " " + rewardValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Raid getRaid() {
        return raid;
    }

    public void setRaid(Raid raid) {
        this.raid = raid;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    public Integer getRewardValue() {
        return rewardValue;
    }

    public void setRewardValue(Integer rewardValue) {
        this.rewardValue = rewardValue;
    }
}
