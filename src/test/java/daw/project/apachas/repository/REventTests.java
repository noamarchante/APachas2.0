package daw.project.apachas.repository;

import daw.project.apachas.entity.Event;
import daw.project.apachas.entity.User;
import daw.project.apachas.entity.UserEvent;
import daw.project.apachas.entity.id.UserEventId;
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
public class REventTests {
    @Autowired
    @Qualifier("REvent")
    REvent rEvent;

    @Autowired
    private TestEntityManager em;

    @Test
    public void testFindByEventId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        em.persist(user);
        em.persist(user2);
        em.persist(event);
        em.persist(event2);

        Event savedEvent = rEvent.findByEventId(event.getEventId());

        assertThat(savedEvent).isEqualTo(event);
    }

    @Test
    public void testFindAll() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        em.persist(user);
        em.persist(user2);
        em.persist(event);
        em.persist(event2);

        Page<Event> savedEvent = rEvent.findAll(Pageable.unpaged());

        assertThat(savedEvent).hasSize(2).contains(event,event2);
    }


    @Test
    public void testFindByEvent_EventIdAndDebtGreaterThanEqualOrderByDebtAsc() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);
        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, false);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, false);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);

        List<UserEvent> savedUserEvent = rEvent.findByEvent_EventIdAndDebtGreaterThanEqualOrderByDebtAsc(userEvent.getUserEventId().getEventId());

        assertThat(savedUserEvent).hasSize(2).contains(userEvent,userEvent2);
    }
}