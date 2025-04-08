package custom.cyd.GuildHelper.Dto;

import custom.cyd.GuildHelper.Entity.Player;

public class PlayerInputDto {
    private Long id;
    private String name;
    private String rank;
    private double ep;
    private double gp;
    private double prio;
    private boolean changed;

    public PlayerInputDto(Player player){
        id = player.getId();
        name = player.getName();
        rank = player.getRank();
        ep = player.getEp();
        gp = player.getGp();
        prio = ep/gp;
    }

    public String toString(){
        return String.format("Player ID: %d, Name: %s, Rank: %s, EP: %f, GP: %f, Changed: %s",
                id, name, rank, ep, gp, changed);
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

    public double getPrio() {
        return prio;
    }

    public void setPrio(double prio) {
        this.prio = prio;
    }
}
