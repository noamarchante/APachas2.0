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
public class RUserUserEventTests {
    @Autowired
    @Qualifier("RUserUserEvent")
    RUserUserEvent rUserUserEvent;

    @Autowired
    private TestEntityManager em;

    @Test
    public void testFindByUserUserEventId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        UserUserEvent savedUserUserEvent = rUserUserEvent.findByUserUserEventId(userUserEvent.getUserUserEventId());

        assertThat(savedUserUserEvent).isEqualTo(userUserEvent);
    }

    @Test
    public void testFindByEvent_EventId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        List<UserUserEvent> savedUserUserEvent = rUserUserEvent.findByEvent_EventId(userUserEvent.getUserUserEventId().getEventId());

        assertThat(savedUserUserEvent).hasSize(1).contains(userUserEvent);
    }

    @Test
    public void testFindByUserUserEventIdAndUserUserEventActiveTrue() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        UserUserEvent savedUserUserEvent = rUserUserEvent.findByUserUserEventIdAndUserUserEventActiveTrue(userUserEvent.getUserUserEventId());

        assertThat(savedUserUserEvent).isEqualTo(userUserEvent);
    }

    @Test
    public void testFindPageableUserUserEvents() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Page<UserUserEvent> savedUserUserEvent = rUserUserEvent.findPageableUserUserEvents(userUserEvent.getUserUserEventId().getEventId(), Pageable.unpaged());

        assertThat(savedUserUserEvent).hasSize(1).contains(userUserEvent);
    }

    @Test
    public void testFindPageableUserUserEventsWithAuthUserId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Page<UserUserEvent> savedUserUserEvent = rUserUserEvent.findPageableUserUserEventsWithAuthUserId(user.getUserId(), Pageable.unpaged());

        assertThat(savedUserUserEvent).hasSize(1).contains(userUserEvent);
    }

    /*@Test
    public void testFindPageableUserUserEventsWithAuthUserIdByEvent() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Page<UserUserEvent> savedUserUserEvent = rUserUserEvent.findPageableUserUserEventsWithAuthUserIdByEvent(user2.getUserId(), Pageable.unpaged());

        assertThat(savedUserUserEvent).hasSize(2).contains(userUserEvent, userUserEvent2);
    }*/

    @Test
    public void testFindPageableUserUserEventsDebtsWithAuthUserId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Page<UserUserEvent> savedUserUserEvent = rUserUserEvent.findPageableUserUserEventsDebtsWithAuthUserId(user2.getUserId(), Pageable.unpaged());

        assertThat(savedUserUserEvent).hasSize(2).contains(userUserEvent, userUserEvent2);
    }

    /*@Test
    public void testFindPageableUserUserEventsDebtsWithAuthUserIdByEvent() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Page<UserUserEvent> savedUserUserEvent = rUserUserEvent.findPageableUserUserEventsDebtsWithAuthUserIdByEvent(user2.getUserId(), Pageable.unpaged());

        assertThat(savedUserUserEvent).hasSize(2).contains(userUserEvent, userUserEvent2);
    }*/

    @Test
    public void testFindPageableUserUserEventsPaymentsWithAuthUserId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Page<UserUserEvent> savedUserUserEvent = rUserUserEvent.findPageableUserUserEventsPaymentsWithAuthUserId(user.getUserId(), Pageable.unpaged());

        assertThat(savedUserUserEvent).hasSize(1).contains(userUserEvent);
    }

    /*@Test
    public void testFindPageableUserUserEventsPaymentsWithAuthUserIdByEvent() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Page<UserUserEvent> savedUserUserEvent = rUserUserEvent.findPageableUserUserEventsPaymentsWithAuthUserIdByEvent(user.getUserId(), Pageable.unpaged());

        assertThat(savedUserUserEvent).hasSize(1).contains(userUserEvent);
    }*/

    @Test
    public void testFindPageableNotFinishedUserUserEventsWithAuthUserId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Page<UserUserEvent> savedUserUserEvent = rUserUserEvent.findPageableNotFinishedUserUserEventsWithAuthUserId(user2.getUserId(), Pageable.unpaged());

        assertThat(savedUserUserEvent).hasSize(2).contains(userUserEvent, userUserEvent2);
    }

    /*@Test
    public void testFindPageableNotFinishedUserUserEventsWithAuthUserIdByEvent() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Page<UserUserEvent> savedUserUserEvent = rUserUserEvent.findPageableNotFinishedUserUserEventsWithAuthUserIdByEvent(user2.getUserId(), Pageable.unpaged());

        assertThat(savedUserUserEvent).hasSize(2).contains(userUserEvent, userUserEvent2);
    }*/

    @Test
    public void testFindPageableNotFinishedUserUserEventsDebtsWithAuthUserId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Page<UserUserEvent> savedUserUserEvent = rUserUserEvent.findPageableNotFinishedUserUserEventsDebtsWithAuthUserId(user2.getUserId(), Pageable.unpaged());

        assertThat(savedUserUserEvent).hasSize(2).contains(userUserEvent, userUserEvent2);
    }

    /*@Test
    public void testFindPageableNotFinishedUserUserEventsDebtsWithAuthUserIdByEvent() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Page<UserUserEvent> savedUserUserEvent = rUserUserEvent.findPageableNotFinishedUserUserEventsDebtsWithAuthUserIdByEvent(user2.getUserId(), Pageable.unpaged());

        assertThat(savedUserUserEvent).hasSize(2).contains(userUserEvent, userUserEvent2);
    }*/

    @Test
    public void testFindPageableNotFinishedUserUserEventsPaymentsWithAuthUserId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Page<UserUserEvent> savedUserUserEvent = rUserUserEvent.findPageableNotFinishedUserUserEventsPaymentsWithAuthUserId(user.getUserId(), Pageable.unpaged());

        assertThat(savedUserUserEvent).hasSize(1).contains(userUserEvent);
    }

    /*@Test
    public void testFindPageableNotFinishedUserUserEventsPaymentsWithAuthUserIdByEvent() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Page<UserUserEvent> savedUserUserEvent = rUserUserEvent.findPageableNotFinishedUserUserEventsPaymentsWithAuthUserIdByEvent(user.getUserId(), Pageable.unpaged());

        assertThat(savedUserUserEvent).hasSize(1).contains(userUserEvent);
    }*/

    /*@Test
    public void testFindPageableUserUserEventsWithAuthUserIdByDate() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");
        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);
        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);
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
        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);
        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);
        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);
        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);
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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);
        Page<UserUserEvent> savedUserUserEvent = rUserUserEvent.findPageableUserUserEventsWithAuthUserIdByDate(user2.getUserId(), Pageable.unpaged());
        assertThat(savedUserUserEvent).hasSize(2).contains(userUserEvent, userUserEvent2);
    }*/

    /*@Test
    public void testFindPageableUserUserEventsDebtsWithAuthUserIdByDate() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Page<UserUserEvent> savedUserUserEvent = rUserUserEvent.findPageableUserUserEventsDebtsWithAuthUserIdByDate(user2.getUserId(), Pageable.unpaged());

        assertThat(savedUserUserEvent).hasSize(2).contains(userUserEvent, userUserEvent2);
    }*/

    /*@Test
    public void testFindPageableUserUserEventsPaymentsWithAuthUserIdByDate() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Page<UserUserEvent> savedUserUserEvent = rUserUserEvent.findPageableUserUserEventsPaymentsWithAuthUserIdByDate(user.getUserId(), Pageable.unpaged());

        assertThat(savedUserUserEvent).hasSize(1).contains(userUserEvent);
    }*/

    /*@Test
    public void testFindPageableNotFinishedUserUserEventsWithAuthUserIdByDate() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Page<UserUserEvent> savedUserUserEvent = rUserUserEvent.findPageableNotFinishedUserUserEventsWithAuthUserIdByDate(user2.getUserId(), Pageable.unpaged());

        assertThat(savedUserUserEvent).hasSize(2).contains(userUserEvent, userUserEvent2);
    }*/

    /*@Test
    public void testFindPageableNotFinishedUserUserEventsDebtsWithAuthUserIdByDate() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Page<UserUserEvent> savedUserUserEvent = rUserUserEvent.findPageableNotFinishedUserUserEventsDebtsWithAuthUserIdByDate(user2.getUserId(), Pageable.unpaged());

        assertThat(savedUserUserEvent).hasSize(2).contains(userUserEvent, userUserEvent2);
    }*/

    /*@Test
    public void testFindPageableNotFinishedUserUserEventsPaymentsWithAuthUserIdByDate() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Page<UserUserEvent> savedUserUserEvent = rUserUserEvent.findPageableNotFinishedUserUserEventsPaymentsWithAuthUserIdByDate(user.getUserId(), Pageable.unpaged());

        assertThat(savedUserUserEvent).hasSize(1).contains(userUserEvent);
    }*/

    @Test
    public void testCountUserUserEvents() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Long savedUserUserEvent = rUserUserEvent.countUserUserEvents(event3.getEventId());

        assertThat(savedUserUserEvent).isEqualTo(1);
    }

    @Test
    public void testCountUserUserEventsWithAuthUserId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Long savedUserUserEvent = rUserUserEvent.countUserUserEventsWithAuthUserId(user.getUserId());

        assertThat(savedUserUserEvent).isEqualTo(1);
    }

    @Test
    public void testCountUserUserEventsDebtsWithAuthUserId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Long savedUserUserEvent = rUserUserEvent.countUserUserEventsDebtsWithAuthUserId(user2.getUserId());

        assertThat(savedUserUserEvent).isEqualTo(2);
    }

    @Test
    public void testCountUserUserEventsPaymentsWithAuthUserId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Long savedUserUserEvent = rUserUserEvent.countUserUserEventsPaymentsWithAuthUserId(user.getUserId());

        assertThat(savedUserUserEvent).isEqualTo(1);
    }

    @Test
    public void testCountNotFinishedUserUserEventsWithAuthUserId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Long savedUserUserEvent = rUserUserEvent.countNotFinishedUserUserEventsWithAuthUserId(user.getUserId());

        assertThat(savedUserUserEvent).isEqualTo(1);
    }

    @Test
    public void testCountNotFinishedUserUserEventsDebtsWithAuthUserId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Long savedUserUserEvent = rUserUserEvent.countNotFinishedUserUserEventsDebtsWithAuthUserId(user2.getUserId());

        assertThat(savedUserUserEvent).isEqualTo(2);
    }

    @Test
    public void testCountNotFinishedUserUserEventsPaymentsWithAuthUserId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Long savedUserUserEvent = rUserUserEvent.countNotFinishedUserUserEventsPaymentsWithAuthUserId(user.getUserId());

        assertThat(savedUserUserEvent).isEqualTo(1);
    }

    @Test
    public void testFindPageableSearchUserUserEvents() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Page<UserUserEvent> savedUserUserEvent = rUserUserEvent.findPageableSearchUserUserEvents(event3.getEventId(), "laura",Pageable.unpaged());

        assertThat(savedUserUserEvent).hasSize(1).contains(userUserEvent);
    }

    @Test
    public void testFindPageableSearchUserUserEventsWithActorId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Page<UserUserEvent> savedUserUserEvent = rUserUserEvent.findPageableSearchUserUserEventsWithActorId(user2.getUserId(), "laura",Pageable.unpaged());

        assertThat(savedUserUserEvent).hasSize(2).contains(userUserEvent, userUserEvent2);
    }

    @Test
    public void testCountSearchUserUserEvents() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Long savedUserUserEvent = rUserUserEvent.countSearchUserUserEvents(event3.getEventId(), "noa");

        assertThat(savedUserUserEvent).isEqualTo(1);
    }

    @Test
    public void testCountSearchUserUserEventsWithActorId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        Long savedUserUserEvent = rUserUserEvent.countSearchUserUserEventsWithActorId(user2.getUserId(), "laura");

        assertThat(savedUserUserEvent).isEqualTo(2);
    }

    @Test
    public void testFindPendingPay() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);


        List<User> savedUserUserEvent = rUserUserEvent.findPendingPay(userUserEvent.getUserUserEventCreation(), userUserEvent.getUserUserEventCreation());

        assertThat(savedUserUserEvent).hasSize(2).contains(user, user3);
    }

    @Test
    public void testFindPendingConfirm() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");
        User user3 = new User(0, "u3", "u3", "u3", "abc123.", "u3gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);

        Event event2 = new Event(0, "e2", "Descripcion del evento 2", new Timestamp(12), new Timestamp(12), "loc2", null, user2);

        Event event3 = new Event(0, "e3", "Descripcion del evento 3", new Timestamp(12), new Timestamp(12), "loc2", null, user2);
        event3.setEventOpen(false);


        Event event4 = new Event(0, "e4", "Descripcion del evento 4", new Timestamp(12), new Timestamp(12), "loc4", null, user2);
        event4.setEventOpen(false);

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

        UserEvent userEvent7 = new UserEvent(new UserEventId(4,2), 15.4, 5.0, true);
        userEvent7.setUser(user2);
        userEvent7.setEvent(event4);
        userEvent7.setUserEventActive(true);

        UserEvent userEvent8 = new UserEvent(new UserEventId(4,3), 15.4, 5.0, true);
        userEvent8.setUser(user3);
        userEvent8.setEvent(event4);
        userEvent8.setUserEventActive(true);

        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(1,2,3), 5, false, false);
        userUserEvent.setSender(user);
        userUserEvent.setReceiver(user2);
        userUserEvent.setEvent(event3);

        UserUserEvent userUserEvent2 = new UserUserEvent(new UserUserEventId(3,2,4), 5, false, false);
        userUserEvent2.setSender(user3);
        userUserEvent2.setReceiver(user2);
        userUserEvent2.setEvent(event4);

        UserUserEvent userUserEvent3 = new UserUserEvent(new UserUserEventId(3,2,3), 5, true, false);
        userUserEvent3.setSender(user3);
        userUserEvent3.setReceiver(user2);
        userUserEvent3.setEvent(event3);

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
        em.persist(userEvent7);
        em.persist(userEvent8);
        em.persist(userUserEvent);
        em.persist(userUserEvent2);
        em.persist(userUserEvent3);



        List<User> savedUserUserEvent = rUserUserEvent.findPendingConfirm(userUserEvent.getUserUserEventCreation(), userUserEvent.getUserUserEventCreation());

        assertThat(savedUserUserEvent).hasSize(1).contains(user2);
    }
}