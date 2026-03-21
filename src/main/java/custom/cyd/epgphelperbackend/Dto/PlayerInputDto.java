package custom.cyd.epgphelperbackend.Dto;

import custom.cyd.epgphelperbackend.Entity.Player;

public class PlayerInputDto {
    private String name;

    public PlayerInputDto(Player player){
        name = player.getName();
    }

    public PlayerInputDto(){
    }

    public PlayerInputDto(String name){
        this.name = name;
    }

    public String toString(){
        return String.format("Player Name: %s", name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
