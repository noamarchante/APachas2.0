package daw.project.apachas.model;

import daw.project.apachas.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Setter
@Getter
@NoArgsConstructor
public class MUser {

    private long userId;
    private String userName;
    private String userSurname;
    private String userLogin;
    private String userPassword;
    private String userEmail;
    private Timestamp userBirthday;
    private String userPhoto;
    private String roles;
    private String permissions;
    private Timestamp userCreation;
    private Timestamp userRemoval;
    private boolean userActive;
    private boolean userVerified;
    private boolean userNotify;

    public MUser(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userSurname = user.getUserSurname();
        this.userLogin = user.getUserLogin();
        this.userPassword = user.getUserPassword();
        this.userEmail = user.getUserEmail();
        this.userBirthday = user.getUserBirthday();
        this.userPhoto = user.getUserPhoto();
        this.roles = user.getRoles();
        this.permissions = user.getPermissions();
        this.userCreation = user.getUserCreation();
        this.userRemoval = user.getUserRemoval();
        this.userActive = user.isUserActive();
        this.userVerified = user.isUserVerified();
        this.userNotify = user.isUserNotify();
    }
}
