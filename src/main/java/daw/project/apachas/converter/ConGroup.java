package daw.project.apachas.converter;

import daw.project.apachas.entity.Group;
import daw.project.apachas.entity.User;
import org.springframework.stereotype.Component;
import daw.project.apachas.model.MGroup;
import java.util.ArrayList;
import java.util.List;

@Component("ConGroup")
public class ConGroup {

    public List<MGroup> conGroupList(List<Group> groupList) {
        List<MGroup> mGroupList = new ArrayList<>();
        for (Group group : groupList) {
            mGroupList.add(new MGroup(group));
        }
        return mGroupList;
    }

    public MGroup conGroup(Group group) {
        return new MGroup(group);
    }

    public Group conMGroup(MGroup mGroup) {
        return new Group(mGroup.getGroupId(), mGroup.getGroupName(), mGroup.getGroupDescription(), mGroup.getGroupPhoto(), new User(mGroup.getGroupOwner()));
    }

}
