package daw.project.apachas.controller;

import daw.project.apachas.configuration.SecurityConfiguration;
import daw.project.apachas.model.MEmailBody;
import daw.project.apachas.model.MRetrievePassword;
import daw.project.apachas.model.MUser;
import daw.project.apachas.model.MVerifyEmail;
import daw.project.apachas.service.SEmail;
import daw.project.apachas.service.SUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.DELETE, RequestMethod.PUT})
public class CUser {
    @Autowired
    @Qualifier("SUser")
    SUser sUser;

    @Autowired
    @Qualifier("SEmail")
    SEmail sEmail;

    @Autowired
    SecurityConfiguration securityConfiguration;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid MUser mUser, UriComponentsBuilder builder) {

        boolean unverified = sUser.unverifiedUser(mUser);
        if (unverified) {
            boolean send = sendVerifyEmail(mUser);
            if (send){
                return new ResponseEntity<>(HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }else {
            mUser.setUserPassword(securityConfiguration.passwordEncoder().encode(mUser.getUserPassword()));
            mUser.setRoles("USER");
            boolean flag = sUser.insertUser(mUser);
            if (!flag) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                boolean send = sendVerifyEmail(mUser);
                if (send){
                    HttpHeaders headers = new HttpHeaders();
                    headers.setLocation(builder.path("/{userId}").buildAndExpand(mUser.getUserId()).toUri());
                    return new ResponseEntity<>(headers, HttpStatus.CREATED);
                }else{
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }
        }
    }

    private boolean sendVerifyEmail(MUser mUser){
        File file = null;
        String line;
        FileReader fr = null;
        BufferedReader br = null;
        String content = "";
        try {
            file = new File ("src/main/resources/templates/verifyEmail.html");
            fr = new FileReader (file);
            br = new BufferedReader(fr);
            while((line=br.readLine())!=null){
                content += line;
            }
            UUID uuid = UUID.randomUUID();
            String tokenPassword = uuid.toString();
            boolean token = sUser.updateTokenPassword(mUser,tokenPassword);

            if (token){
                content = content
                        .replace(" * ",  "cid:image001")
                        .replace(" ** ",  " " + mUser.getUserEmail() + " ")
                        .replace(" *** ", "http://localhost:4200/verifyEmail?email=" + mUser.getUserEmail() + "&token=" + tokenPassword);
                boolean send = sEmail.sendEmailWithImage(new MEmailBody(content, mUser.getUserEmail(),"APACHAS: Activación cuenta"));
                if (send){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally{
            try{
                if( null != fr ){
                    fr.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }

    @PutMapping("/sendRetrievePasswordEmail")
    public ResponseEntity<Void> sendRetrievePasswordEmail(@RequestBody @Valid MUser mUser, UriComponentsBuilder builder){
        boolean flag = sUser.verifiedUser(mUser);
        if (flag){
            File file = null;
            String line;
            FileReader fr = null;
            BufferedReader br = null;
            String content = "";
            try {
                file = new File ("src/main/resources/templates/retrievePassword.html");
                fr = new FileReader (file);
                br = new BufferedReader(fr);
                while((line=br.readLine())!=null){
                    content += line;
                }
                UUID uuid = UUID.randomUUID();
                String tokenPassword = uuid.toString();
                boolean token = sUser.updateTokenPassword(mUser,tokenPassword);
                if (token){
                    content = content
                            .replace(" * ",  "cid:image001")
                            .replace(" ** ", "http://localhost:4200/retrievePassword?email=" + mUser.getUserEmail() + "&token=" + tokenPassword);
                    boolean send = sEmail.sendEmailWithImage(new MEmailBody(content, mUser.getUserEmail(),"APACHAS: Recuperar contraseña"));
                    if (!send) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    } else {
                        HttpHeaders headers = new HttpHeaders();
                        headers.setLocation(builder.path("/{userId}").buildAndExpand(mUser.getUserId()).toUri());
                        return new ResponseEntity<>(headers, HttpStatus.CREATED);
                    }
                }else{
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

            }catch(Exception e){
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }finally{
                try{
                    if( null != fr ){
                        fr.close();
                    }
                }catch (Exception e2){
                    e2.printStackTrace();
                }
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<Void> editUser(@RequestBody @Valid MUser mUser) {
        mUser.setUserPassword(securityConfiguration.passwordEncoder().encode(mUser.getUserPassword()));
        boolean flag = sUser.updateUser(mUser);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/verify")
    public ResponseEntity<Void> verifyUser(@RequestBody @Valid MVerifyEmail mVerifyEmail) {
        boolean flag = sUser.verifyUser(mVerifyEmail);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/retrievePassword")
    public ResponseEntity<Void> retrievePassword(@RequestBody @Valid MRetrievePassword mRetrievePassword) {
        mRetrievePassword.setNewPassword(securityConfiguration.passwordEncoder().encode(mRetrievePassword.getNewPassword()));

        boolean flag = sUser.retrievePassword(mRetrievePassword);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/token")
    public ResponseEntity<Boolean> isTokenPassword(@RequestBody @Valid String userEmail) {
        boolean isTokenPassword = sUser.isTokenPassword(userEmail);
        if (isTokenPassword){
            return new ResponseEntity<>(isTokenPassword,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(isTokenPassword,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/count/{authId}")
    public ResponseEntity<Long> countUsers( @PathVariable("authId") long authId) {
        long userCount = sUser.countUsers(authId);
        return new ResponseEntity<>(userCount, HttpStatus.OK);
    }



    @GetMapping("/count/{userLogin}/{authId}")
    public ResponseEntity<Long> countSearchUsers(@PathVariable("userLogin") String userLogin, @PathVariable("authId") long authId) {
        long userCount = sUser.countSearchUsers(userLogin, authId);
        return new ResponseEntity<>(userCount, HttpStatus.OK);
    }

    @GetMapping("/pageable/{authId}")
    public ResponseEntity<List<MUser>> getPageableUsers(@PathVariable("authId") long authId, Pageable pageable) {

        List<MUser> userList = sUser.selectPageableUsers(authId, pageable);

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/pageable/{userLogin}/{authId}")
    public ResponseEntity<List<MUser>> getPageableSearchUsers(@PathVariable("userLogin") String userLogin, @PathVariable("authId") long authId, Pageable pageable) {
        List<MUser> userList = sUser.selectPageableSearchUsers(userLogin,authId,pageable);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/{userLogin}")
    public ResponseEntity<MUser> getUser(@PathVariable("userLogin") String userLogin) {
        MUser mUser = sUser.selectUser(userLogin);
        return new ResponseEntity<>(mUser, HttpStatus.OK);
    }

    @GetMapping("/authUser/{userId}")
    public ResponseEntity<MUser> getUserById(@PathVariable("userId") long userId) {
        MUser mUser = sUser.selectUserById(userId);
        return new ResponseEntity<>(mUser, HttpStatus.OK);
    }

    @GetMapping("/available/{login}")
    public ResponseEntity<Boolean> loginAvailable(@PathVariable("login") String login) {
        boolean available = sUser.loginAvailable(login);
        return new ResponseEntity<>(available, HttpStatus.OK);
    }
}
