package daw.project.apachas.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
@Setter
@Getter
@NoArgsConstructor
@Entity(name = "event")
@Table(name = "event")
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "eventId")
    private long eventId;

    @Column(name = "eventName", nullable = false, length = 50)
    @Size(min = 4, max = 50)
    @NotNull
    @NotBlank
    private String eventName;

    @Column(name = "eventDescription", length = 155)
    @Size(min=0,max = 155)
    @NotBlank
    private String eventDescription;

    @Column(name = "eventStart", nullable = false, length = 19)
    private Timestamp eventStart;

    @Column(name = "eventLocation", length = 155, nullable = false)
    @Size(min = 4, max = 155)
    @NotNull
    @NotBlank
    private String eventLocation;

    @Column(name = "eventPhoto", length = 100000)
    private String eventPhoto;

    @Column(name = "eventOpen", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    private boolean eventOpen;

    @Column(name = "eventEnd", nullable = false, length = 19)
    private Timestamp eventEnd;

    @Column(name = "eventActive", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    private boolean eventActive;

    @Column(name = "eventCreation", length = 19, nullable = false)
    private Timestamp eventCreation;

    @Column(name = "eventRemoval", length = 19)
    private Timestamp eventRemoval;

    //N:1 EVENTO ES CREADO POR USUARIO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventOwner", referencedColumnName = "userId", nullable = false)
    @NotNull
    @NotBlank
    private User user;

    //1:N EVENTO CONTIENE ELEMENTO
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @Transient
    private Set<Product> productSet = new HashSet<>();

    //N:M EVENTO PARTICIPA USUARIO
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @Transient
    private Set<UserEvent> userEventSet = new HashSet<>();

    public Event(long eventId, String eventName, String eventDescription, Timestamp eventStart, Timestamp eventEnd, String eventLocation, String eventPhoto, User user) {
        this.eventId = eventId;
        this.setEventName(eventName);
        this.setEventDescription(eventDescription);
        this.setEventStart(eventStart);
        this.setEventEnd(eventEnd);
        this.setEventLocation(eventLocation);
        this.setEventPhoto(eventPhoto);
        this.setUser(user);
        this.setEventOpen(true);
        this.setEventActive(true);
        this.setEventCreation(new Timestamp(System.currentTimeMillis()));
        this.setEventRemoval(null);
    }
    public Event (long eventId){
        this.eventId = eventId;
    }
}
