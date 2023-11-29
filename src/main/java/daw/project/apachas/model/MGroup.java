package daw.project.apachas.model;


import daw.project.apachas.entity.Group;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Setter
@Getter
@NoArgsConstructor
public class MGroup {

    private long groupId;
    private String groupName;
    private String groupDescription;
    private String groupPhoto;
    private long groupOwner;
    private Timestamp groupCreation;
    private Timestamp groupRemoval;
    private boolean groupActive;

    public MGroup(Group group){
        this.groupId = group.getGroupId();
        this.groupName = group.getGroupName();
        this.groupDescription = group.getGroupDescription();
        this.groupPhoto = group.getGroupPhoto();
        this.groupCreation = group.getGroupCreation();
        this.groupOwner = group.getUser().getUserId();
        this.groupRemoval = group.getGroupRemoval();
        this.groupActive = group.isGroupActive();
    }
}
