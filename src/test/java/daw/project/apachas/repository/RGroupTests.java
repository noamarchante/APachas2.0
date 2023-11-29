package daw.project.apachas.repository;

import daw.project.apachas.entity.Group;
import daw.project.apachas.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.sql.Timestamp;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RGroupTests {

    @Autowired
    @Qualifier("RGroup")
    RGroup rGroup;

    @Autowired
    private TestEntityManager em;

    @Test
    public void testFindByEventId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Group group = new Group(0, "g1", "descripcion del grupo 1", null , user);
        Group group2 = new Group(0, "g2", "Descripcion del grupo 2", null, user2);

        em.persist(user);
        em.persist(user2);
        em.persist(group);
        em.persist(group2);

        Group savedGroup = rGroup.findByGroupId(group.getGroupId());

        assertThat(savedGroup).isEqualTo(group);
    }
}
