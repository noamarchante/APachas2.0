package daw.project.apachas.converter;

import daw.project.apachas.entity.User;
import daw.project.apachas.model.MUser;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component("ConUser")
public class ConUser {

    public List<MUser> conUserList(List<User> userList) {
        List<MUser> mUserList = new ArrayList<>();
        for (User user : userList) {
            mUserList.add(new MUser(user));
        }
        return mUserList;
    }

    public MUser conUser(User user) {
        return new MUser(user);
    }

    public User conMUser(MUser mUser) {
        return new User(mUser.getUserId(), mUser.getUserName(), mUser.getUserSurname(), mUser.getUserLogin(), mUser.getUserPassword(), mUser.getUserEmail(), mUser.getUserBirthday(), mUser.getUserPhoto(), mUser.getRoles(), mUser.getPermissions());
    }


}
