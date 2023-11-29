package daw.project.apachas.controller;

import daw.project.apachas.model.MUserUserEvent;
import daw.project.apachas.service.SUserUserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usersUsersEvents")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS, RequestMethod.DELETE, RequestMethod.OPTIONS})

public class CUserUserEvent {

    @Autowired
    @Qualifier("SUserUserEvent")
    SUserUserEvent sUserUserEvent;

    @GetMapping("/pageable/byEvent/{eventId}")
    public ResponseEntity<List<MUserUserEvent>> getPageableUserUserEventsByEvent(@PathVariable("eventId") long eventId, Pageable pageable) {
        List<MUserUserEvent> mUserUserEventList = sUserUserEvent.selectPageableUserUserEventsByEvent(eventId, pageable);
        return new ResponseEntity<>(mUserUserEventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/byAuthUser/{authUserId}")
    public ResponseEntity<List<MUserUserEvent>> getPageableUserUserEventsByAuthUser(@PathVariable("authUserId") long authUserId, Pageable pageable) {
        List<MUserUserEvent> mUserUserEventList = sUserUserEvent.selectPageableUserUserEventsByAuthUser(authUserId, pageable);
        return new ResponseEntity<>(mUserUserEventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/byAuthUser/{authUserId}/byEvent")
    public ResponseEntity<List<MUserUserEvent>> getPageableUserUserEventsByAuthUserByEvent(@PathVariable("authUserId") long authUserId, Pageable pageable) {
        List<MUserUserEvent> mUserUserEventList = sUserUserEvent.selectPageableUserUserEventsByAuthUserByEvent(authUserId, pageable);
        return new ResponseEntity<>(mUserUserEventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/debts/byAuthUser/{authUserId}")
    public ResponseEntity<List<MUserUserEvent>> getPageableUserUserEventsDebtsByAuthUser(@PathVariable("authUserId") long authUserId, Pageable pageable) {
        List<MUserUserEvent> mUserUserEventList = sUserUserEvent.selectPageableUserUserEventsDebtsByAuthUser(authUserId, pageable);
        return new ResponseEntity<>(mUserUserEventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/debts/byAuthUser/{authUserId}/byEvent")
    public ResponseEntity<List<MUserUserEvent>> getPageableUserUserEventsDebtsByAuthUserByEvent(@PathVariable("authUserId") long authUserId, Pageable pageable) {
        List<MUserUserEvent> mUserUserEventList = sUserUserEvent.selectPageableUserUserEventsDebtsByAuthUserByEvent(authUserId, pageable);
        return new ResponseEntity<>(mUserUserEventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/payments/byAuthUser/{authUserId}")
    public ResponseEntity<List<MUserUserEvent>> getPageableUserUserEventsPaymentsByAuthUser(@PathVariable("authUserId") long authUserId, Pageable pageable) {
        List<MUserUserEvent> mUserUserEventList = sUserUserEvent.selectPageableUserUserEventsPaymentsByAuthUser(authUserId, pageable);
        return new ResponseEntity<>(mUserUserEventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/payments/byAuthUser/{authUserId}/byEvent")
    public ResponseEntity<List<MUserUserEvent>> getPageableUserUserEventsPaymentsByAuthUserByEvent(@PathVariable("authUserId") long authUserId, Pageable pageable) {
        List<MUserUserEvent> mUserUserEventList = sUserUserEvent.selectPageableUserUserEventsPaymentsByAuthUserByEvent(authUserId, pageable);
        return new ResponseEntity<>(mUserUserEventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/notFinished/byAuthUser/{authUserId}")
    public ResponseEntity<List<MUserUserEvent>> getPageableNotFinishedUserUserEventsByAuthUser(@PathVariable("authUserId") long authUserId, Pageable pageable) {
        List<MUserUserEvent> mUserUserEventList = sUserUserEvent.selectPageableNotFinishedUserUserEventsByAuthUser(authUserId, pageable);
        return new ResponseEntity<>(mUserUserEventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/notFinished/byAuthUser/{authUserId}/byEvent")
    public ResponseEntity<List<MUserUserEvent>> getPageableNotFinishedUserUserEventsByAuthUserByEvent(@PathVariable("authUserId") long authUserId, Pageable pageable) {
        List<MUserUserEvent> mUserUserEventList = sUserUserEvent.selectPageableNotFinishedUserUserEventsByAuthUserByEvent(authUserId, pageable);
        return new ResponseEntity<>(mUserUserEventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/notFinished/debts/byAuthUser/{authUserId}")
    public ResponseEntity<List<MUserUserEvent>> getPageableNotFinishedUserUserEventsDebtsByAuthUser(@PathVariable("authUserId") long authUserId, Pageable pageable) {
        List<MUserUserEvent> mUserUserEventList = sUserUserEvent.selectPageableNotFinishedUserUserEventsDebtsByAuthUser(authUserId, pageable);
        return new ResponseEntity<>(mUserUserEventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/notFinished/debts/byAuthUser/{authUserId}/byEvent")
    public ResponseEntity<List<MUserUserEvent>> getPageableNotFinishedUserUserEventsDebtsByAuthUserByEvent(@PathVariable("authUserId") long authUserId, Pageable pageable) {
        List<MUserUserEvent> mUserUserEventList = sUserUserEvent.selectPageableNotFinishedUserUserEventsDebtsByAuthUserByEvent(authUserId, pageable);
        return new ResponseEntity<>(mUserUserEventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/notFinished/payments/byAuthUser/{authUserId}")
    public ResponseEntity<List<MUserUserEvent>> getPageableNotFinishedUserUserEventsPaymentsByAuthUser(@PathVariable("authUserId") long authUserId, Pageable pageable) {
        List<MUserUserEvent> mUserUserEventList = sUserUserEvent.selectPageableNotFinishedUserUserEventsPaymentsByAuthUser(authUserId, pageable);
        return new ResponseEntity<>(mUserUserEventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/notFinished/payments/byAuthUser/{authUserId}/byEvent")
    public ResponseEntity<List<MUserUserEvent>> getPageableNotFinishedUserUserEventsPaymentsByAuthUserByEvent(@PathVariable("authUserId") long authUserId, Pageable pageable) {
        List<MUserUserEvent> mUserUserEventList = sUserUserEvent.selectPageableNotFinishedUserUserEventsPaymentsByAuthUserByEvent(authUserId, pageable);
        return new ResponseEntity<>(mUserUserEventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/byAuthUser/{authUserId}/byDate")
    public ResponseEntity<List<MUserUserEvent>> getPageableUserUserEventsByAuthUserByDate(@PathVariable("authUserId") long authUserId, Pageable pageable) {
        List<MUserUserEvent> mUserUserEventList = sUserUserEvent.selectPageableUserUserEventsByAuthUserByDate(authUserId, pageable);
        return new ResponseEntity<>(mUserUserEventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/debts/byAuthUser/{authUserId}/byDate")
    public ResponseEntity<List<MUserUserEvent>> getPageableUserUserEventsDebtsByAuthUserByDate(@PathVariable("authUserId") long authUserId, Pageable pageable) {
        List<MUserUserEvent> mUserUserEventList = sUserUserEvent.selectPageableUserUserEventsDebtsByAuthUserByDate(authUserId, pageable);
        return new ResponseEntity<>(mUserUserEventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/payments/byAuthUser/{authUserId}/byDate")
    public ResponseEntity<List<MUserUserEvent>> getPageableUserUserEventsPaymentsByAuthUserByDate(@PathVariable("authUserId") long authUserId, Pageable pageable) {
        List<MUserUserEvent> mUserUserEventList = sUserUserEvent.selectPageableUserUserEventsPaymentsByAuthUserByDate(authUserId, pageable);
        return new ResponseEntity<>(mUserUserEventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/notFinished/byAuthUser/{authUserId}/byDate")
    public ResponseEntity<List<MUserUserEvent>> getPageableNotFinishedUserUserEventsByAuthUserByDate(@PathVariable("authUserId") long authUserId, Pageable pageable) {
        List<MUserUserEvent> mUserUserEventList = sUserUserEvent.selectPageableNotFinishedUserUserEventsByAuthUserByDate(authUserId, pageable);
        return new ResponseEntity<>(mUserUserEventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/notFinished/debts/byAuthUser/{authUserId}/byDate")
    public ResponseEntity<List<MUserUserEvent>> getPageableNotFinishedUserUserEventsDebtsByAuthUserByDate(@PathVariable("authUserId") long authUserId, Pageable pageable) {
        List<MUserUserEvent> mUserUserEventList = sUserUserEvent.selectPageableNotFinishedUserUserEventsDebtsByAuthUserByDate(authUserId, pageable);
        return new ResponseEntity<>(mUserUserEventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/notFinished/payments/byAuthUser/{authUserId}/byDate")
    public ResponseEntity<List<MUserUserEvent>> getPageableNotFinishedUserUserEventsPaymentsByAuthUserByDate(@PathVariable("authUserId") long authUserId, Pageable pageable) {
        List<MUserUserEvent> mUserUserEventList = sUserUserEvent.selectPageableNotFinishedUserUserEventsPaymentsByAuthUserByDate(authUserId, pageable);
        return new ResponseEntity<>(mUserUserEventList, HttpStatus.OK);
    }

    @GetMapping("/pageable/byEvent/{transactionActorName}/{eventId}")
    public ResponseEntity<List<MUserUserEvent>> getPageableSearchUserUserEventsByEvent(@PathVariable("transactionActorName") String transactionActorName, @PathVariable("eventId") long eventId, Pageable pageable) {
        List<MUserUserEvent> userUserEventList = sUserUserEvent.selectPageableSearchUserUserEventsByEvent(transactionActorName, eventId,pageable);
        return new ResponseEntity<>(userUserEventList, HttpStatus.OK);
    }


    @GetMapping("/pageable/byAuthUser/{transactionSearchValue}/{authUserId}")
    public ResponseEntity<List<MUserUserEvent>> getPageableSearchUserUserEventsByAuthUser(@PathVariable("transactionSearchValue") String transactionSearchValue, @PathVariable("authUserId") long authUserId, Pageable pageable) {
        List<MUserUserEvent> userUserEventList = sUserUserEvent.selectPageableSearchUserUserEventsByAuthUser(transactionSearchValue, authUserId,pageable);
        return new ResponseEntity<>(userUserEventList, HttpStatus.OK);
    }

    @GetMapping("/count/byEvent/{transactionActorName}/{eventId}")
    public ResponseEntity<Long> countSearchUserUserEventsByEvent(@PathVariable("transactionActorName") String transactionActorName, @PathVariable("eventId") long eventId) {
        long userUserEventCount = sUserUserEvent.countSearchUserUserEventsByEvent(transactionActorName, eventId);
        return new ResponseEntity<>(userUserEventCount, HttpStatus.OK);
    }

    @GetMapping("/count/byAuthUser/{transactionSearchValue}/{authUserId}")
    public ResponseEntity<Long> countSearchUserUserEventsByAuthUser(@PathVariable("transactionSearchValue") String transactionSearchValue, @PathVariable("authUserId") long authUserId) {
        long userUserEventCount = sUserUserEvent.countSearchUserUserEventsByAuthUser(transactionSearchValue, authUserId);
        return new ResponseEntity<>(userUserEventCount, HttpStatus.OK);
    }

    @GetMapping("/count/byEvent/{eventId}")
    public ResponseEntity<Long> countUserUserEventsByEvent( @PathVariable("eventId") long eventId) {
        long userUserEventCount = sUserUserEvent.countUserUserEventsByEvent(eventId);
        return new ResponseEntity<>(userUserEventCount, HttpStatus.OK);
    }

    @GetMapping("/count/byAuthUser/{authUserId}")
    public ResponseEntity<Long> countUserUserEventsByAuthUser( @PathVariable("authUserId") long authUserId) {
        long userUserEventCount = sUserUserEvent.countUserUserEventsByAuthUser(authUserId);
        return new ResponseEntity<>(userUserEventCount, HttpStatus.OK);
    }

    @GetMapping("/count/debts/byAuthUser/{authUserId}")
    public ResponseEntity<Long> countUserUserEventsDebtsByAuthUser( @PathVariable("authUserId") long authUserId) {
        long userUserEventCount = sUserUserEvent.countUserUserEventsDebtsByAuthUser(authUserId);
        return new ResponseEntity<>(userUserEventCount, HttpStatus.OK);
    }

    @GetMapping("/count/payments/byAuthUser/{authUserId}")
    public ResponseEntity<Long> countUserUserEventsPaymentsByAuthUser( @PathVariable("authUserId") long authUserId) {
        long userUserEventCount = sUserUserEvent.countUserUserEventsPaymentsByAuthUser(authUserId);
        return new ResponseEntity<>(userUserEventCount, HttpStatus.OK);
    }

    @GetMapping("/count/notFinished/byAuthUser/{authUserId}")
    public ResponseEntity<Long> countNotFinishedUserUserEventsByAuthUser( @PathVariable("authUserId") long authUserId) {
        long userUserEventCount = sUserUserEvent.countNotFinishedUserUserEventsByAuthUser(authUserId);
        return new ResponseEntity<>(userUserEventCount, HttpStatus.OK);
    }

    @GetMapping("/count/notFinished/debts/byAuthUser/{authUserId}")
    public ResponseEntity<Long> countNotFinishedUserUserEventsDebtsByAuthUser( @PathVariable("authUserId") long authUserId) {
        long userUserEventCount = sUserUserEvent.countNotFinishedUserUserEventsDebtsByAuthUser(authUserId);
        return new ResponseEntity<>(userUserEventCount, HttpStatus.OK);
    }

    @GetMapping("/count/notFinished/payments/byAuthUser/{authUserId}")
    public ResponseEntity<Long> countNotFinishedUserUserEventsPaymentsByAuthUser( @PathVariable("authUserId") long authUserId) {
        long userUserEventCount = sUserUserEvent.countNotFinishedUserUserEventsPaymentsByAuthUser(authUserId);
        return new ResponseEntity<>(userUserEventCount, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUserUserEvent(@RequestBody @Valid long eventId) {
        boolean flag = sUserUserEvent.insertUserUserEvent(eventId);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @PutMapping("/paid/{eventId}/{senderId}/{receiverId}")
    public ResponseEntity<Void> editPaid(@PathVariable("eventId") long eventId, @PathVariable("senderId") long senderId, @PathVariable("receiverId") long receiverId, @RequestBody @Valid boolean paid) {
        boolean flag = sUserUserEvent.updatePaid(eventId, senderId, receiverId, paid);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @PutMapping("/confirmed/{eventId}/{senderId}/{receiverId}")
    public ResponseEntity<Void> editConfirmed(@PathVariable("eventId") long eventId, @PathVariable("senderId") long senderId, @PathVariable("receiverId") long receiverId, @RequestBody @Valid boolean confirmed) {
        boolean flag = sUserUserEvent.updateConfirmed(eventId, senderId, receiverId, confirmed);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

}
