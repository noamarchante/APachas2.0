package daw.project.apachas.controller;

import daw.project.apachas.entity.id.UserEventId;
import daw.project.apachas.model.MUser;
import daw.project.apachas.model.MUserProduct;
import daw.project.apachas.service.SUserProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/usersProducts")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.OPTIONS})
public class CUserProduct {

    @Autowired
    @Qualifier("SUserProduct")
    SUserProduct sUserProduct;

    @PostMapping
    public ResponseEntity<Void> createUserProduct(@RequestBody @Valid MUserProduct mUserProduct, UriComponentsBuilder builder) {
        boolean flag = sUserProduct.insertUserProduct(mUserProduct);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/usersProducts/{userProductId}").buildAndExpand(new UserEventId(mUserProduct.getProductId(), mUserProduct.getUserId())).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    @PutMapping
    public ResponseEntity<Void> editUserProduct(@RequestBody @Valid MUserProduct mUserProduct) {
        boolean flag = sUserProduct.updateUserProduct(mUserProduct);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{productId}/{userId}")
    public ResponseEntity<Void> deleteUserProduct(@PathVariable("productId") long productId, @PathVariable("userId") long userId) {
        boolean flag = sUserProduct.deleteUserProduct(productId, userId);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/count/partakers/{productId}")
    public ResponseEntity<Long> countPartakers( @PathVariable("productId") long productId) {
        long productCount = sUserProduct.countPartakers(productId);
        return new ResponseEntity<>(productCount, HttpStatus.OK);
    }

    @GetMapping("/pageable/partakers/{productId}")
    public ResponseEntity<List<MUser>> getPageablePartakers(@PathVariable("productId") long productId, Pageable pageable) {
        List<MUser> mUserList = sUserProduct.selectPageablePartakers(productId, pageable);
        return new ResponseEntity<>(mUserList, HttpStatus.OK);
    }

    @GetMapping("/partakers/{productId}")
    public ResponseEntity<List<MUser>> getPartakers(@PathVariable("productId") long productId) {
        try {
            List<MUser> mUser = sUserProduct.selectPartakers(productId);
            return new ResponseEntity<>(mUser, HttpStatus.OK);
        }catch ( NoSuchElementException e){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping("/{productId}/{authId}")
    public ResponseEntity<MUserProduct> getUserProduct(@PathVariable("productId") long productId, @PathVariable("authId") long authId) {
        try {
            MUserProduct mUserProduct = sUserProduct.selectUserProduct(productId, authId);
            return new ResponseEntity<>(mUserProduct, HttpStatus.OK);
        }catch (NullPointerException e){
            return new ResponseEntity<>(new MUserProduct(), HttpStatus.OK);
        }
    }
}
