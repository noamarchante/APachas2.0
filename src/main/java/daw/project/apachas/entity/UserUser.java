package daw.project.apachas.entity;

import daw.project.apachas.entity.id.UserUserId;
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
@Entity(name = "userUser")
@Table(name = "userUser")
public class UserUser implements Serializable {

    @EmbeddedId
    private UserUserId userUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("friendId")
    @JoinColumn(name = "friendId", referencedColumnName = "userId", nullable = false)
    private User friend;

    @Column(name = "accept", nullable = false)
    private boolean accept;

    @Column(name = "userUserCreation", nullable = false)
    private Timestamp userUserCreation;

    @Column(name = "userUserRemoval")
    private Timestamp userUserRemoval;

    //ATRIBUTO: USUARIO DE EVENTO ACTIVO (SI SE HA ELIMINADO O NO)
    @Column(name = "userUserActive", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    private boolean userUserActive;
    public UserUser(UserUserId userUserId, boolean accept) {
        this.userUserId = userUserId;
        this.accept = accept;
        this.setUserUserActive(true);
        this.setUserUserCreation(new Timestamp(System.currentTimeMillis()));
        this.setUserUserRemoval(null);
    }
}
