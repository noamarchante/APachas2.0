package daw.project.apachas.repository;

import daw.project.apachas.entity.Group;
import daw.project.apachas.entity.GroupUser;
import daw.project.apachas.entity.User;
import daw.project.apachas.entity.id.GroupUserId;
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
public class RGroupUserTests {

    @Autowired
    @Qualifier("RGroupUser")
    RGroupUser rGroupUser;

    @Autowired
    private TestEntityManager em;

    @Test
    public void testFindByGroupUserId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Group group = new Group(0, "g1", "descripcion del grupo 1", null , user);
        Group group2 = new Group(0, "g2", "Descripcion del grupo 2", null, user2);

        GroupUser groupUser = new GroupUser(new GroupUserId(1,1));
        groupUser.setGroup(group);
        groupUser.setUser(user);
        GroupUser groupUser2 = new GroupUser(new GroupUserId(1,2));
        groupUser2.setGroup(group);
        groupUser2.setUser(user2);
        GroupUser groupUser3 = new GroupUser(new GroupUserId(2,1));
        groupUser3.setGroup(group2);
        groupUser3.setUser(user);

        em.persist(user);
        em.persist(user2);
        em.persist(group);
        em.persist(group2);
        em.persist(groupUser);
        em.persist(groupUser2);
        em.persist(groupUser3);

        GroupUser savedGroupUser = rGroupUser.findByGroupUserId(groupUser.getGroupUserId());

