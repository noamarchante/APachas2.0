package daw.project.apachas.entity;

import daw.project.apachas.entity.id.GroupUserId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "groupUser")
@Entity(name = "groupUser")
public class GroupUser implements Serializable {

    @EmbeddedId
    private GroupUserId groupUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("groupId")
    @JoinColumn(name = "groupId", referencedColumnName = "groupId", nullable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user;

    @Column(name = "groupUserCreation", nullable = false)
    private Timestamp groupUserCreation;

    @Column(name = "groupUserRemoval")
    private Timestamp groupUserRemoval;

    @Column(name = "groupUserActive", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    private boolean groupUserActive;

    public GroupUser(GroupUserId groupUserId) {
        this.groupUserId = groupUserId;
        this.setGroupUserCreation(new Timestamp(System.currentTimeMillis()));
        this.setGroupUserRemoval(null);
        this.setGroupUserActive(true);
    }
}
