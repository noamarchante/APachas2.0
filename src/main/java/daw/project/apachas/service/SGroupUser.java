package daw.project.apachas.service;

import daw.project.apachas.converter.ConGroup;
import daw.project.apachas.converter.ConGroupUser;
import daw.project.apachas.converter.ConUser;
import daw.project.apachas.entity.Group;
import daw.project.apachas.entity.GroupUser;
import daw.project.apachas.entity.User;
import daw.project.apachas.model.MGroup;
import daw.project.apachas.model.MGroupUser;
import daw.project.apachas.model.MUser;
import daw.project.apachas.repository.RGroup;
import daw.project.apachas.repository.RGroupUser;
import daw.project.apachas.repository.RUser;
import daw.project.apachas.entity.id.GroupUserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service("SGroupUser")
public class SGroupUser {
    @Autowired
    @Qualifier("RGroupUser")
    private RGroupUser rGroupUser;

    @Autowired
    @Qualifier("RGroup")
    private RGroup rGroup;

    @Autowired
    @Qualifier("RUser")
    private RUser rUser;

    @Autowired
    @Qualifier("ConGroupUser")
    private ConGroupUser conGroupUser;

    @Autowired
    @Qualifier("ConGroup")
    private ConGroup conGroup;

    @Autowired
    @Qualifier("ConUser")
    private ConUser conUser;

    public synchronized boolean insertGroupUser(MGroupUser mGroupUser) {
        GroupUser groupUser = conGroupUser.conMGroupUser(mGroupUser);
        GroupUser existingGroupUser = rGroupUser.findByGroupUserId(groupUser.getGroupUserId());
        Group existingGroup = rGroup.findByGroupId(groupUser.getGroupUserId().getGroupId());
        User existingUser = rUser.findByUserId(groupUser.getGroupUserId().getUserId());
        if (existingGroupUser != null || existingGroup == null || existingUser == null) {
            return false;
        } else {
            groupUser.setGroupUserCreation(new Timestamp(System.currentTimeMillis()));
            groupUser.setGroupUserRemoval(null);
            groupUser.setGroupUserActive(true);
            groupUser.setGroup(existingGroup);
            groupUser.setUser(existingUser);
            rGroupUser.save(groupUser);
            return true;
        }
    }

    public synchronized boolean updateGroupUser(Long groupId, List<Long> userIdList) {
        Group existingGroup = rGroup.findByGroupId(groupId);
        if (existingGroup != null){
            List<GroupUser> existingGroupUserList = rGroupUser.findByGroupUserId_GroupId(groupId);
            existingGroupUserList.forEach(groupUser -> {
                if (!userIdList.contains(groupUser.getGroupUserId().getUserId())){
                    deleteGroupUser(groupUser.getGroupUserId().getGroupId(), groupUser.getGroupUserId().getUserId());
                }
                userIdList.remove(groupUser.getGroupUserId().getUserId());
            });

            userIdList.forEach(userId -> {
                insertGroupUser(new MGroupUser(new GroupUser(new GroupUserId(groupId, userId))));
            });
            return true;
        }else{
            return false;
        }
    }

    public synchronized boolean deleteGroupUser(long groupId, long userId) {
        GroupUser existingGroupUser = rGroupUser.findByGroupUserId(new GroupUserId(groupId, userId));
        Group existingGroup = rGroup.findByGroupId(groupId);
        User existingUser = rUser.findByUserId(userId);
        if (existingGroupUser != null || existingGroup != null || existingUser != null) {
            existingGroupUser.setGroupUserActive(false);
            existingGroupUser.setGroupUserRemoval(new Timestamp(System.currentTimeMillis()));
            rGroupUser.save(existingGroupUser);
            return true;
        } else {
            return false;
        }
    }

    public synchronized Long countMembers(long groupId){
        return rGroupUser.countByGroupUserId_GroupIdAndGroupUserActiveTrue(groupId);
    }

    public synchronized Long countGroups(long authId){
        return rGroupUser.countGroups(authId);
    }

    public synchronized Long countSearchGroups(long authId, String groupName){
        return rGroupUser.countSearchGroups(authId, groupName);
    }

    public synchronized Long countMutualGroups(long userId, long authId){
        return rGroupUser.countMutualGroups(userId, authId);
    }

    public synchronized List<MGroup> selectPageableGroups(Long authId, Pageable pageable) {
        List<Group> groupList = rGroupUser.findPageableGroups(authId, pageable).getContent();
        return conGroup.conGroupList(groupList);
    }

    public synchronized List<MUser> selectPageableMembers(long groupId, Pageable pageable) {
        List<User> userList = rGroupUser.findPageableMembers(groupId, pageable).getContent();
        return conUser.conUserList(userList);
    }

    public synchronized List<MGroup> selectPageableSearchGroups(String groupName, long authId, Pageable pageable) {
        return conGroup.conGroupList(rGroupUser.findPageableSearchGroups(authId, groupName, pageable).getContent());
    }


    public synchronized List<MGroup> selectPageableMutualGroups(long userId, long authId, Pageable pageable) {
        return conGroup.conGroupList(rGroupUser.findPageableMutualGroups(userId, authId, pageable).getContent());
    }

    public synchronized List<MUser> selectMembers(long groupId) {
        List<User> userList = rGroupUser.findMembers(groupId);
        return conUser.conUserList(userList);
    }

    public synchronized List<MGroup> selectGroups(long authId) {
        List<Group> groupList = rGroupUser.getGroups(authId);
        return conGroup.conGroupList(groupList);
    }
}
