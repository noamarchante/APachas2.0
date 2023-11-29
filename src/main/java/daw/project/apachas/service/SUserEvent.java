package daw.project.apachas.service;

import daw.project.apachas.converter.ConEvent;
import daw.project.apachas.converter.ConUser;
import daw.project.apachas.converter.ConUserEvent;
import daw.project.apachas.entity.Event;
import daw.project.apachas.entity.User;
import daw.project.apachas.entity.UserEvent;
import daw.project.apachas.model.MEvent;
import daw.project.apachas.model.MUser;
import daw.project.apachas.model.MUserEvent;
import daw.project.apachas.repository.RUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import daw.project.apachas.entity.id.UserEventId;
import daw.project.apachas.repository.REvent;
import daw.project.apachas.repository.RUserEvent;
import java.sql.Timestamp;
import java.util.List;

@Service("SUserEvent")
public class SUserEvent {

    @Autowired
    @Qualifier("RUserEvent")
    private RUserEvent rUserEvent;

    @Autowired
    @Qualifier("REvent")
    private REvent rEvent;

    @Autowired
    @Qualifier("RUser")
    private RUser rUser;

    @Autowired
    @Qualifier("ConUserEvent")
    private ConUserEvent conUserEvent;

    @Autowired
    @Qualifier("ConEvent")
    private ConEvent conEvent;

    @Autowired
    @Qualifier("ConUser")
    private ConUser conUser;

    public synchronized boolean insertUserEvent(MUserEvent mUserEvent) {
        UserEvent userEvent = conUserEvent.conMUserEvent(mUserEvent);
        UserEvent existingUserEvent = rUserEvent.findByUserEventId(userEvent.getUserEventId());
        Event existingEvent = rEvent.findByEventId(userEvent.getUserEventId().getEventId());
        User existingUser = rUser.findByUserId(userEvent.getUserEventId().getUserId());
        if (existingUserEvent != null || existingEvent == null || existingUser == null) {
            return false;
        } else {
            userEvent.setUserEventActive(true);
            userEvent.setUserEventRemoval(null);
            userEvent.setUserEventCreation(new Timestamp(System.currentTimeMillis()));
            userEvent.setEvent(existingEvent);
            userEvent.setUser(existingUser);
            rUserEvent.save(userEvent);
            return true;
        }
    }

    public synchronized boolean updateUserEvent(Long eventId, List<Long> userIdList) {
        Event existingEvent = rEvent.findByEventId(eventId);
        if (existingEvent != null){
            List<UserEvent> existingUserEventList = rUserEvent.findByUserEventId_EventId(eventId);
            existingUserEventList.forEach(userEvent -> {
                if (!userIdList.contains(userEvent.getUserEventId().getUserId())){
                    deleteUserEvent(userEvent.getUserEventId().getEventId(), userEvent.getUserEventId().getUserId());
                }
                userIdList.remove(userEvent.getUserEventId().getUserId());
            });

            userIdList.forEach(userId -> {
                existingUserEventList.forEach(userEvent ->{
                    if (userEvent.getUser().getUserId() == userId && !userEvent.isUserEventActive()){
                        userEvent.setUserEventActive(true);
                        userEvent.setUserEventRemoval(null);
                        userEvent.setAccept(false);
                        rUserEvent.save(userEvent);
                    }
                });
                insertUserEvent(new MUserEvent(new UserEvent(new UserEventId(eventId, userId), 0.0, 0.0, false)));
            });
            return true;
        }else{
            return false;
        }
    }

    public synchronized boolean updateStatus(Long eventId, long authId) {
        Event existingEvent = rEvent.findByEventId(eventId);
        UserEvent existingUserEvent = rUserEvent.findByUserEventId(new UserEventId(eventId, authId));
        User existingUser = rUser.findByUserId(authId);

        if (existingEvent != null && existingUser != null && existingUserEvent != null){
            existingUserEvent.setAccept(!existingUserEvent.isAccept());
            rUserEvent.save(existingUserEvent);
            return true;
        }else{
            return false;
        }
    }

    public synchronized boolean deleteUserEvent(long eventId, long userId) {
        UserEvent existingUserEvent = rUserEvent.findByUserEventId(new UserEventId(eventId, userId));
        Event existingEvent = rEvent.findByEventId(eventId);
        User existingUser = rUser.findByUserId(userId);
        if (existingUserEvent != null || existingEvent != null || existingUser != null) {
            existingUserEvent.setUserEventActive(false);
            existingUserEvent.setUserEventRemoval(new Timestamp(System.currentTimeMillis()));
            rUserEvent.save(existingUserEvent);
            return true;
        } else {
            return false;
        }
    }

    public synchronized Long countEvents(long authId){
        return rUserEvent.countEvents(authId);
    }

    public synchronized Long countSearchEvents(String eventName, long authId){
        return rUserEvent.countSearchEvents(authId, eventName);
    }
    public synchronized Long countEventsWithFinished(long authId){
        return rUserEvent.countEventsWithFinished(authId);
    }

