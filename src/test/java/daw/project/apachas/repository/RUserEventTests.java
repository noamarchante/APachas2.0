package daw.project.apachas.repository;

import daw.project.apachas.entity.Event;
import daw.project.apachas.entity.User;
import daw.project.apachas.entity.UserEvent;
import daw.project.apachas.entity.UserUserEvent;
import daw.project.apachas.entity.id.UserUserEventId;
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
public class RUserEventTests {
    @Autowired
    @Qualifier("RUserEvent")
    RUserEvent rUserEvent;

    @Autowired
    private TestEntityManager em;

    @Test
    public void testFindByUserEventId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);
        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
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

        UserEvent savedUserEvent = rUserEvent.findByUserEventId(userEvent.getUserEventId());

        assertThat(savedUserEvent).isEqualTo(userEvent);
    }

    @Test
    public void testFindByUserEventId_EventId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);
        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
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

        List<UserEvent> savedUserEvent = rUserEvent.findByUserEventId_EventId(event.getEventId());

        assertThat(savedUserEvent).hasSize(3).contains(userEvent, userEvent2, userEvent3);
    }

    @Test
    public void testFindNotifications() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);
        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
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

        String[] savedUserEvent = rUserEvent.findNotifications(user.getUserId());

        assertThat(savedUserEvent).hasSize(2).contains(event.getEventName(), event2.getEventName());
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
        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
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

        List<UserEvent> savedUserEvent = rUserEvent.findByEvent_EventIdAndDebtGreaterThanEqualOrderByDebtAsc(event.getEventId());

        assertThat(savedUserEvent).hasSize(2).contains(userEvent, userEvent2);
    }

    @Test
    public void testFindByEvent_EventIdAndDebtGreaterThanEqualOrderByDebtDesc() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);
        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
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

        List<UserEvent> savedUserEvent = rUserEvent.findByEvent_EventIdAndDebtGreaterThanEqualOrderByDebtDesc(event.getEventId());

        assertThat(savedUserEvent).hasSize(1).contains(userEvent3);
    }

    @Test
    public void testFindAll() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);
        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
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

        Page<UserEvent> savedUserEvent = rUserEvent.findAll(Pageable.unpaged());

        assertThat(savedUserEvent).hasSize(4).contains(userEvent, userEvent2, userEvent3, userEvent4);
    }

    @Test
    public void testFindPageableSearchEvents() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);
        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
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

        Page<Event> savedUserEvent = rUserEvent.findPageableSearchEvents(user.getUserId(), "e", Pageable.unpaged());

        assertThat(savedUserEvent).hasSize(2).contains(event, event2);
    }

    @Test
    public void testFindPageableSearchEventsWithFinished() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);
        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);

        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);

        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);


        Page<Event> savedUserEvent = rUserEvent.findPageableSearchEventsWithFinished(user.getUserId(), "e", Pageable.unpaged());

        assertThat(savedUserEvent).hasSize(1).contains(event3);
    }

    @Test
    public void testFindPageableEvents() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);

        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);

        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);


        Page<Event> savedUserEvent = rUserEvent.findPageableEvents(user.getUserId(), Pageable.unpaged());

        assertThat(savedUserEvent).hasSize(2).contains(event, event2);
    }

    @Test
    public void testFindPageableEventsWithFinished() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);

        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);

        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);


        Page<Event> savedUserEvent = rUserEvent.findPageableEventsWithFinished(user.getUserId(), Pageable.unpaged());

        assertThat(savedUserEvent).hasSize(1).contains(event3);
    }

    @Test
    public void testCountEvents() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);

        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);

        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);


        Long savedUserEvent = rUserEvent.countEvents(user.getUserId());

        assertThat(savedUserEvent).isEqualTo(2);
    }

    @Test
    public void testSumTotalEventExpense() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);

        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);

        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);

        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);


        Double savedUserEvent = rUserEvent.sumTotalEventExpense(event.getEventId());

        assertThat(savedUserEvent).isEqualTo(46.2);
    }

    @Test
    public void testCountEventsWithFinished() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);

        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);

        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);


        Long savedUserEvent = rUserEvent.countEventsWithFinished(user.getUserId());

        assertThat(savedUserEvent).isEqualTo(1);
    }

    @Test
    public void testCountSearchEvents() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);

        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);

        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);


        Long savedUserEvent = rUserEvent.countSearchEvents(user.getUserId(), "e");

        assertThat(savedUserEvent).isEqualTo(2);
    }

    @Test
    public void testCountSearchEventsWithFinished() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);

        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);

        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);


        Long savedUserEvent = rUserEvent.countSearchEventsWithFinished(user.getUserId(), "e");

        assertThat(savedUserEvent).isEqualTo(1);
    }

    @Test
    public void testCountMutualEvents() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);

        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);

        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);

        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);


        Long savedUserEvent = rUserEvent.countMutualEvents(user2.getUserId(), user3.getUserId());

        assertThat(savedUserEvent).isEqualTo(1);
    }

    @Test
    public void testFindPageableMutualEvents() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);

        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);

        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);


        Page<Event> savedUserEvent = rUserEvent.findPageableMutualEvents(user2.getUserId(), user2.getUserId(), Pageable.unpaged());

        assertThat(savedUserEvent).hasSize(1).contains(event);
    }

    @Test
    public void testCountPartakers() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);

        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);

        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);


        Long savedUserEvent = rUserEvent.countPartakers(event.getEventId());

        assertThat(savedUserEvent).isEqualTo(2);
    }

    @Test
    public void testFindPageablePartakers() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);

        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);

        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);


        Page<User> savedUserEvent = rUserEvent.findPageablePartakers(event.getEventId(), Pageable.unpaged());

        assertThat(savedUserEvent).hasSize(2).contains(user2,user3);
    }

    @Test
    public void testFindPartakers() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);

        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);

        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);


        List<User> savedUserEvent = rUserEvent.findPartakers(event.getEventId());

        assertThat(savedUserEvent).hasSize(3).contains(user,user2,user3);
    }

    @Test
    public void testFindUserEventByUserEventId_EventIdAndUserEventId_UserId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);

        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);

        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);

        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);


        UserEvent savedUserEvent = rUserEvent.findUserEventByUserEventId_EventIdAndUserEventId_UserId(event.getEventId(), user.getUserId());

        assertThat(savedUserEvent).isEqualTo(userEvent);
    }

   /*@Test
    public void testFindPageableUserEvents() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");
        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);
        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);
        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);
        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);
        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);
        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);
        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);
        Page<UserEvent> savedUserEvent = rUserEvent.findPageableUserEvents(event.getEventId(), Pageable.unpaged());
        assertThat(savedUserEvent).hasSize(2);
    }*/

    /*@Test
    public void testFindUserEvents() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");
        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);
        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);
        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);
        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);
        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);
        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);
        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);
        List<UserEvent> savedUserEvent = rUserEvent.findUserEvents(event.getEventId());
        assertThat(savedUserEvent).hasSize(3).contains(userEvent, userEvent2, userEvent3);
    }*/

    @Test
    public void testCountUserEvents() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);

        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);

        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);

        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);


        Long savedUserEvent = rUserEvent.countUserEvents(event.getEventId());

        assertThat(savedUserEvent).isEqualTo(2);
    }

    /*@Test
    public void testFindPageableSearchUserEvents() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");
        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);
        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);
        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);
        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);
        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);
        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);
        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);
        Page<UserEvent> savedUserEvent = rUserEvent.findPageableSearchUserEvents(event.getEventId(), "a" , Pageable.unpaged());
        assertThat(savedUserEvent).hasSize(1).contains(userEvent2);
    }*/

    @Test
    public void testCountSearchUserEvents() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);

        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);

        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);


        Long savedUserEvent = rUserEvent.countSearchUserEvents(event.getEventId(), "a" );

        assertThat(savedUserEvent).isEqualTo(1);
    }

    @Test
    public void testFindOpenEvents() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);

        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);

        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);


        List<Event> savedUserEvent = rUserEvent.findOpenEvents(user.getUserId());

        assertThat(savedUserEvent).hasSize(2).contains(event, event2);
    }

    @Test
    public void testFindClosedEvents() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);

        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);

        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);

        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);


        List<Event> savedUserEvent = rUserEvent.findClosedEvents(user.getUserId());

        assertThat(savedUserEvent).hasSize(1).contains(event3);
    }

    @Test
    public void testFindAddedNewEvent() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);

        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);

        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);


        List<User> savedUserEvent = rUserEvent.findAddedNewEvent(userEvent.getUserEventCreation(),userEvent4.getUserEventCreation());

        assertThat(savedUserEvent).hasSize(3).contains(user, user2, user3);
    }

    @Test
    public void testFindNearEvent() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);

        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);

        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);



        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);


        List<User> savedUserEvent = rUserEvent.findNearEvent(new Timestamp(12));

        assertThat(savedUserEvent).hasSize(3).contains(user, user2, user3);
    }

    @Test
    public void testFindEventStart() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);

        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);

        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);


        List<User> savedUserEvent = rUserEvent.findEventStart(new Timestamp(12));

        assertThat(savedUserEvent).hasSize(3).contains(user, user2, user3);
    }

    @Test
    public void testFindEventFinished() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);

        UserEvent userEvent = new UserEvent(new UserEventId(1,1), 15.4, 5.0, false);
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEvent.setUserEventActive(true);

        UserEvent userEvent2 = new UserEvent(new UserEventId(1,2), 15.4, 5.0, true);
        userEvent2.setUser(user2);
        userEvent2.setEvent(event);
        userEvent2.setUserEventActive(true);

        UserEvent userEvent3 = new UserEvent(new UserEventId(1,3), 15.4, -3.0, true);
        userEvent3.setUser(user3);
        userEvent3.setEvent(event);
        userEvent3.setUserEventActive(true);

        UserEvent userEvent4 = new UserEvent(new UserEventId(2,1), 15.4, 5.0, false);
        userEvent4.setUser(user);
        userEvent4.setEvent(event2);
        userEvent4.setUserEventActive(true);

        UserEvent userEvent5 = new UserEvent(new UserEventId(3,1), 15.4, 5.0, true);
        userEvent5.setUser(user);
        userEvent5.setEvent(event3);
        userEvent5.setUserEventActive(true);

        UserEvent userEvent6 = new UserEvent(new UserEventId(3,2), 15.4, 5.0, true);
        userEvent6.setUser(user2);
        userEvent6.setEvent(event3);
        userEvent6.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        em.persist(user);
        em.persist(user2);
        em.persist(user3);
        em.persist(event);
        em.persist(event2);
        em.persist(event3);
        em.persist(userEvent);
        em.persist(userEvent2);
        em.persist(userEvent3);
        em.persist(userEvent4);
        em.persist(userEvent5);
        em.persist(userEvent6);
        em.persist(userUserEvent);

        List<User> savedUserEvent = rUserEvent.findEventFinished(userUserEvent.getUserUserEventCreation(), userUserEvent.getUserUserEventCreation());

        assertThat(savedUserEvent).hasSize(2).contains(user, user2);
    }
}