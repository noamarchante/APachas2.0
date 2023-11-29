package daw.project.apachas.model;


import daw.project.apachas.entity.GroupUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Setter
@Getter
@NoArgsConstructor
public class MGroupUser {
    private long groupId;
    private long userId;
    private Timestamp groupUserRemoval;
    private Timestamp groupUserCreation;
    private boolean groupUserActive;

    public MGroupUser(GroupUser groupUser) {
        this.groupId = groupUser.getGroupUserId().getGroupId();
        this.userId = groupUser.getGroupUserId().getUserId();
        this.groupUserCreation = groupUser.getGroupUserCreation();
        this.groupUserRemoval = groupUser.getGroupUserRemoval();
        this.groupUserActive = groupUser.isGroupUserActive();
    }
}
