package daw.project.apachas.model;

import daw.project.apachas.entity.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Setter
@Getter
@NoArgsConstructor
public class MEvent {

    private long eventId;
    private String eventName;
    private String eventDescription;
    private Timestamp eventStart;
    private Timestamp eventEnd;
    private String eventLocation;
    private String eventPhoto;
    private boolean eventOpen;
    private long eventOwner;
    private boolean eventActive;
    private Timestamp eventCreation;
    private Timestamp eventRemoval;

    public MEvent(Event event) {
        this.eventId = event.getEventId();
        this.eventName = event.getEventName();
        this.eventDescription = event.getEventDescription();
        this.eventStart = event.getEventStart();
        this.eventEnd = event.getEventEnd();
        this.eventLocation = event.getEventLocation();
        this.eventPhoto = event.getEventPhoto();
        this.eventOpen = event.isEventOpen();
        this.eventOwner = event.getUser().getUserId();
        this.eventActive = event.isEventActive();
        this.eventCreation = event.getEventCreation();
        this.eventRemoval = event.getEventRemoval();
    }
}
