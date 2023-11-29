package daw.project.apachas.converter;

import daw.project.apachas.entity.Event;
import daw.project.apachas.entity.User;
import org.springframework.stereotype.Component;
import daw.project.apachas.model.MEvent;
import org.springframework.web.bind.annotation.Mapping;

import java.util.ArrayList;
import java.util.List;

@Component("ConEvent")
public class ConEvent {
    public List<MEvent> conEventList(List<Event> eventList) {
        List<MEvent> mEventList = new ArrayList<>();
        for (Event event : eventList) {
            mEventList.add(new MEvent(event));
        }
        return mEventList;
    }

    public MEvent conEvent(Event event) {
        return new MEvent(event);
    }

    public Event conMEvent(MEvent mEvent) {
        return new Event(mEvent.getEventId(), mEvent.getEventName(), mEvent.getEventDescription(), mEvent.getEventStart(), mEvent.getEventEnd(), mEvent.getEventLocation(), mEvent.getEventPhoto(), new User(mEvent.getEventOwner()));
    }

}
