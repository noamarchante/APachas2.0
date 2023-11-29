package daw.project.apachas.service;

import daw.project.apachas.converter.ConUser;
import daw.project.apachas.entity.User;
import daw.project.apachas.model.MRetrievePassword;
import daw.project.apachas.model.MUser;
import daw.project.apachas.model.MVerifyEmail;
import daw.project.apachas.repository.RGroup;
import daw.project.apachas.repository.RGroupUser;
import daw.project.apachas.repository.RUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("SUser")
public class SUser implements UserDetailsService {

    @Autowired
    @Qualifier("RUser")
    private RUser rUser;

    @Autowired
    @Qualifier("ConUser")
    private ConUser conUser;

    @Autowired
    @Qualifier("RGroup")
    private RGroup rGroup;

    @Autowired
    @Qualifier("RGroupUser")
    private RGroupUser rGroupUser;


    //JWT: ESTE MÉTODO BUSCAR UN USUARIO CON EL NOMBRE PASADO POR PARÁMETRO Y DEVUELVE UN OBJETO DE TIPO USERDETAILS CON LOGIN + CONTRASEÑA + LISTA DE PERMISOS VACÍA
    @Override
    public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {
        User user = rUser.findByUserLoginAndUserActiveTrueAndUserVerifiedTrue(userLogin);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with userLogin: " + userLogin);
        }else {
            return new org.springframework.security.core.userdetails.User(user.getUserLogin(), user.getUserPassword(), getAuthorities(user));
        }
    }

    protected Collection<? extends GrantedAuthority> getAuthorities (User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        rGroupUser.findByGroupUserId_UserId(user.getUserId()).forEach(p -> {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+ rGroup.findByGroupId(p.getGroupUserId().getGroupId()).getGroupName());
            authorities.add(authority);
        });
        return authorities;
    }

    public synchronized boolean insertUser(MUser mUser) {
        User user = conUser.conMUser(mUser);
        User existingUser = rUser.findByUserId(user.getUserId());
        if (existingUser != null)
        {
            return false;
        } else {
            user.setUserCreation(new Timestamp(System.currentTimeMillis()));
            user.setUserRemoval(null);
            user.setUserActive(true);
            user.setUserVerified(false);
            rUser.save(user);
            return true;
        }
    }

    public synchronized boolean updateUser(MUser mUser) {
        User user = conUser.conMUser(mUser);
        User existingUser = rUser.findByUserId(user.getUserId());
        if (existingUser != null) {
            existingUser.setUserPassword(user.getUserPassword());
            existingUser.setUserBirthday(user.getUserBirthday());
            existingUser.setUserLogin(user.getUserLogin());
            existingUser.setUserName(user.getUserName());
            existingUser.setUserSurname(user.getUserSurname());
            existingUser.setUserPhoto(user.getUserPhoto());
            rUser.save(existingUser);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean updateTokenPassword(MUser mUser, String tokenPassword) {

        User user = conUser.conMUser(mUser);
        User existingUser = rUser.findByUserEmail(user.getUserEmail());

        if (existingUser != null) {
            existingUser.setTokenPassword(tokenPassword);
            rUser.save(existingUser);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean verifyUser(MVerifyEmail mVerifyEmail) {
        User existingUser = rUser.findByUserEmailAndUserVerifiedFalse(mVerifyEmail.getUserEmail());
        if (existingUser != null) {
            if (existingUser.getTokenPassword().equals(mVerifyEmail.getTokenPassword())){
                existingUser.setUserVerified(true);
                existingUser.setTokenPassword(null);
                rUser.save(existingUser);
                return true;
            }else{
                return false;
            }
        } else {
            return false;
        }
    }

    public synchronized boolean retrievePassword(MRetrievePassword mRetrievePassword) {
        User existingUser = rUser.findByUserEmailAndUserActiveTrueAndUserVerifiedTrue(mRetrievePassword.getUserEmail());
        if (existingUser != null) {
            if (existingUser.getTokenPassword().equals(mRetrievePassword.getTokenPassword())){
                existingUser.setUserPassword(mRetrievePassword.getNewPassword());
                existingUser.setTokenPassword(null);
                rUser.save(existingUser);
                return true;
            }else{
                return false;
            }
        } else {
            return false;
        }
    }

    public synchronized boolean unverifiedUser(MUser mUser) {
        User existingUser = rUser.findByUserEmail(mUser.getUserEmail());
        if (existingUser != null && !existingUser.isUserVerified()) {
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean verifiedUser(MUser mUser) {
        User existingUser = rUser.findByUserEmail(mUser.getUserEmail());
        if (existingUser != null && existingUser.isUserVerified()) {
            return true;
        } else {
            return false;
        }
    }

    public synchronized Long countUsers(long authId){
        return rUser.countByRolesAndUserIdIsNotAndUserActiveTrueAndUserVerifiedTrue("USER",authId);
    }

    public synchronized Long countSearchUsers(String userLogin, long authId){
        return rUser.countByRolesAndUserLoginContainingAndUserIdIsNotAndUserActiveTrueAndUserVerifiedTrue("USER",userLogin,authId);
    }

    public synchronized List<MUser> selectPageableUsers(long authId, Pageable pageable) {
        return conUser.conUserList(rUser.findUsersByRolesEqualsAndUserIdIsNotAndUserActiveTrueAndUserVerifiedTrueOrderByUserLoginAsc("USER", authId,pageable).getContent());
    }

    public synchronized List<MUser> selectPageableSearchUsers(String userLogin, long authId, Pageable pageable) {
        return conUser.conUserList(rUser.findUsersByUserLoginContainingAndUserIdIsNotAndRolesEqualsAndUserActiveTrueAndUserVerifiedTrueOrderByUserLoginAsc(userLogin, authId, "USER", pageable).getContent());
    }

    public synchronized MUser selectUser(String userLogin) {
        User user = rUser.findByUserLoginAndUserActiveTrueAndUserVerifiedTrue(userLogin);
        return conUser.conUser(user);
    }

    public synchronized MUser selectUserById(long userId) {
        User user = rUser.findByUserIdAndUserActiveTrueAndUserVerifiedTrue(userId);
        return conUser.conUser(user);
    }

    public synchronized boolean isTokenPassword(String userEmail) {
        User existingUser = rUser.findByUserEmailAndTokenPasswordNull(userEmail);
       if ( existingUser!= null) {
           return false;
       }else{
           return true;
       }
    }

    public synchronized boolean loginAvailable(String login) {
        long available = rUser.findLoginAvailable(login);
        if (available == 0){
            return true;
        }else{
            return false;
        }
    }
}
