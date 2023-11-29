package daw.project.apachas.controller;

import daw.project.apachas.model.MGroup;
import daw.project.apachas.model.MUser;
import daw.project.apachas.service.SGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import daw.project.apachas.entity.id.GroupUserId;
import daw.project.apachas.model.MGroupUser;
import daw.project.apachas.service.SGroupUser;
import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/groupsUsers")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.OPTIONS})
public class CGroupUser {
    @Autowired
    @Qualifier("SGroupUser")
    SGroupUser sGroupUser;
    @Autowired
    @Qualifier("SGroup")
    SGroup sGroup;

    @PostMapping
    public ResponseEntity<Void> createGroupUser(@RequestBody @Valid MGroupUser mGroupUser, UriComponentsBuilder builder) {
        boolean flag = sGroupUser.insertGroupUser(mGroupUser);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/groupsUsers/{groupUserId}").buildAndExpand(new GroupUserId(mGroupUser.getGroupId(), mGroupUser.getUserId())).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<Void> editGroupUser(@PathVariable("groupId") long groupId, @RequestBody @Valid List<Long> userIdList) {
        boolean flag = sGroupUser.updateGroupUser(groupId, userIdList);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{groupId}/{userId}")
    public ResponseEntity<Void> deleteGroupUser(@PathVariable("groupId") long groupId, @PathVariable("userId") long userId) {
        boolean flag = sGroupUser.deleteGroupUser(groupId, userId);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/count/members/{groupId}")
    public ResponseEntity<Long> countMembers(@PathVariable("groupId") long groupId) {
        long groupUserCount = sGroupUser.countMembers(groupId);
        return new ResponseEntity<>(groupUserCount, HttpStatus.OK);
    }

    @GetMapping("/count/{authId}")
    public ResponseEntity<Long> countGroups(@PathVariable("authId") long authId) {
        long userCount = sGroupUser.countGroups(authId);
        return new ResponseEntity<>(userCount, HttpStatus.OK);
    }

    @GetMapping("/count/{authId}/{groupName}")
    public ResponseEntity<Long> countSearchGroups(@PathVariable("groupName") String groupName, @PathVariable("authId") long authId) {
        long userCount = sGroupUser.countSearchGroups(authId, groupName);
        return new ResponseEntity<>(userCount, HttpStatus.OK);
    }

    @GetMapping("/count/mutual/{userId}/{authId}")
    public ResponseEntity<Long> countMutualGroups(@PathVariable("userId") long userId, @PathVariable("authId") long authId) {
        long groupCount = sGroupUser.countMutualGroups(userId,authId);
        return new ResponseEntity<>(groupCount, HttpStatus.OK);
    }

    @GetMapping("/pageable/members/{groupId}")
    public ResponseEntity<List<MUser>> getPageableMembers(@PathVariable("groupId") long groupId, Pageable pageable) {
        List<MUser> mUserList = sGroupUser.selectPageableMembers(groupId, pageable);
        return new ResponseEntity<>(mUserList, HttpStatus.OK);
    }

    @GetMapping("/pageable/{groupName}/{authId}")
    public ResponseEntity<List<MGroup>> getPageableSearchGroups(@PathVariable("groupName") String groupName, @PathVariable("authId") long authId, Pageable pageable) {
        List<MGroup> groupList = sGroupUser.selectPageableSearchGroups(groupName,authId,pageable);
        return new ResponseEntity<>(groupList, HttpStatus.OK);
    }

    @GetMapping("/pageable/{authId}")
    public ResponseEntity<List<MGroup>> getPageableGroups(@PathVariable("authId") long authId, Pageable pageable) {
        List<MGroup> mGroupList = sGroupUser.selectPageableGroups(authId, pageable);
        return new ResponseEntity<>(mGroupList, HttpStatus.OK);
    }

    @GetMapping("/pageable/mutual/{userId}/{authId}")
    public ResponseEntity<List<MGroup>> getPageableMutualGroups(@PathVariable("userId") long userId, @PathVariable("authId") long authId, Pageable pageable) {
        List<MGroup> groupList = sGroupUser.selectPageableMutualGroups(userId, authId,pageable);
        return new ResponseEntity<>(groupList, HttpStatus.OK);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<List<MUser>> getMembers(@PathVariable("groupId") long groupId) {
        List<MUser> mUserList = sGroupUser.selectMembers(groupId);
        return new ResponseEntity<>(mUserList, HttpStatus.OK);
    }

    @GetMapping("/groups/{authId}")
    public ResponseEntity<List<MGroup>> getGroups(@PathVariable("authId") long authId) {
        try {
            List<MGroup> mGroupList = sGroupUser.selectGroups(authId);
            return new ResponseEntity<>(mGroupList, HttpStatus.OK);
        }catch ( NoSuchElementException e){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }
}
