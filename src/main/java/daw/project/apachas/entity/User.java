package daw.project.apachas.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "user")
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "userId")
    private long userId;

    @Column(name = "userName", length = 50, nullable = false)
    @Size(min = 3, max = 50)
    @NotNull
    @NotBlank
    private String userName;

    @Column(name = "userSurname", length = 50, nullable = false)
    @Size(min = 4, max = 50)
    @NotNull
    @NotBlank
    private String userSurname;

    @Column(name = "userLogin", length = 14, nullable = false, unique = true)
    @Size(min = 4, max = 15)
    @NotBlank
    @NotNull
    private String userLogin;

    @Column(name = "userPassword", nullable = false)
    @NotNull
    @NotBlank
    private String userPassword;

    @Column(name = "userEmail", length = 320, nullable = false, unique = true)
    @NotNull
    @NotBlank
    @Email
    private String userEmail;

    @Column(name = "userBirthday")
    private Timestamp userBirthday;

    @Lob
    @Column(name = "userPhoto", length = 100000)
    private String userPhoto;

    @Column(name = "roles", nullable = false)
    private String roles = "";

    @Column(name = "permissions")
    private String permissions = "";

    @Column(name = "userCreation")
    private Timestamp userCreation;

    @Column(name = "userRemoval")
    private Timestamp userRemoval;

    @Column(name = "userActive", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    private boolean userActive;

    @Column(name = "userVerified", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    private boolean userVerified;

    @Column(name = "tokenPassword")
    private String tokenPassword;

    @Column(name = "userNotify", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    private boolean userNotify;

    //N:M USUARIO RELACIONADO CON USUARIO
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Transient
    private Set<UserUser> userUserSet = new HashSet<>();

    //N:M USUARIO RELACIONADO CON USUARIO
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Transient
    private Set<UserUser> userFriendSet = new HashSet<>();

    //N:M USUARIO PARTICIPA EN EVENTO
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Transient
    private Set<UserEvent> userEventSet = new HashSet<>();

    //1:N USUARIO CREA EVENTO
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Transient
    private Set<Event> eventSet = new HashSet<>();

    //N:M USUARIO INTEGRADO EN GRUPO
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Transient
    private Set<GroupUser> groupUserSet = new HashSet<>();

    public User(long userId){
        this.userId = userId;
    }

    public User(long userId, String userName, String userSurname, String userLogin, String userPassword, String userEmail, Timestamp userBirthday, String userPhoto, String roles, String permissions) {
        this.userId = userId;
        this.setUserName(userName);
        this.setUserSurname(userSurname);
        this.setUserLogin(userLogin);
        this.setUserPassword(userPassword);
        this.setUserEmail(userEmail);
        this.setUserBirthday(userBirthday);
        this.setUserPhoto(userPhoto);
        this.setPermissions(permissions);
        this.setRoles(roles);
        this.setUserCreation(new Timestamp(System.currentTimeMillis()));
        this.setUserRemoval(null);
        this.setUserActive(true);
        this.setUserVerified(false);
        this.setUserNotify(true);
    }

    public List<String> getRoleList(){
        if(!this.roles.isEmpty()){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public List<String> getPermissionList(){
        if(!this.permissions.isEmpty()){
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }
}
