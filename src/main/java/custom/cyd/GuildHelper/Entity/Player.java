package custom.cyd.GuildHelper.Entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "players")
public class Player implements Serializable {
    @Id
    @SequenceGenerator(name="players_seq", sequenceName="players_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="players_seq")
    private Long id;
    private String name;
    private String rank;
    private double ep;
    private double gp;
    private boolean active;

    @Override
    public String toString(){
        return String.format("Player ID: %d, Name: %s, Rank: %s, EP: %f, GP: %f, changed: %s",
                id, name, rank, ep, gp, active);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public double getEp() {
        return ep;
    }

    public void setEp(double ep) {
        this.ep = ep;
    }

    public double getGp() {
        return gp;
    }

    public void setGp(double gp) {
        this.gp = gp;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
