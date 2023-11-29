package daw.project.apachas.converter;

import daw.project.apachas.entity.GroupUser;
import org.springframework.stereotype.Component;
import daw.project.apachas.entity.id.GroupUserId;
import daw.project.apachas.model.MGroupUser;
import java.util.ArrayList;
import java.util.List;

@Component("ConGroupUser")
public class ConGroupUser {
    public List<MGroupUser> conGroupUserList(List<GroupUser> groupUserList) {
        List<MGroupUser> mGroupUserList = new ArrayList<>();
        for (GroupUser groupUser : groupUserList) {
            mGroupUserList.add(new MGroupUser(groupUser));
        }
        return mGroupUserList;
    }

    public MGroupUser conGroupUser(GroupUser groupUser) {
        return new MGroupUser(groupUser);
    }

    public GroupUser conMGroupUser(MGroupUser mGroupUser) {
        return new GroupUser(new GroupUserId(mGroupUser.getGroupId(), mGroupUser.getUserId()));
    }
}
