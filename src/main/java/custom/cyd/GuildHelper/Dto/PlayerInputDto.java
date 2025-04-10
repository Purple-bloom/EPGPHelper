package custom.cyd.GuildHelper.Dto;

import custom.cyd.GuildHelper.Entity.Player;

public class PlayerInputDto {
    private String name;
    private String rank;

    public PlayerInputDto(Player player){
        name = player.getName();
        rank = player.getRank();
    }

    public PlayerInputDto(){
    }

    public PlayerInputDto(String name, String rank){
        this.name = name;
        this.rank = rank;
    }

    public String toString(){
        return String.format("Player Name: %s, Rank: %s",
                name, rank);
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
}
