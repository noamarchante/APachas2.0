package daw.project.apachas.configuration;

import daw.project.apachas.entity.User;
import daw.project.apachas.repository.RUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SUserPrincipalDetails implements UserDetailsService {

    @Autowired
    private RUser rUser;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.rUser.findByUserLoginAndUserActiveTrueAndUserVerifiedTrue(s);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        return userPrincipal;
    }
}
