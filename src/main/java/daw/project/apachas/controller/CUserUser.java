package daw.project.apachas.controller;

import daw.project.apachas.model.MUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import daw.project.apachas.entity.id.UserUserId;
import daw.project.apachas.model.MUserUser;
import daw.project.apachas.service.SUserUser;
import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/usersUsers")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS, RequestMethod.DELETE})
public class CUserUser {
    @Autowired
    @Qualifier("SUserUser")
    SUserUser sUserUser;

    @PostMapping
    public ResponseEntity<Void> createUserUser(@RequestBody @Valid MUserUser mUserUser, UriComponentsBuilder builder) {
        boolean flag = sUserUser.insertUserUser(mUserUser);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/usersUsers/{friendId}/{userId}").buildAndExpand(mUserUser.getFriendId(), mUserUser.getUserId()).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    @PutMapping
    public ResponseEntity<MUserUser> editUserUser(@RequestBody @Valid MUserUser mUserUser) {
        boolean flag = sUserUser.updateUserUser(mUserUser);
        if (!flag) {
            return new ResponseEntity<>(mUserUser, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(mUserUser, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{friendId}/{userId}")
    public ResponseEntity<Void> deleteUserUser(@PathVariable("friendId") long friendId, @PathVariable("userId") long userId) {
        boolean flag = sUserUser.deleteUserUser(friendId,userId);
        if (!flag) {
            boolean flag2 = sUserUser.deleteUserUser(userId, friendId);
            if (!flag2) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/count/mutual/{userId}/{authId}")
    public ResponseEntity<Long> countMutualFriends(@PathVariable("userId") long userId, @PathVariable("authId") long authId) {
        long userCount = sUserUser.countMutualFriends(userId,authId);
        return new ResponseEntity<>(userCount, HttpStatus.OK);
    }

    @GetMapping("/pageable/mutual/{userId}/{authId}")
    public ResponseEntity<List<MUser>> getPageableMutualFriends(@PathVariable("userId") long userId, @PathVariable("authId") long authId, Pageable pageable) {
        List<MUser> userList = sUserUser.selectPageableMutualFriends(userId, authId,pageable);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/notifications/{authId}")
    public ResponseEntity<String[]> getNotifications(@PathVariable("authId") long authId) {
        String[] notifications = sUserUser.selectNotifications(authId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/{friendId}/{userId}")
    public ResponseEntity<MUserUser> getUserUser(@PathVariable("friendId") long friendId, @PathVariable("userId") long userId) {

        try {
            MUserUser mUserUser = sUserUser.selectUserUser(new UserUserId(friendId, userId));

            if (mUserUser == null){
                 mUserUser = sUserUser.selectUserUser(new UserUserId(userId, friendId));
            }
            if (mUserUser.isUserUserActive()) {
                return new ResponseEntity<>(mUserUser, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping("/deleted/{friendId}/{userId}")
    public ResponseEntity<MUserUser> getDeletedUserUser(@PathVariable("friendId") long friendId, @PathVariable("userId") long userId) {

        try {
            MUserUser mUserUser = sUserUser.selectUserUser(new UserUserId(friendId, userId));

            if (mUserUser == null){
                mUserUser = sUserUser.selectUserUser(new UserUserId(userId, friendId));
            }
            if (!mUserUser.isUserUserActive()) {
                return new ResponseEntity<>(mUserUser, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<MUser>> getFriends(@PathVariable("userId") long userId) {
        try {
            List<MUser> mUserUserList = sUserUser.selectFriendsByFriendId(userId);
            mUserUserList.addAll(sUserUser.selectFriendsByUserId(userId));
            return new ResponseEntity<>(mUserUserList, HttpStatus.OK);
        }catch ( NoSuchElementException e){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }
}
