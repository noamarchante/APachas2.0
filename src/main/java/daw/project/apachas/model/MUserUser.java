package daw.project.apachas.model;


import daw.project.apachas.entity.UserUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Setter
@Getter
@NoArgsConstructor
public class MUserUser {

    private long userId;
    private long friendId;
    private boolean accept;
    private Timestamp userUserCreation;
    private Timestamp userUserRemoval;
    private boolean userUserActive;

    public MUserUser (UserUser userUser){
        this.userId = userUser.getUserUserId().getUserId();
        this.friendId = userUser.getUserUserId().getFriendId();
        this.accept = userUser.isAccept();
        this.userUserActive = userUser.isUserUserActive();
        this.userUserCreation = userUser.getUserUserCreation();
        this.userUserRemoval = userUser.getUserUserRemoval();
    }
}
