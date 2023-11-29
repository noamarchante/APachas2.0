package daw.project.apachas.entity;

import daw.project.apachas.entity.id.UserUserEventId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "userUserEvent")
@Table(name = "userUserEvent")
public class UserUserEvent implements Serializable {

    @EmbeddedId
    private UserUserEventId userUserEventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("senderId")
    @JoinColumn(name = "senderId", referencedColumnName = "userId", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("receiverId")
    @JoinColumn(name = "receiverId", referencedColumnName = "userId", nullable = false)
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eventId")
    @JoinColumn(name = "eventId", referencedColumnName = "eventId", nullable = false)
    private Event event;

    @Column(name = "paid", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    private boolean paid;

    @Column(name = "confirmed", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    private boolean confirmed;

    @Column(name = "cost", nullable = false)
    private double cost;

    @Column(name = "userUserEventCreation", nullable = false)
    private Timestamp userUserEventCreation;

    @Column(name = "userUserEventRemoval")
    private Timestamp userUserEventRemoval;

    @Column(name = "userUserEventActive", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    private boolean userUserEventActive;

    public UserUserEvent(UserUserEventId userUserEventId, double cost, boolean paid, boolean confirmed) {
        this.userUserEventId = userUserEventId;
        this.setCost(cost);
        this.setPaid(paid);
        this.setConfirmed(confirmed);
        this.setUserUserEventActive(true);
        this.setUserUserEventCreation(new Timestamp(System.currentTimeMillis()));
        this.setUserUserEventRemoval(null);
    }
}