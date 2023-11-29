package daw.project.apachas.service;

import daw.project.apachas.converter.ConUser;
import daw.project.apachas.converter.ConUserUser;
import daw.project.apachas.entity.User;
import daw.project.apachas.entity.UserUser;
import daw.project.apachas.model.MUser;
import daw.project.apachas.model.MUserUser;
import daw.project.apachas.repository.RUser;
import daw.project.apachas.repository.RUserUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import daw.project.apachas.entity.id.UserUserId;

import java.sql.Timestamp;
import java.util.List;

@Service("SUserUser")
public class SUserUser {
    @Autowired
    @Qualifier("RUserUser")
    private RUserUser rUserUser;

    @Autowired
    @Qualifier("RUser")
    private RUser rUser;

    @Autowired
    @Qualifier("ConUserUser")
    private ConUserUser conUserUser;

    @Autowired
    @Qualifier("ConUser")
    private ConUser conUser;

    public synchronized boolean insertUserUser(MUserUser mUserUser) {
        UserUser userUser = conUserUser.conMUserUser(mUserUser);
        UserUser existingUserUser = rUserUser.findByUserUserId(userUser.getUserUserId());
        User existingUser = rUser.findByUserId(userUser.getUserUserId().getUserId());
        User existingFriend = rUser.findByUserId(userUser.getUserUserId().getFriendId());
        if (existingUserUser != null || existingUser == null || existingFriend == null) {
            return false;
        } else {
            userUser.setAccept(false);
            userUser.setUserUserActive(true);
            userUser.setUserUserCreation(new Timestamp(System.currentTimeMillis()));
            userUser.setUserUserRemoval(null);
            userUser.setUser(existingUser);
            userUser.setFriend(existingFriend);
            rUserUser.save(userUser);
            return true;
        }
    }

    public synchronized boolean updateUserUser(MUserUser mUserUser) {
        UserUser userUser = conUserUser.conMUserUser(mUserUser);
        UserUser existingUserUser = rUserUser.findByUserUserId(userUser.getUserUserId());
        User existingUser = rUser.findByUserId(userUser.getUserUserId().getUserId());
        User existingFriend = rUser.findByUserId(userUser.getUserUserId().getFriendId());
        if (existingUserUser != null || existingUser != null || existingFriend != null) {
            rUserUser.save(userUser);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean deleteUserUser(long friendId, long userId) {
        UserUser existingUserUser = rUserUser.findByUserUserId(new UserUserId(friendId, userId));
        if (existingUserUser == null){
            existingUserUser = rUserUser.findByUserUserId(new UserUserId(userId, friendId));
        }
        User existingFriend = rUser.findByUserId(friendId);
        User existingUser = rUser.findByUserId(userId);
        if (existingUserUser != null && existingFriend != null && existingUser != null) {
            existingUserUser.setAccept(false);
            existingUserUser.setUserUserActive(false);
            existingUserUser.setUserUserRemoval(new Timestamp(System.currentTimeMillis()));
            rUserUser.save(existingUserUser);
            return true;
        } else {
            return false;
        }
    }

    public synchronized Long countMutualFriends( long userId, long authId){
        Long countUsers = rUserUser.countMutualFriends(userId, authId);
        return countUsers;
    }

    public synchronized List<MUser> selectPageableMutualFriends(long userId, long authId, Pageable pageable) {
        List<User> userList = rUserUser.findPageableMutualFriends(userId, authId, pageable).getContent();
        return conUser.conUserList(userList);
    }

    public synchronized MUserUser selectUserUser(UserUserId userUserId) {
        UserUser userUser = rUserUser.findById(userUserId).get();
        return conUserUser.conUserUser(userUser);
    }

    public synchronized String[] selectNotifications(long authId) {
       return rUserUser.findNotifications(authId);
    }

    public synchronized List<MUser> selectFriendsByFriendId(long friendId) {
        List<User> userList = rUserUser.findFriendsByFriendId(friendId);
        return conUser.conUserList(userList);
    }

    public synchronized List<MUser> selectFriendsByUserId(long userId) {
        List<User> userList = rUserUser.findFriendsByUserId(userId);
        return conUser.conUserList(userList);
    }
}
