package daw.project.apachas.model;


import daw.project.apachas.entity.UserEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Setter
@Getter
@NoArgsConstructor
public class MUserEvent {
    private long eventId;
    private long userId;
    private boolean accept;
    private Double totalExpense;
    private Double debt;
    private Timestamp userEventCreation;
    private Timestamp userEventRemoval;
    private boolean userEventActive;

    public MUserEvent(UserEvent userEvent) {
        this.eventId = userEvent.getUserEventId().getEventId();
        this.userId = userEvent.getUserEventId().getUserId();
        this.accept = userEvent.isAccept();
        this.totalExpense = userEvent.getTotalExpense();
        this.debt = userEvent.getDebt();
        this.userEventActive = userEvent.isUserEventActive();
        this.userEventCreation = userEvent.getUserEventCreation();
        this.userEventRemoval = userEvent.getUserEventRemoval();
    }
}