        assertThat(savedGroupUser).isEqualTo(groupUser);
    }

    @Test
    public void testFindByGroupUserId_UserId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Group group = new Group(0, "g1", "descripcion del grupo 1", null , user);
        Group group2 = new Group(0, "g2", "Descripcion del grupo 2", null, user2);

        GroupUser groupUser = new GroupUser(new GroupUserId(1,1));
        groupUser.setGroup(group);
        groupUser.setUser(user);
        GroupUser groupUser2 = new GroupUser(new GroupUserId(1,2));
        groupUser2.setGroup(group);
        groupUser2.setUser(user2);
        GroupUser groupUser3 = new GroupUser(new GroupUserId(2,1));
        groupUser3.setGroup(group2);
        groupUser3.setUser(user);

        em.persist(user);
        em.persist(user2);
        em.persist(group);
        em.persist(group2);
        em.persist(groupUser);
        em.persist(groupUser2);
        em.persist(groupUser3);

        List<GroupUser> savedGroupUser = rGroupUser.findByGroupUserId_UserId(groupUser.getGroupUserId().getUserId());

        assertThat(savedGroupUser).hasSize(2).contains(groupUser, groupUser3);
    }

    @Test
    public void testFindByGroupUserId_GroupId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Group group = new Group(0, "g1", "descripcion del grupo 1", null , user);
        Group group2 = new Group(0, "g2", "Descripcion del grupo 2", null, user2);

        GroupUser groupUser = new GroupUser(new GroupUserId(1,1));
        groupUser.setGroup(group);
        groupUser.setUser(user);
        GroupUser groupUser2 = new GroupUser(new GroupUserId(1,2));
        groupUser2.setGroup(group);
        groupUser2.setUser(user2);
        GroupUser groupUser3 = new GroupUser(new GroupUserId(2,1));
        groupUser3.setGroup(group2);
        groupUser3.setUser(user);

        em.persist(user);
        em.persist(user2);
        em.persist(group);
        em.persist(group2);
        em.persist(groupUser);
        em.persist(groupUser2);
        em.persist(groupUser3);

        List<GroupUser> savedGroupUser = rGroupUser.findByGroupUserId_GroupId(groupUser.getGroupUserId().getGroupId());

        assertThat(savedGroupUser).hasSize(2).contains(groupUser, groupUser2);
    }

    @Test
    public void testCountByGroupUserId_GroupIdAndGroupUserActiveTrue() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Group group = new Group(0, "g1", "descripcion del grupo 1", null , user);
        Group group2 = new Group(0, "g2", "Descripcion del grupo 2", null, user2);

        GroupUser groupUser = new GroupUser(new GroupUserId(1,1));
        groupUser.setGroup(group);
        groupUser.setUser(user);
        GroupUser groupUser2 = new GroupUser(new GroupUserId(1,2));
        groupUser2.setGroup(group);
        groupUser2.setUser(user2);
        GroupUser groupUser3 = new GroupUser(new GroupUserId(2,1));
        groupUser3.setGroup(group2);
        groupUser3.setUser(user);

        em.persist(user);
        em.persist(user2);
        em.persist(group);
        em.persist(group2);
        em.persist(groupUser);
        em.persist(groupUser2);
        em.persist(groupUser3);

        Long savedGroupUser = rGroupUser.countByGroupUserId_GroupIdAndGroupUserActiveTrue(groupUser.getGroupUserId().getGroupId());

        assertThat(savedGroupUser).isEqualTo(2);
    }

    @Test
    public void testFindPageableMembers() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Group group = new Group(0, "g1", "descripcion del grupo 1", null , user);
        Group group2 = new Group(0, "g2", "Descripcion del grupo 2", null, user2);

        GroupUser groupUser = new GroupUser(new GroupUserId(1,1));
        groupUser.setGroup(group);
        groupUser.setUser(user);
        GroupUser groupUser2 = new GroupUser(new GroupUserId(1,2));
        groupUser2.setGroup(group);
        groupUser2.setUser(user2);
        GroupUser groupUser3 = new GroupUser(new GroupUserId(2,1));
        groupUser3.setGroup(group2);
        groupUser3.setUser(user);

        em.persist(user);
        em.persist(user2);
        em.persist(group);
        em.persist(group2);
        em.persist(groupUser);
        em.persist(groupUser2);
        em.persist(groupUser3);

        Page<User> savedGroupUser = rGroupUser.findPageableMembers(groupUser.getGroupUserId().getGroupId(), Pageable.unpaged());

        assertThat(savedGroupUser).hasSize(2).contains(user,user2);
    }

    @Test
    public void testFindMembers() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Group group = new Group(0, "g1", "descripcion del grupo 1", null , user);
        Group group2 = new Group(0, "g2", "Descripcion del grupo 2", null, user2);

        GroupUser groupUser = new GroupUser(new GroupUserId(1,1));
        groupUser.setGroup(group);
        groupUser.setUser(user);
        GroupUser groupUser2 = new GroupUser(new GroupUserId(1,2));
        groupUser2.setGroup(group);
        groupUser2.setUser(user2);
        GroupUser groupUser3 = new GroupUser(new GroupUserId(2,1));
        groupUser3.setGroup(group2);
        groupUser3.setUser(user);

        em.persist(user);
        em.persist(user2);
        em.persist(group);
        em.persist(group2);
        em.persist(groupUser);
        em.persist(groupUser2);
        em.persist(groupUser3);

        List<User> savedGroupUser = rGroupUser.findMembers(groupUser.getGroupUserId().getGroupId());

        assertThat(savedGroupUser).hasSize(2).contains(user,user2);
    }

    @Test
    public void testFindPageableSearchGroups() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Group group = new Group(0, "g1", "descripcion del grupo 1", null , user);
        Group group2 = new Group(0, "g2", "Descripcion del grupo 2", null, user2);

        GroupUser groupUser = new GroupUser(new GroupUserId(1,1));
        groupUser.setGroup(group);
        groupUser.setUser(user);
        GroupUser groupUser2 = new GroupUser(new GroupUserId(1,2));
        groupUser2.setGroup(group);
        groupUser2.setUser(user2);
        GroupUser groupUser3 = new GroupUser(new GroupUserId(2,1));
        groupUser3.setGroup(group2);
        groupUser3.setUser(user);

        em.persist(user);
        em.persist(user2);
        em.persist(group);
        em.persist(group2);
        em.persist(groupUser);
        em.persist(groupUser2);
        em.persist(groupUser3);

        Page<Group> savedGroupUser = rGroupUser.findPageableSearchGroups(user.getUserId(), "g", Pageable.unpaged());

        assertThat(savedGroupUser).hasSize(2).contains(group,group2);
    }

    @Test
    public void testFindPageableGroups() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Group group = new Group(0, "g1", "descripcion del grupo 1", null , user);
        Group group2 = new Group(0, "g2", "Descripcion del grupo 2", null, user2);

        GroupUser groupUser = new GroupUser(new GroupUserId(1,1));
        groupUser.setGroup(group);
        groupUser.setUser(user);
        GroupUser groupUser2 = new GroupUser(new GroupUserId(1,2));
        groupUser2.setGroup(group);
        groupUser2.setUser(user2);
        GroupUser groupUser3 = new GroupUser(new GroupUserId(2,1));
        groupUser3.setGroup(group2);
        groupUser3.setUser(user);

        em.persist(user);
        em.persist(user2);
        em.persist(group);
        em.persist(group2);
        em.persist(groupUser);
        em.persist(groupUser2);
        em.persist(groupUser3);

        Page<Group> savedGroupUser = rGroupUser.findPageableGroups(user.getUserId(), Pageable.unpaged());

        assertThat(savedGroupUser).hasSize(2).contains(group,group2);
    }

    @Test
    public void testCountGroups() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Group group = new Group(0, "g1", "descripcion del grupo 1", null , user);
        Group group2 = new Group(0, "g2", "Descripcion del grupo 2", null, user2);

        GroupUser groupUser = new GroupUser(new GroupUserId(1,1));
        groupUser.setGroup(group);
        groupUser.setUser(user);
        GroupUser groupUser2 = new GroupUser(new GroupUserId(1,2));
        groupUser2.setGroup(group);
        groupUser2.setUser(user2);
        GroupUser groupUser3 = new GroupUser(new GroupUserId(2,1));
        groupUser3.setGroup(group2);
        groupUser3.setUser(user);

        em.persist(user);
        em.persist(user2);
        em.persist(group);
        em.persist(group2);
        em.persist(groupUser);
        em.persist(groupUser2);
        em.persist(groupUser3);

        Long savedGroupUser = rGroupUser.countGroups(user.getUserId());

        assertThat(savedGroupUser).isEqualTo(2);
    }

    @Test
    public void testCountSearchGroups() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Group group = new Group(0, "g1", "descripcion del grupo 1", null , user);
        Group group2 = new Group(0, "g2", "Descripcion del grupo 2", null, user2);

        GroupUser groupUser = new GroupUser(new GroupUserId(1,1));
        groupUser.setGroup(group);
        groupUser.setUser(user);
        GroupUser groupUser2 = new GroupUser(new GroupUserId(1,2));
        groupUser2.setGroup(group);
        groupUser2.setUser(user2);
        GroupUser groupUser3 = new GroupUser(new GroupUserId(2,1));
        groupUser3.setGroup(group2);
        groupUser3.setUser(user);

        em.persist(user);
        em.persist(user2);
        em.persist(group);
        em.persist(group2);
        em.persist(groupUser);
        em.persist(groupUser2);
        em.persist(groupUser3);

        Long savedGroupUser = rGroupUser.countSearchGroups(user.getUserId(), "1");

        assertThat(savedGroupUser).isEqualTo(1);
    }

    @Test
    public void testCountMutualGroups() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Group group = new Group(0, "g1", "descripcion del grupo 1", null , user);
        Group group2 = new Group(0, "g2", "Descripcion del grupo 2", null, user2);

        GroupUser groupUser = new GroupUser(new GroupUserId(1,1));
        groupUser.setGroup(group);
        groupUser.setUser(user);
        GroupUser groupUser2 = new GroupUser(new GroupUserId(1,2));
        groupUser2.setGroup(group);
        groupUser2.setUser(user2);
        GroupUser groupUser3 = new GroupUser(new GroupUserId(2,1));
        groupUser3.setGroup(group2);
        groupUser3.setUser(user);

        em.persist(user);
        em.persist(user2);
        em.persist(group);
        em.persist(group2);
        em.persist(groupUser);
        em.persist(groupUser2);
        em.persist(groupUser3);

        Long savedGroupUser = rGroupUser.countMutualGroups(user.getUserId(), user2.getUserId());

        assertThat(savedGroupUser).isEqualTo(1);
    }

    @Test
    public void testFindPageableMutualGroups() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Group group = new Group(0, "g1", "descripcion del grupo 1", null , user);
        Group group2 = new Group(0, "g2", "Descripcion del grupo 2", null, user2);

        GroupUser groupUser = new GroupUser(new GroupUserId(1,1));
        groupUser.setGroup(group);
        groupUser.setUser(user);
        GroupUser groupUser2 = new GroupUser(new GroupUserId(1,2));
        groupUser2.setGroup(group);
        groupUser2.setUser(user2);
        GroupUser groupUser3 = new GroupUser(new GroupUserId(2,1));
        groupUser3.setGroup(group2);
        groupUser3.setUser(user);

        em.persist(user);
        em.persist(user2);
        em.persist(group);
        em.persist(group2);
        em.persist(groupUser);
        em.persist(groupUser2);
        em.persist(groupUser3);

        Page<Group> savedGroupUser = rGroupUser.findPageableMutualGroups(user.getUserId(), user2.getUserId(), Pageable.unpaged());

        assertThat(savedGroupUser).hasSize(1).contains(group);
    }

    @Test
    public void testGetGroups() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Group group = new Group(0, "g1", "descripcion del grupo 1", null , user);
        Group group2 = new Group(0, "g2", "Descripcion del grupo 2", null, user2);

        GroupUser groupUser = new GroupUser(new GroupUserId(1,1));
        groupUser.setGroup(group);
        groupUser.setUser(user);
        GroupUser groupUser2 = new GroupUser(new GroupUserId(1,2));
        groupUser2.setGroup(group);
        groupUser2.setUser(user2);
        GroupUser groupUser3 = new GroupUser(new GroupUserId(2,1));
        groupUser3.setGroup(group2);
        groupUser3.setUser(user);

        em.persist(user);
        em.persist(user2);
        em.persist(group);
        em.persist(group2);
        em.persist(groupUser);
        em.persist(groupUser2);
        em.persist(groupUser3);

        List<Group> savedGroupUser = rGroupUser.getGroups(user.getUserId());

        assertThat(savedGroupUser).hasSize(2).contains(group, group2);
    }

    @Test
    public void testFindAddedNewGroup() {
        User user = new User(0, "noa", "López Marchante", "noamarchante2", "abc123.", "noamarchante2@gmail.com", new Timestamp(12), null, "USER", "");
        user.setUserActive(true);
        user.setUserNotify(true);
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Group group = new Group(0, "gn1", "descripcion del grupo 1", null , user);
        Group group2 = new Group(0, "gn2", "Descripcion del grupo 2", null, user2);

        GroupUser groupUser = new GroupUser(new GroupUserId(1,1));
        groupUser.setGroup(group);
        groupUser.setUser(user);
        groupUser.setGroupUserCreation(new Timestamp(12));
        GroupUser groupUser2 = new GroupUser(new GroupUserId(1,2));
        groupUser2.setGroup(group);
        groupUser2.setUser(user2);
        GroupUser groupUser3 = new GroupUser(new GroupUserId(2,1));
        groupUser3.setGroup(group2);
        groupUser3.setUser(user);

        em.persist(user);
        em.persist(user2);
        em.persist(group);
        em.persist(group2);
        em.persist(groupUser);
        em.persist(groupUser2);
        em.persist(groupUser3);

        List<User> savedGroupUser = rGroupUser.findAddedNewGroup(new Timestamp(12), new Timestamp(12));

        assertThat(savedGroupUser).hasSize(1).contains(user);
    }

    @Test
    public void testFindAllByGroupUserCreationIsNotNull() {
        User user = new User(0, "noa", "López Marchante", "noamarchante2", "abc123.", "noamarchante2@gmail.com", new Timestamp(12), null, "USER", "");
        user.setUserActive(true);
        user.setUserNotify(true);
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Group group = new Group(0, "gn1", "descripcion del grupo 1", null , user);
        Group group2 = new Group(0, "gn2", "Descripcion del grupo 2", null, user2);

        GroupUser groupUser = new GroupUser(new GroupUserId(1,1));
        groupUser.setGroup(group);
        groupUser.setUser(user);
        groupUser.setGroupUserCreation(new Timestamp(12));
        GroupUser groupUser2 = new GroupUser(new GroupUserId(1,2));
        groupUser2.setGroup(group);
        groupUser2.setUser(user2);
        GroupUser groupUser3 = new GroupUser(new GroupUserId(2,1));
        groupUser3.setGroup(group2);
        groupUser3.setUser(user);

        em.persist(user);
        em.persist(user2);
        em.persist(group);
        em.persist(group2);
        em.persist(groupUser);
        em.persist(groupUser2);
        em.persist(groupUser3);

        List<GroupUser> savedGroupUser = rGroupUser.findAllByGroupUserCreationIsNotNull();

        assertThat(savedGroupUser).hasSize(3).contains(groupUser);
    }
}