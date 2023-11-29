package daw.project.apachas.repository;

import daw.project.apachas.entity.User;
import daw.project.apachas.entity.UserUser;
import daw.project.apachas.entity.id.UserUserId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RUserUserTests {
    @Autowired
    @Qualifier("RUserUser")
    RUserUser rUserUser;

    @Autowired
    private TestEntityManager em;

    @Test
    public void testFindByUserUserId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        UserUser userUser = new UserUser(new UserUserId(1,2), false);
        userUser.setUser(user);
        userUser.setFriend(user2);

        em.persist(user);
        em.persist(user2);
        em.persist(userUser);


        UserUser savedUser = rUserUser.findByUserUserId(userUser.getUserUserId());

        assertThat(savedUser).isEqualTo(userUser);
    }

    @Test
    public void testFindNotifications() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        UserUser userUser = new UserUser(new UserUserId(1,2), false);
        userUser.setUser(user);
        userUser.setFriend(user2);

        em.persist(user);
        em.persist(user2);
        em.persist(userUser);


        String[] savedUser = rUserUser.findNotifications(user.getUserId());

        assertThat(savedUser).hasSize(1).contains(user2.getUserLogin());
    }

    @Test
    public void testFindFriendsByFriendId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        UserUser userUser = new UserUser(new UserUserId(1,2), false);
        userUser.setUser(user);
        userUser.setFriend(user2);

        em.persist(user);
        em.persist(user2);
        em.persist(userUser);


        List<User> savedUser = rUserUser.findFriendsByFriendId(user2.getUserId());

        assertThat(savedUser).hasSize(1).contains(user);
    }

    @Test
    public void testFindFriendsByUserId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        UserUser userUser = new UserUser(new UserUserId(1,2), false);
        userUser.setUser(user);
        userUser.setFriend(user2);

        em.persist(user);
        em.persist(user2);
        em.persist(userUser);


        List<User> savedUser = rUserUser.findFriendsByUserId(user.getUserId());

        assertThat(savedUser).hasSize(1).contains(user2);
    }

    @Test
    public void testCountMutualFriends() {
        User user = new User(0, "noa", "López Marchante", "noamarchanteg", "abc123.", "noamarchanteg@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlancog", "abc123.", "laurablancog@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3g", "abc123.", "u3g@gmail.com",new Timestamp(12) , null, "USER", "");

        user.setUserVerified(true);
        user2.setUserVerified(true);
        user3.setUserVerified(true);
        UserUser userUser = new UserUser(new UserUserId(1,2), false);
        userUser.setUser(user);
        userUser.setFriend(user2);

        UserUser userUser2 = new UserUser(new UserUserId(1,3), true);
        userUser2.setUser(user);
        userUser2.setFriend(user3);

        UserUser userUser3 = new UserUser(new UserUserId(2,3), true);
        userUser3.setUser(user2);
        userUser3.setFriend(user3);

        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(userUser);
        em.persist(userUser2);
        em.persist(userUser3);

        Long savedUser = rUserUser.countMutualFriends(user.getUserId(), user2.getUserId());

        assertThat(savedUser).isEqualTo(1);
    }

    @Test
    public void testFindPageableMutualFriends() {
        User user = new User(0, "noa", "López Marchante", "noamarchanteg", "abc123.", "noamarchanteg@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlancog", "abc123.", "laurablancog@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3g", "abc123.", "u3g@gmail.com",new Timestamp(12) , null, "USER", "");

        user.setUserVerified(true);
        user2.setUserVerified(true);
        user3.setUserVerified(true);
        UserUser userUser = new UserUser(new UserUserId(1,2), false);
        userUser.setUser(user);
        userUser.setFriend(user2);

        UserUser userUser2 = new UserUser(new UserUserId(1,3), true);
        userUser2.setUser(user);
        userUser2.setFriend(user3);

        UserUser userUser3 = new UserUser(new UserUserId(2,3), true);
        userUser3.setUser(user2);
        userUser3.setFriend(user3);

        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(userUser);
        em.persist(userUser2);
        em.persist(userUser3);

       Page<User> savedUser = rUserUser.findPageableMutualFriends(user.getUserId(), user2.getUserId(), Pageable.unpaged());

        assertThat(savedUser).hasSize(1).contains(user3);
    }

    @Test
    public void testFindNewRequest() {
        User user = new User(0, "noa", "López Marchante", "noamarchanteg", "abc123.", "noamarchanteg@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlancog", "abc123.", "laurablancog@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3g", "abc123.", "u3g@gmail.com",new Timestamp(12) , null, "USER", "");

        user.setUserVerified(true);
        user2.setUserVerified(true);
        user3.setUserVerified(true);
        UserUser userUser = new UserUser(new UserUserId(1,2), false);
        userUser.setUser(user);
        userUser.setFriend(user2);

        UserUser userUser2 = new UserUser(new UserUserId(1,3), true);
        userUser2.setUser(user);
        userUser2.setFriend(user3);

        UserUser userUser3 = new UserUser(new UserUserId(2,3), true);
        userUser3.setUser(user2);
        userUser3.setFriend(user3);

        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(userUser);
        em.persist(userUser2);
        em.persist(userUser3);

        List<User> savedUser = rUserUser.findNewRequest(userUser.getUserUserCreation(), userUser.getUserUserCreation());

        assertThat(savedUser).hasSize(2).contains(user,user2);
    }
}