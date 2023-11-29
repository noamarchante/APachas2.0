package daw.project.apachas.service;

import daw.project.apachas.converter.ConEvent;
import daw.project.apachas.entity.Event;
import daw.project.apachas.model.MEvent;
import daw.project.apachas.repository.REvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;

@Service("SEvent")
public class SEvent {

    @Autowired
    @Qualifier("REvent")
    private REvent rEvent;

    @Autowired
    @Qualifier("SUserUserEvent")
    private SUserUserEvent sUserUserEvent;

    @Autowired
    @Qualifier("ConEvent")
    private ConEvent conEvent;

    public synchronized Long insertEvent(MEvent mEvent) {
        Event event = conEvent.conMEvent(mEvent);
        Event existingEvent = rEvent.findByEventId(event.getEventId());
        if (existingEvent != null) {
            return 0L;
        } else {
            event.setEventOpen(true);
            event.setEventActive(true);
            event.setEventCreation(new Timestamp(System.currentTimeMillis()));
            event.setEventRemoval(null);
            return rEvent.save(event).getEventId();
        }
    }

    public synchronized boolean updateEvent(MEvent mEvent) {
        Event event = conEvent.conMEvent(mEvent);
        Event existingEvent = rEvent.findByEventId(event.getEventId());
        if (existingEvent != null) {
            rEvent.save(event);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean deleteEvent(long eventId) {
        Event existingEvent = rEvent.findByEventId(eventId);
        if (existingEvent != null) {
            existingEvent.setEventActive(false);
            existingEvent.setEventRemoval(new Timestamp(System.currentTimeMillis()));
            rEvent.save(existingEvent);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean updateOpen(long eventId, boolean close) {
        Event existingEvent = rEvent.findByEventId(eventId);
        if (existingEvent != null) {
            sUserUserEvent.deleteUserUserEvent(eventId);
            existingEvent.setEventOpen(close);
            rEvent.save(existingEvent);
            return true;
        } else {
            return false;
        }
    }

    public synchronized MEvent selectEvent(Long eventId) {
        Event event = rEvent.findByEventId(eventId);
        return conEvent.conEvent(event);
    }
}
