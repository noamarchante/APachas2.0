package daw.project.apachas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import daw.project.apachas.model.MGroup;
import daw.project.apachas.service.SGroup;
import javax.validation.Valid;

@RestController
@RequestMapping("/groups")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.OPTIONS})
public class CGroup {
    @Autowired
    @Qualifier("SGroup")
    SGroup sGroup;

    @PostMapping
    public ResponseEntity<Long> createGroup(@RequestBody @Valid MGroup mGroup, UriComponentsBuilder builder) {
        Long groupId = sGroup.insertGroup(mGroup);
        if (groupId ==0) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/groups/{groupId}").buildAndExpand(mGroup.getGroupId()).toUri());
            return new ResponseEntity<>(groupId, HttpStatus.CREATED);
        }
    }

    @PutMapping
    public ResponseEntity<Void> editGroup(@RequestBody @Valid MGroup mGroup) {
        boolean flag = sGroup.updateGroup(mGroup);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

   @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("groupId") long groupId) {
        boolean flag = sGroup.deleteGroup(groupId);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }
}
