package custom.cyd.GuildHelper.Entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "raids")
public class Raid implements Serializable {
    @Id
    @SequenceGenerator(name="raids_seq", sequenceName="raids_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="raids_seq")
    private Long id;
    private String name;

    @Override
    public String toString(){
        return String.format("Raid ID: %d, name: %s",
                id, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
