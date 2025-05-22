package custom.cyd.GuildHelper.Entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "Logs")
public class Log {
    @Id
    @SequenceGenerator(name="logs_seq", sequenceName="logs_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="logs_seq")
    private Long id;
    private Timestamp time;
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
