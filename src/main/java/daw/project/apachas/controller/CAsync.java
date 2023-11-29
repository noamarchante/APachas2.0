package daw.project.apachas.controller;

import daw.project.apachas.service.SAsync;
import daw.project.apachas.service.SEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CAsync {
    @Autowired
    SAsync sAsync;

    @Autowired
    @Qualifier("SEmail")
    SEmail sEmail;

    @GetMapping("/eventNotification")
    public ResponseEntity<Void> eventNotification(){
        sAsync.eventNotification();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/eventReminder")
    public ResponseEntity<Void> eventReminder(){
        sAsync.eventReminder();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/eventStart")
    public ResponseEntity<Void> eventStart(){
        sAsync.eventStart();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/eventFinished")
    public ResponseEntity<Void> eventFinished(){
        sAsync.eventFinished();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/groupNotification")
    public ResponseEntity<Void> groupNotification(){
        sAsync.groupNotification();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/userFriendRequest")
    public ResponseEntity<Void> userFriendRequest(){
        sAsync.userFriendRequest();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/transactionPayReminder")
    public ResponseEntity<Void> transactionPayReminder(){
        sAsync.transactionPayReminder();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/transactionConfirmReminder")
    public ResponseEntity<Void> transactionConfirmReminder(){
        sAsync.transactionConfirmReminder();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
