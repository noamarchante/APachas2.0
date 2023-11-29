package daw.project.apachas.configuration;

import daw.project.apachas.repository.RUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private SUserPrincipalDetails SUserPrincipalDetails;
    @Autowired
    private RUser rUser;

    public SecurityConfiguration(SUserPrincipalDetails SUserPrincipalDetails) {
        this.SUserPrincipalDetails = SUserPrincipalDetails;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CorsConfiguration corsConf = new CorsConfiguration();
        corsConf.addExposedHeader("Authorization");
        corsConf.addAllowedOrigin("*");
        corsConf.addAllowedMethod("GET");
        corsConf.addAllowedMethod("POST");
        corsConf.addAllowedMethod("PUT");
        corsConf.addAllowedMethod("DELETE");
        corsConf.addAllowedMethod("OPTIONS");
        corsConf.applyPermitDefaultValues();
        http
                .cors().configurationSource(request ->corsConf)
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.rUser))
                .authorizeRequests()



                /**********************************************LOGIN***************************************************/
                //POST
                .antMatchers(HttpMethod.POST, "/login").permitAll()



                /**********************************************USER***************************************************/
                //GET
                .antMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMIN", "USER")
                //POST
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                //PUT
                .antMatchers(HttpMethod.PUT, "/users").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/users/retrievePassword").permitAll()
                .antMatchers(HttpMethod.PUT, "/users/sendRetrievePasswordEmail").permitAll()
                .antMatchers(HttpMethod.PUT, "/users/verify").permitAll()
                .antMatchers(HttpMethod.PUT, "/users/token").permitAll()
                //DELETE
                .antMatchers(HttpMethod.DELETE, "/users").hasAnyRole("ADMIN", "USER")



                /**********************************************USERUSER************************************************/
                //GET
                .antMatchers(HttpMethod.GET, "/usersUsers").hasAnyRole("ADMIN", "USER")
                //POST
                .antMatchers(HttpMethod.POST, "/usersUsers").hasAnyRole("ADMIN", "USER")
                //PUT
                .antMatchers(HttpMethod.PUT, "/usersUsers").hasAnyRole("ADMIN", "USER")
                //DELETE
                .antMatchers(HttpMethod.DELETE, "/usersUsers").hasAnyRole("ADMIN", "USER")



                /**********************************************GROUP***************************************************/
                //GET
                .antMatchers(HttpMethod.GET, "/groups").hasAnyRole("ADMIN", "USER")
                //POST
                .antMatchers(HttpMethod.POST, "/groups").hasAnyRole("ADMIN", "USER")
                //PUT
                .antMatchers(HttpMethod.PUT, "/groups").hasAnyRole("ADMIN", "USER")
                //DELETE
                .antMatchers(HttpMethod.DELETE, "/groups").hasAnyRole("ADMIN", "USER")



                /**********************************************USEREVENT***********************************************/
                //GET
                .antMatchers(HttpMethod.GET, "/usersEvents").hasAnyRole("ADMIN", "USER")
                //POST
                .antMatchers(HttpMethod.POST, "/usersEvents").hasAnyRole("ADMIN", "USER")
                //PUT
                .antMatchers(HttpMethod.PUT, "/usersEvents").hasAnyRole("ADMIN", "USER")
                //DELETE
                .antMatchers(HttpMethod.DELETE, "/usersEvents").hasAnyRole("ADMIN", "USER")



                /*********************************************GROUPUSER************************************************/
                //GET
                .antMatchers(HttpMethod.GET, "/groupsUsers").hasAnyRole("ADMIN", "USER")
                //POST
                .antMatchers(HttpMethod.POST, "/groupsUsers").hasAnyRole("ADMIN", "USER")
                //PUT
                .antMatchers(HttpMethod.PUT, "/groupsUsers").hasAnyRole("ADMIN", "USER")
                //DELETE
                .antMatchers(HttpMethod.DELETE, "/groupsUsers").hasAnyRole("ADMIN", "USER")



                /**********************************************EVENT***************************************************/
                //GET
                .antMatchers(HttpMethod.GET, "/events").hasAnyRole("ADMIN", "USER")
                //POST
                .antMatchers(HttpMethod.POST, "/events").hasAnyRole("ADMIN", "USER")
                //PUT
                .antMatchers(HttpMethod.PUT, "/events").hasAnyRole("ADMIN", "USER")
                //DELETE
                .antMatchers(HttpMethod.DELETE, "/events").hasAnyRole("ADMIN", "USER")



                /**********************************************PRODUCT*************************************************/
                //GET
                .antMatchers(HttpMethod.GET, "/products").hasAnyRole("ADMIN", "USER")
                //POST
                .antMatchers(HttpMethod.POST, "/products").hasAnyRole( "ADMIN", "USER")
                //PUT
                .antMatchers(HttpMethod.PUT, "/products").hasAnyRole("ADMIN", "USER")
                //DELETE
                .antMatchers(HttpMethod.DELETE, "/products").hasAnyRole("ADMIN", "USER")



                /*******************************************USERUSEREVENT**********************************************/
                //GET
                .antMatchers(HttpMethod.GET, "/usersUsersEvents").hasAnyRole("ADMIN", "USER")
                //POST
                .antMatchers(HttpMethod.POST, "/usersUsersEvents").hasAnyRole("ADMIN", "USER")
                //PUT
                .antMatchers(HttpMethod.PUT, "/usersUsersEvents").hasAnyRole("ADMIN", "USER")
                //DELETE
                .antMatchers(HttpMethod.DELETE, "/usersUsersEvents").hasAnyRole("ADMIN", "USER")



                /**********************************************EMAIL***************************************************/
                //POST
                .antMatchers(HttpMethod.POST, "/email").hasAnyRole("ADMIN", "USER")

                .anyRequest().authenticated();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.SUserPrincipalDetails);

        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}