    public synchronized Long countSearchEventsWithFinished(String eventName, long authId){
        return rUserEvent.countSearchEventsWithFinished(authId, eventName);
    }

    public synchronized Long countMutualEvents( long userId, long authId){
        return rUserEvent.countMutualEvents(userId, authId);
    }

    public synchronized Long countPartakers(long eventId){
        return rUserEvent.countPartakers(eventId);
    }

    public synchronized String[] selectNotifications(Long authId) {
        return rUserEvent.findNotifications(authId);
    }

    public synchronized List<MEvent> selectPageableEvents(Long userId, Pageable pageable) {
        List<Event> eventList = rUserEvent.findPageableEvents(userId, pageable).getContent();
        return conEvent.conEventList(eventList);
    }

    public synchronized List<MEvent> selectPageableEventsWithFinished(Long userId, Pageable pageable) {
        List<Event> eventList = rUserEvent.findPageableEventsWithFinished(userId, pageable).getContent();
        return conEvent.conEventList(eventList);
    }

    public synchronized List<MEvent> selectPageableSearchEvents(String eventName, long authId, Pageable pageable) {
        return conEvent.conEventList(rUserEvent.findPageableSearchEvents(authId, eventName, pageable).getContent());
    }

    public synchronized List<MEvent> selectPageableSearchEventsWithFinished(String eventName, long authId, Pageable pageable) {
        return conEvent.conEventList(rUserEvent.findPageableSearchEventsWithFinished(authId, eventName, pageable).getContent());
    }

    public synchronized List<MEvent> selectPageableMutualEvents(long userId, long authId, Pageable pageable) {
        return conEvent.conEventList(rUserEvent.findPageableMutualEvents(userId, authId, pageable).getContent());
    }

    public synchronized List<MUser> selectPageablePartakers(long eventId, Pageable pageable) {
        return conUser.conUserList(rUserEvent.findPageablePartakers(eventId, pageable).getContent());
    }

    public synchronized List<MUser> selectPartakers(long eventId) {
        List<User> userList = rUserEvent.findPartakers(eventId);
        return conUser.conUserList(userList);
    }

    public synchronized MUserEvent selectUserEvent(long eventId, long authId) {
        return conUserEvent.conUserEvent(rUserEvent.findUserEventByUserEventId_EventIdAndUserEventId_UserId(eventId, authId));
    }

    public synchronized List<MUserEvent> selectPageableUserEvents(Long eventId, Pageable pageable) {
        List<UserEvent> userEventList = rUserEvent.findPageableUserEvents(eventId, pageable).getContent();
        return conUserEvent.conUserEventList(userEventList);
    }

    public synchronized List<MUserEvent> selectUserEvents(Long eventId) {
        List<UserEvent> userEventList = rUserEvent.findUserEvents(eventId);
        return conUserEvent.conUserEventList(userEventList);
    }

    public synchronized Long countUserEvents(long eventId){
        return rUserEvent.countUserEvents(eventId);
    }
    public synchronized double sumTotalEventExpense(long eventId){
        if (rUserEvent.countUserEvents(eventId) >0){
            return rUserEvent.sumTotalEventExpense(eventId);
        }else{
            return 0L;
        }
    }
    public synchronized List<MUserEvent> selectPageableSearchUserEvents(String userName, long eventId, Pageable pageable) {
        return conUserEvent.conUserEventList(rUserEvent.findPageableSearchUserEvents(eventId, userName, pageable).getContent());
    }

    public synchronized Long countSearchUserEvents(String userName, long eventId){
        return rUserEvent.countSearchUserEvents(eventId, userName);
    }

    public synchronized boolean updateTotalExpense(long eventId, long userId, double totalExpense) {
        UserEvent existingUserEvent = rUserEvent.findByUserEventId(new UserEventId(eventId, userId));
        Event existingEvent = rEvent.findByEventId(eventId);
        User existingUser = rUser.findByUserId(userId);
        if (existingUserEvent == null || existingEvent == null || existingUser == null) {
            return false;
        } else {
            existingUserEvent.setTotalExpense(totalExpense);
            rUserEvent.save(existingUserEvent);
            return true;
        }
    }

    public synchronized boolean updateDebt(long eventId, long userId, double userDebt) {
        UserEvent existingUserEvent = rUserEvent.findByUserEventId(new UserEventId(eventId, userId));
        Event existingEvent = rEvent.findByEventId(eventId);
        User existingUser = rUser.findByUserId(userId);
        if (existingUserEvent == null || existingEvent == null || existingUser == null) {
            return false;
        } else {
            existingUserEvent.setDebt(userDebt);
            rUserEvent.save(existingUserEvent);
            return true;
        }
    }

    public synchronized List<MEvent> selectOpenEvents(Long userId) {
        List<Event> eventList = rUserEvent.findOpenEvents(userId);
        return conEvent.conEventList(eventList);
    }

    public synchronized List<MEvent> selectClosedEvents(Long userId) {
        List<Event> eventList = rUserEvent.findClosedEvents(userId);
        return conEvent.conEventList(eventList);
    }
}
