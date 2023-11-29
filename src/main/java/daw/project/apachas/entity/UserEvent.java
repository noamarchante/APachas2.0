package daw.project.apachas.entity;

import daw.project.apachas.entity.id.UserEventId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
@Setter
@Getter
@NoArgsConstructor
@Entity(name = "userEvent")
@Table(name = "userEvent")
public class UserEvent implements Serializable {
    @EmbeddedId
    private UserEventId userEventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eventId")
    @JoinColumn(name = "eventId", referencedColumnName = "eventId", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user;

    @Column(name = "accept", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    private boolean accept;

    @Column(name = "totalExpense", length = 12, nullable = false)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 12)
    @ColumnDefault(value = "0.0")
    private Double totalExpense;

    @Column(name = "userEventCreation", nullable = false)
    private Timestamp userEventCreation;

    @Column(name = "userEventRemoval")
    private Timestamp userEventRemoval;

    @Column(name = "userEventActive", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    private boolean userEventActive;


    @Column(name = "debt", length = 12, nullable = false)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 12)
    @ColumnDefault(value = "0")
    private Double debt;

    public UserEvent(UserEventId userEventId, Double totalExpense, Double debt, boolean accept) {
        this.userEventId = userEventId;
        this.setTotalExpense(totalExpense);
        this.setDebt(debt);
        this.setAccept(accept);
        this.setUserEventActive(false);
        this.setUserEventRemoval(null);
        this.setUserEventCreation(new Timestamp(System.currentTimeMillis()));
    }
}
