package daw.project.apachas.converter;

import daw.project.apachas.entity.UserUser;
import org.springframework.stereotype.Component;
import daw.project.apachas.entity.id.UserUserId;
import daw.project.apachas.model.MUserUser;
import java.util.ArrayList;
import java.util.List;

@Component("ConUserUser")
public class ConUserUser {
    public List<MUserUser> conUserUserList(List<UserUser> userUserList) {
        List<MUserUser> mUserUserList = new ArrayList<>();
        for (UserUser userUser : userUserList) {
            mUserUserList.add(new MUserUser(userUser));
        }
        return mUserUserList;
    }

    public MUserUser conUserUser(UserUser userUser) {
        return new MUserUser(userUser);
    }

    public UserUser conMUserUser(MUserUser mUserUser) {
        return new UserUser(new UserUserId(mUserUser.getFriendId(), mUserUser.getUserId()), mUserUser.isAccept());
    }
}
