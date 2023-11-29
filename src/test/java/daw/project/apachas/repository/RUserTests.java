package daw.project.apachas.repository;

import daw.project.apachas.entity.User;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.sql.Timestamp;

@DataJpaTest
public class RUserTests {

    @Autowired
    @Qualifier("RUser")
    RUser rUser;

    @Autowired
    private TestEntityManager em;

    @Test
    public void testFindByUserId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");


        em.persist(user);
        em.persist(user2);

        User savedUser = rUser.findByUserId(user.getUserId());

        assertThat(savedUser).isEqualTo(user);
    }

    @Test
    public void testFindByUserEmail() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");


        em.persist(user);
        em.persist(user2);

        User savedUser = rUser.findByUserEmail(user.getUserEmail());

        assertThat(savedUser).isEqualTo(user);
    }

    @Test
    public void testFindByUserEmailAndUserActiveTrueAndUserVerifiedTrue() {
        User user = new User(0, "prueba", "prueba", "prueba", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        user.setUserVerified(true);

        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        em.persist(user);
        em.persist(user2);

        User savedUser = rUser.findByUserEmailAndUserActiveTrueAndUserVerifiedTrue(user.getUserEmail());

        assertThat(savedUser).isEqualTo(user);
    }

    @Test
    public void testFindByUserEmailAndUserVerifiedFalse() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        em.persist(user);
        em.persist(user2);

        User savedUser = rUser.findByUserEmailAndUserVerifiedFalse(user.getUserEmail());

        assertThat(savedUser).isEqualTo(user);
    }

    @Test
    public void testFindByUserLoginAndUserActiveTrueAndUserVerifiedTrue() {
        User user = new User(0, "noa", "López Marchante", "noamarchanted", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        user.setUserVerified(true);

        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        em.persist(user);
        em.persist(user2);

        User savedUser = rUser.findByUserLoginAndUserActiveTrueAndUserVerifiedTrue(user.getUserLogin());

        assertThat(savedUser).isEqualTo(user);
    }


    @Test
    public void testFindByUserIdAndUserActiveTrueAndUserVerifiedTrue() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        user.setUserVerified(true);

        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        em.persist(user);
        em.persist(user2);

        User savedUser = rUser.findByUserIdAndUserActiveTrueAndUserVerifiedTrue(user.getUserId());

        assertThat(savedUser).isEqualTo(user);
    }

    @Test
    public void testFindUsersByRolesEqualsAndUserIdIsNotAndUserActiveTrueAndUserVerifiedTrueOrderByUserLoginAsc() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        user.setUserVerified(true);

        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        user2.setUserVerified(true);
        user2.setRoles("ADMIN");

        User user3 = new User(0, "Juan Manuel", "Fuentes Macía", "juanmafuentes", "abc123.", "juanmafuentes@gmail.com",new Timestamp(12) , null, "USER", "");
        user3.setUserVerified(false);
        user3.setRoles("ADMIN");

        User user4 = new User(0, "Marcos", "García Fernández", "marcosgarcia", "abc123.", "marcosgarcia@gmail.com",new Timestamp(12) , null, "USER", "");
        user4.setUserVerified(true);


        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(user4);

        Page<User> savedUser = rUser.findUsersByRolesEqualsAndUserIdIsNotAndUserActiveTrueAndUserVerifiedTrueOrderByUserLoginAsc("USER", user4.getUserId(), Pageable.unpaged());

        assertThat(savedUser).hasSize(1).contains(user);
    }

    @Test
    public void testFindUsersByUserLoginContainingAndUserIdIsNotAndRolesEqualsAndUserActiveTrueAndUserVerifiedTrueOrderByUserLoginAsc() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        user.setUserVerified(true);

        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        user2.setUserVerified(true);
        user2.setRoles("ADMIN");

        User user3 = new User(0, "Juan Manuel", "Fuentes Macía", "juanmafuentes", "abc123.", "juanmafuentes@gmail.com",new Timestamp(12) , null, "USER", "");
        user3.setUserVerified(false);
        user3.setRoles("ADMIN");

        User user4 = new User(0, "Marcos", "García Fernández", "marcosgarcia", "abc123.", "marcosgarcia@gmail.com",new Timestamp(12) , null, "USER", "");
        user4.setUserVerified(true);

        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(user4);

        Page<User> savedUser = rUser.findUsersByUserLoginContainingAndUserIdIsNotAndRolesEqualsAndUserActiveTrueAndUserVerifiedTrueOrderByUserLoginAsc("m",user4.getUserId(),"USER", Pageable.unpaged());

        assertThat(savedUser.getContent()).hasSize(1).contains(user);
    }

    @Test
    public void testCountByRolesAndUserIdIsNotAndUserActiveTrueAndUserVerifiedTrue() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        user.setUserVerified(true);

        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        user2.setUserVerified(true);
        user2.setRoles("ADMIN");

        User user3 = new User(0, "Juan Manuel", "Fuentes Macía", "juanmafuentes", "abc123.", "juanmafuentes@gmail.com",new Timestamp(12) , null, "USER", "");
        user3.setUserVerified(false);
        user3.setRoles("USER");

        User user4 = new User(0, "Marcos", "García Fernández", "marcosgarcia", "abc123.", "marcosgarcia@gmail.com",new Timestamp(12) , null, "USER", "");
        user4.setUserVerified(true);
        user4.setRoles("USER");

        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(user4);

        Long countSavedUser = rUser.countByRolesAndUserIdIsNotAndUserActiveTrueAndUserVerifiedTrue("USER",user4.getUserId());

        assertThat(countSavedUser).isEqualTo(1);
    }

    @Test
    public void testCountByRolesAndUserLoginContainingAndUserIdIsNotAndUserActiveTrueAndUserVerifiedTrue() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        user.setUserVerified(true);

        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        user2.setUserVerified(true);
        user2.setRoles("USER");

        User user3 = new User(0, "Juan Manuel", "Fuentes Macía", "juanmafuentes", "abc123.", "juanmafuentes@gmail.com",new Timestamp(12) , null, "USER", "");
        user3.setUserVerified(false);
        user3.setRoles("USER");

        User user4 = new User(0, "Marcos", "García Fernández", "marcosgarcia", "abc123.", "marcosgarcia@gmail.com",new Timestamp(12) , null, "USER", "");
        user4.setUserVerified(true);
        user4.setRoles("ADMIN");


        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(user4);

        Long countSavedUser = rUser.countByRolesAndUserLoginContainingAndUserIdIsNotAndUserActiveTrueAndUserVerifiedTrue("USER","m",user4.getUserId());

        assertThat(countSavedUser).isEqualTo(1L);
    }

    @Test
    public void testFindByUserEmailAndTokenPasswordNull() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");

        em.persist(user);

        User savedUser = rUser.findByUserEmailAndTokenPasswordNull("noamarchante@gmail.com");

        assertThat(savedUser).isEqualTo(user);
    }

}
