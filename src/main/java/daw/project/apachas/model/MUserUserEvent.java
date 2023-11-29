package daw.project.apachas.model;

import daw.project.apachas.entity.UserUserEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Setter
@Getter
@NoArgsConstructor
public class MUserUserEvent {

    private long senderId;
    private long receiverId;
    private long eventId;
    private boolean paid;
    private boolean confirmed;
    private double cost;
    private Timestamp userUserEventCreation;
    private Timestamp userUserEventRemoval;
    private boolean userUserEventActive;

    public MUserUserEvent (UserUserEvent userUserEvent){
        this.senderId = userUserEvent.getUserUserEventId().getSenderId();
        this.receiverId = userUserEvent.getUserUserEventId().getReceiverId();
        this.eventId = userUserEvent.getUserUserEventId().getEventId();
        this.paid = userUserEvent.isPaid();
        this.confirmed = userUserEvent.isConfirmed();
        this.cost = userUserEvent.getCost();
        this.userUserEventActive = userUserEvent.isUserUserEventActive();
        this.userUserEventCreation = userUserEvent.getUserUserEventCreation();
        this.userUserEventRemoval = userUserEvent.getUserUserEventRemoval();
    }
}
