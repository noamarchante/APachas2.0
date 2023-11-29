package daw.project.apachas.controller;

import daw.project.apachas.entity.id.UserEventId;
import daw.project.apachas.model.MEvent;
import daw.project.apachas.model.MUser;
import daw.project.apachas.model.MUserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import daw.project.apachas.service.SUserEvent;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/usersEvents")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class CUserEvent {

    @Autowired
    @Qualifier("SUserEvent")
    SUserEvent sUserEvent;

    @PostMapping
    public ResponseEntity<Void> createUserEvent(@RequestBody @Valid MUserEvent mUserEvent, UriComponentsBuilder builder) {
        boolean flag = sUserEvent.insertUserEvent(mUserEvent);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/usersEvents/{userEventId}").buildAndExpand(new UserEventId(mUserEvent.getEventId(), mUserEvent.getUserId())).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Void> editUserEvent(@PathVariable("eventId") long eventId, @RequestBody @Valid List<Long> userIdList) {
        boolean flag = sUserEvent.updateUserEvent(eventId, userIdList);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/status/{eventId}")
    public ResponseEntity<Void> editStatus (@PathVariable("eventId") long eventId, @RequestBody @Valid long authId) {
        boolean flag = sUserEvent.updateStatus(eventId, authId);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{eventId}/{userId}")
    public ResponseEntity<Void> deleteUserEvent(@PathVariable("eventId") long eventId, @PathVariable("userId") long userId) {
        boolean flag = sUserEvent.deleteUserEvent(eventId, userId);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/count/mutual/{userId}/{authId}")
    public ResponseEntity<Long> countMutualEvents(@PathVariable("userId") long userId, @PathVariable("authId") long authId) {
        long eventCount = sUserEvent.countMutualEvents(userId,authId);
        return new ResponseEntity<>(eventCount, HttpStatus.OK);
    }

    @GetMapping("/count/events/{authId}")
    public ResponseEntity<Long> countEvents( @PathVariable("authId") long authId) {
        long eventCount = sUserEvent.countEvents(authId);
        return new ResponseEntity<>(eventCount, HttpStatus.OK);
    }

    @GetMapping("/count/events/{eventName}/{authId}")
    public ResponseEntity<Long> countSearchEvents(@PathVariable("eventName") String eventName, @PathVariable("authId") long authId) {
        long eventCount = sUserEvent.countSearchEvents(eventName, authId);
        return new ResponseEntity<>(eventCount, HttpStatus.OK);
    }

    @GetMapping("/count/withFinished/{authId}")
    public ResponseEntity<Long> countEventsWithFinished( @PathVariable("authId") long authId) {
        long eventCount = sUserEvent.countEventsWithFinished(authId);
        return new ResponseEntity<>(eventCount, HttpStatus.OK);
    }

    @GetMapping("/count/withFinished/{eventName}/{authId}")
    public ResponseEntity<Long> countSearchEventsWithFinished(@PathVariable("eventName") String eventName, @PathVariable("authId") long authId) {
        long eventCount = sUserEvent.countSearchEventsWithFinished(eventName, authId);
        return new ResponseEntity<>(eventCount, HttpStatus.OK);
    }

    @GetMapping("/count/partakers/{eventId}")
    public ResponseEntity<Long> countPartakers( @PathVariable("eventId") long eventId) {
        long eventCount = sUserEvent.countPartakers(eventId);
        return new ResponseEntity<>(eventCount, HttpStatus.OK);
    }

    @GetMapping("/notifications/{authId}")
    public ResponseEntity<String[]> getNotifications(@PathVariable("authId") long authId) {
        String[] notifications = sUserEvent.selectNotifications(authId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/pageable/mutual/{userId}/{authId}")
    public ResponseEntity<List<MEvent>> getPageableMutualEvents(@PathVariable("userId") long userId, @PathVariable("authId") long authId, Pageable pageable) {
        List<MEvent> eventList = sUserEvent.selectPageableMutualEvents(userId, authId,pageable);
        return new ResponseEntity<>(eventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/events/{eventName}/{authId}")
    public ResponseEntity<List<MEvent>> getPageableSearchEvents(@PathVariable("eventName") String eventName, @PathVariable("authId") long authId, Pageable pageable) {
        List<MEvent> eventList = sUserEvent.selectPageableSearchEvents(eventName, authId,pageable);
        return new ResponseEntity<>(eventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/withFinished/{eventName}/{authId}")
    public ResponseEntity<List<MEvent>> getPageableSearchEventsWithFinished(@PathVariable("eventName") String eventName, @PathVariable("authId") long authId, Pageable pageable) {
        List<MEvent> eventList = sUserEvent.selectPageableSearchEventsWithFinished(eventName, authId,pageable);
        return new ResponseEntity<>(eventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/events/{authId}")
    public ResponseEntity<List<MEvent>> getPageableEvents(@PathVariable("authId") long authId, Pageable pageable) {
        List<MEvent> mEventList = sUserEvent.selectPageableEvents(authId, pageable);
        return new ResponseEntity<>(mEventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/withFinished/{authId}")
    public ResponseEntity<List<MEvent>> getPageableEventsWithFinished(@PathVariable("authId") long authId, Pageable pageable) {
        List<MEvent> mEventList = sUserEvent.selectPageableEventsWithFinished(authId, pageable);
        return new ResponseEntity<>(mEventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/partakers/{eventId}")
    public ResponseEntity<List<MUser>> getPageablePartakers(@PathVariable("eventId") long eventId, Pageable pageable) {
        List<MUser> mUserList = sUserEvent.selectPageablePartakers(eventId, pageable);
        return new ResponseEntity<>(mUserList, HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<List<MUser>> getPartakers(@PathVariable("eventId") long eventId) {
        try {
            List<MUser> mUser = sUserEvent.selectPartakers(eventId);
            return new ResponseEntity<>(mUser, HttpStatus.OK);
        }catch ( NoSuchElementException e){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping("/{eventId}/{authId}")
    public ResponseEntity<MUserEvent> getUserEvent(@PathVariable("eventId") long eventId, @PathVariable("authId") long authId) {
        try {
            MUserEvent mUserEvent = sUserEvent.selectUserEvent(eventId, authId);
            return new ResponseEntity<>(mUserEvent, HttpStatus.OK);
        }catch ( NoSuchElementException e){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping("/pageable/{eventId}")
    public ResponseEntity<List<MUserEvent>> getPageableUserEvents(@PathVariable("eventId") long eventId, Pageable pageable) {
        List<MUserEvent> mUserEventList = sUserEvent.selectPageableUserEvents(eventId, pageable);
        return new ResponseEntity<>(mUserEventList, HttpStatus.OK);
    }

    @GetMapping("/count/{eventId}")
    public ResponseEntity<Long> countUserEvents( @PathVariable("eventId") long eventId) {
        long userEventCount = sUserEvent.countUserEvents(eventId);
        return new ResponseEntity<>(userEventCount, HttpStatus.OK);
    }

    @GetMapping("/sum/{eventId}")
    public ResponseEntity<Double> sumTotalEventExpense( @PathVariable("eventId") long eventId) {
        Double totalEventExpense = sUserEvent.sumTotalEventExpense(eventId);
        return new ResponseEntity<>(totalEventExpense, HttpStatus.OK);
    }

    @GetMapping("/pageable/{userName}/{eventId}")
    public ResponseEntity<List<MUserEvent>> getPageableSearchUserEvents(@PathVariable("userName") String userName, @PathVariable("eventId") long eventId, Pageable pageable) {
        List<MUserEvent> userEventList = sUserEvent.selectPageableSearchUserEvents(userName, eventId,pageable);
        return new ResponseEntity<>(userEventList, HttpStatus.OK);
    }

    @GetMapping("/count/{userName}/{eventId}")
    public ResponseEntity<Long> countSearchUserEvents(@PathVariable("userName") String userName, @PathVariable("eventId") long eventId) {
        long userEventCount = sUserEvent.countSearchUserEvents(userName, eventId);
        return new ResponseEntity<>(userEventCount, HttpStatus.OK);
    }

    @PutMapping("/totalExpense/{eventId}/{userId}")
    public ResponseEntity<Void> editTotalExpense(@PathVariable("eventId") long eventId,@PathVariable("userId") long userId, @RequestBody @Valid double totalExpense) {
        boolean flag = sUserEvent.updateTotalExpense(eventId, userId, totalExpense);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @PutMapping("/debt/{eventId}/{userId}")
    public ResponseEntity<Void> editDebt(@PathVariable("eventId") long eventId,@PathVariable("userId") long userId, @RequestBody @Valid double userDebt) {
        boolean flag = sUserEvent.updateDebt(eventId, userId, userDebt);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @GetMapping("/events/open/{authId}")
    public ResponseEntity<List<MEvent>> getOpenEvents(@PathVariable("authId") long authId) {
        List<MEvent> mEventList = sUserEvent.selectOpenEvents(authId);
        return new ResponseEntity<>(mEventList, HttpStatus.OK);
    }

    @GetMapping("/events/closed/{authId}")
    public ResponseEntity<List<MEvent>> getClosedEvents(@PathVariable("authId") long authId) {
        List<MEvent> mEventList = sUserEvent.selectClosedEvents(authId);
        return new ResponseEntity<>(mEventList, HttpStatus.OK);
    }


}
