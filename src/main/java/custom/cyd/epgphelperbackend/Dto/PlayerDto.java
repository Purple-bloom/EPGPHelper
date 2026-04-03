package custom.cyd.epgphelperbackend.Dto;

import custom.cyd.epgphelperbackend.Entity.Player;

public class PlayerDto {
    private Long id;
    private String name;
    private double ep;
    private double gp;
    private double prio;
    private boolean active;
    private CharacterDto[] characters;

    public PlayerDto(Player player, CharacterDto[] characters){
        id = player.getId();
        name = player.getName();
        ep = player.getEp();
        gp = player.getGp();
        prio = ep/gp;
        active = player.getActive();
        this.characters = characters;
    }

    public String toString(){
        return String.format("Player ID: %d, Name: %s, EP: %f, GP: %f, Changed: %s",
                id, name, ep, gp, active);
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

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public CharacterDto[] getCharacters() {
        return characters;
    }

    public void setCharacters(CharacterDto[] characters) {
        this.characters = characters;
    }
}
