package daw.project.apachas.controller;

import daw.project.apachas.model.MEmailBody;
import daw.project.apachas.service.SEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.OPTIONS})
public class CEmail {

    @Autowired
    @Qualifier("SEmail")
    SEmail sEmail;

    @PostMapping
    public ResponseEntity<Void> sendEmail(@RequestBody @Valid MEmailBody mEmailBody) {
        boolean flag = sEmail.sendEmail(mEmailBody);
        if (flag) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
    }
}
