package daw.project.apachas.entity;

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
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "uGroup")
@Table(name = "uGroup")
public class Group implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "groupId")
    private long groupId;

    @Column(name = "groupName", length = 50, nullable = false)
    @Size(min = 4, max = 50)
    @NotNull
    @NotBlank
    private String groupName;

    @Column(name = "groupDescription", length = 155)
    @Size(min=0,max = 155)
    private String groupDescription;

    @Lob
    @Column(name = "groupPhoto", length = 100000)
    private String groupPhoto;

    @Column(name = "groupCreation", nullable = false)
    private Timestamp groupCreation;

    @Column(name = "groupRemoval")
    private Timestamp groupRemoval;

    @Column(name = "groupActive", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    private boolean groupActive;

    @NotNull
    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupOwner", referencedColumnName = "userId")
    private User user;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    @Transient
    private Set<GroupUser> groupUserSet = new HashSet<>();

    public Group(long groupId, String groupName, String groupDescription, String groupPhoto, User user) {
        this.groupId = groupId;
        this.setGroupName(groupName);
        this.setGroupDescription(groupDescription);
        this.setGroupPhoto(groupPhoto);
        this.setUser(user);
        this.setGroupCreation(new Timestamp(System.currentTimeMillis()));
        this.setGroupRemoval(null);
        this.setGroupActive(true);
    }
}
