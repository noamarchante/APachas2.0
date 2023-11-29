package daw.project.apachas.converter;

import daw.project.apachas.entity.UserUserEvent;
import daw.project.apachas.entity.id.UserUserEventId;
import daw.project.apachas.model.MUserUserEvent;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component("ConUserUserEvent")
public class ConUserUserEvent {

    public List<MUserUserEvent> conUserUserEventList(List<UserUserEvent> userUserEventList) {
        List<MUserUserEvent> mUserUserEventList = new ArrayList<>();
        for (UserUserEvent userUserEvent : userUserEventList) {
            mUserUserEventList.add(new MUserUserEvent(userUserEvent));
        }
        return mUserUserEventList;
    }

    public MUserUserEvent conUserUserEvent(UserUserEvent userUserEvent) {
        return new MUserUserEvent(userUserEvent);
    }

    public UserUserEvent conMUserUserEvent(MUserUserEvent mUserUserEvent) {
        return new UserUserEvent(new UserUserEventId(mUserUserEvent.getSenderId(), mUserUserEvent.getReceiverId(), mUserUserEvent.getEventId()), mUserUserEvent.getCost(), mUserUserEvent.isPaid(), mUserUserEvent.isConfirmed());
    }
}
