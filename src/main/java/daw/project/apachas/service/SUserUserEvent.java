package daw.project.apachas.service;

import daw.project.apachas.converter.ConUserEvent;
import daw.project.apachas.converter.ConUserUserEvent;
import daw.project.apachas.entity.Event;
import daw.project.apachas.entity.User;
import daw.project.apachas.entity.UserUserEvent;
import daw.project.apachas.entity.id.UserUserEventId;
import daw.project.apachas.model.MUserEvent;
import daw.project.apachas.model.MUserUserEvent;
import daw.project.apachas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service("SUserUserEvent")
public class SUserUserEvent {

    @Autowired
    @Qualifier("RUserUserEvent")
    private RUserUserEvent rUserUserEvent;

    @Autowired
    @Qualifier("RUserUser")
    private RUserUser rUserUser;

    @Autowired
    @Qualifier("RUser")
    private RUser rUser;

    @Autowired
    @Qualifier("REvent")
    private REvent rEvent;

    @Autowired
    @Qualifier("RUserEvent")
    private RUserEvent rUserEvent;

    @Autowired
    @Qualifier("ConUserUserEvent")
    private ConUserUserEvent conUserUserEvent;

    @Autowired
    @Qualifier("ConUserEvent")
    private ConUserEvent conUserEvent;

    public synchronized boolean deleteUserUserEvent(long eventId) {
        List<UserUserEvent> userUserEventList = rUserUserEvent.findByEvent_EventId(eventId);
        if (userUserEventList.size() > 0) {
            rUserUserEvent.deleteAll(userUserEventList);
            return true;
        } else {
            return false;
        }
    }

    public synchronized void insertUserUserEvent(long senderId, long receiverId, long eventId, double debt, Event existingEvent, User existingSender, User existingReceiver) {
        UserUserEvent userUserEvent = new UserUserEvent(new UserUserEventId(senderId, receiverId, eventId), debt, false, false);
        userUserEvent.setEvent(existingEvent);
        userUserEvent.setSender(existingSender);
        userUserEvent.setReceiver(existingReceiver);
        rUserUserEvent.save(userUserEvent);

    }
    @Transactional
    public synchronized boolean insertUserUserEvent(long eventId){
        Event existingEvent = rEvent.findByEventId(eventId);
        if (existingEvent != null) {
            List<MUserEvent> mUserEventList = setUserUserEventDebt(existingEvent.getEventId()); //lista de usuarios del evento con sus deudas
            List<MUserEvent> receiverList = getUserUserEventReceiver(mUserEventList); // lista de usuarios que reciben el pago
            List<MUserEvent> senderList = getUserUserEventSender(mUserEventList); // lista de usuarios que emiten el pago

            AtomicInteger receiverSolved = new AtomicInteger(receiverList.size());
            AtomicInteger senderSolved = new AtomicInteger(senderList.size());


            while (receiverSolved.get() > 0 && senderSolved.get() > 0) {

                receiverList.forEach((receiver) -> senderList.forEach((sender) -> {
                    User existingSender = rUser.findByUserId(sender.getUserId());
                    User existingReceiver = rUser.findByUserId(receiver.getUserId());

                    if (existingReceiver != null && existingSender != null){
                        if (sender.getDebt() != null && receiver.getDebt()!= null){
                            if (receiver.getDebt() + sender.getDebt() == 0){

                                insertUserUserEvent(sender.getUserId(), receiver.getUserId(), eventId, receiver.getDebt(), existingEvent, existingSender, existingReceiver);

                                receiver.setDebt(null);
                                sender.setDebt(null);

                                senderSolved.addAndGet(-1);
                                receiverSolved.addAndGet(-1);

                            }else if (receiver.getDebt() + sender.getDebt() < 0) { //sender debe mas dinero
                                insertUserUserEvent(sender.getUserId(), receiver.getUserId(), eventId,  receiver.getDebt(), existingEvent, existingSender, existingReceiver);

                                sender.setDebt(sender.getDebt() + receiver.getDebt());
                                receiver.setDebt(null);

                                receiverSolved.addAndGet(-1);

                            }else if (receiver.getDebt() + sender.getDebt() > 0) { //deben mas a receiver

                                insertUserUserEvent(sender.getUserId(), receiver.getUserId(), eventId,sender.getDebt()*-1, existingEvent, existingSender, existingReceiver);

                                receiver.setDebt(receiver.getDebt() + sender.getDebt());
                                sender.setDebt(null);

                                senderSolved.addAndGet(-1);
                            }
                        }
                    }
                }));
            }
            return true;
        } else {
            return false;
        }
    }

    //selecciona los que emiten el pago
    private List<MUserEvent> getUserUserEventSender(List<MUserEvent> mUserEventList){
        List<MUserEvent> senderList = new ArrayList<>();
        mUserEventList.forEach((userEvent -> {
            if (userEvent.getDebt() < 0){
                senderList.add(userEvent);
            }
        }));
        return senderList;
    }


    //selecciona los que reciben el pago
    private List<MUserEvent> getUserUserEventReceiver(List<MUserEvent> mUserEventList){
        List<MUserEvent> receiverList = new ArrayList<>();
        mUserEventList.forEach((userEvent -> {
            if (userEvent.getDebt() > 0){
                receiverList.add(userEvent);
            }
        }));
        return receiverList;
    }

    //calcula la deuda de los usuarios
    private List<MUserEvent> setUserUserEventDebt (long eventId) {
        List<MUserEvent> mUserEventList = conUserEvent.conUserEventList(rUserEvent.findByUserEventId_EventId(eventId));
        mUserEventList.forEach((userEvent)->{
            userEvent.setDebt(userEvent.getTotalExpense() - userEvent.getDebt());
        });
        return mUserEventList;
    }

    //ordena los usuarios segun su deuda
    /*private LinkedHashMap<Long,Double> sortUserUserEventCost (HashMap<Long, Double> userUserEventCostHash){
        return userUserEventCostHash
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(a,b) -> a, LinkedHashMap::new));
    }*/

    public synchronized List<MUserUserEvent> selectPageableUserUserEventsByEvent(Long eventId, Pageable pageable) {
        List<UserUserEvent> userUserEventList = rUserUserEvent.findPageableUserUserEvents(eventId, pageable).getContent();
        return conUserUserEvent.conUserUserEventList(userUserEventList);
    }

    public synchronized List<MUserUserEvent> selectPageableUserUserEventsByAuthUser(Long authUserId, Pageable pageable) {
        List<UserUserEvent> userUserEventList = rUserUserEvent.findPageableUserUserEventsWithAuthUserId(authUserId, pageable).getContent();
        return conUserUserEvent.conUserUserEventList(userUserEventList);
    }
    public synchronized List<MUserUserEvent> selectPageableUserUserEventsByAuthUserByEvent(Long authUserId, Pageable pageable) {
        List<UserUserEvent> userUserEventList = rUserUserEvent.findPageableUserUserEventsWithAuthUserIdByEvent(authUserId, pageable).getContent();
        return conUserUserEvent.conUserUserEventList(userUserEventList);
    }

    public synchronized List<MUserUserEvent> selectPageableUserUserEventsDebtsByAuthUser(Long authUserId, Pageable pageable) {
        List<UserUserEvent> userUserEventList = rUserUserEvent.findPageableUserUserEventsDebtsWithAuthUserId(authUserId, pageable).getContent();
        return conUserUserEvent.conUserUserEventList(userUserEventList);
    }

    public synchronized List<MUserUserEvent> selectPageableUserUserEventsDebtsByAuthUserByEvent(Long authUserId, Pageable pageable) {
        List<UserUserEvent> userUserEventList = rUserUserEvent.findPageableUserUserEventsDebtsWithAuthUserIdByEvent(authUserId, pageable).getContent();
        return conUserUserEvent.conUserUserEventList(userUserEventList);
    }

    public synchronized List<MUserUserEvent> selectPageableUserUserEventsPaymentsByAuthUser(Long authUserId, Pageable pageable) {
        List<UserUserEvent> userUserEventList = rUserUserEvent.findPageableUserUserEventsPaymentsWithAuthUserId(authUserId, pageable).getContent();
        return conUserUserEvent.conUserUserEventList(userUserEventList);
    }

    public synchronized List<MUserUserEvent> selectPageableUserUserEventsPaymentsByAuthUserByEvent(Long authUserId, Pageable pageable) {
        List<UserUserEvent> userUserEventList = rUserUserEvent.findPageableUserUserEventsPaymentsWithAuthUserIdByEvent(authUserId, pageable).getContent();
        return conUserUserEvent.conUserUserEventList(userUserEventList);
    }

    public synchronized List<MUserUserEvent> selectPageableNotFinishedUserUserEventsByAuthUser(Long authUserId, Pageable pageable) {
        List<UserUserEvent> userUserEventList = rUserUserEvent.findPageableNotFinishedUserUserEventsWithAuthUserId(authUserId, pageable).getContent();
        return conUserUserEvent.conUserUserEventList(userUserEventList);
    }

    public synchronized List<MUserUserEvent> selectPageableNotFinishedUserUserEventsByAuthUserByEvent(Long authUserId, Pageable pageable) {
        List<UserUserEvent> userUserEventList = rUserUserEvent.findPageableNotFinishedUserUserEventsWithAuthUserIdByEvent(authUserId, pageable).getContent();
        return conUserUserEvent.conUserUserEventList(userUserEventList);
    }

    public synchronized List<MUserUserEvent> selectPageableNotFinishedUserUserEventsDebtsByAuthUser(Long authUserId, Pageable pageable) {
        List<UserUserEvent> userUserEventList = rUserUserEvent.findPageableNotFinishedUserUserEventsDebtsWithAuthUserId(authUserId, pageable).getContent();
        return conUserUserEvent.conUserUserEventList(userUserEventList);
    }

    public synchronized List<MUserUserEvent> selectPageableNotFinishedUserUserEventsDebtsByAuthUserByEvent(Long authUserId, Pageable pageable) {
        List<UserUserEvent> userUserEventList = rUserUserEvent.findPageableNotFinishedUserUserEventsDebtsWithAuthUserIdByEvent(authUserId, pageable).getContent();
        return conUserUserEvent.conUserUserEventList(userUserEventList);
    }

    public synchronized List<MUserUserEvent> selectPageableNotFinishedUserUserEventsPaymentsByAuthUser(Long authUserId, Pageable pageable) {
        List<UserUserEvent> userUserEventList = rUserUserEvent.findPageableNotFinishedUserUserEventsPaymentsWithAuthUserId(authUserId, pageable).getContent();
        return conUserUserEvent.conUserUserEventList(userUserEventList);
    }

    public synchronized List<MUserUserEvent> selectPageableNotFinishedUserUserEventsPaymentsByAuthUserByEvent(Long authUserId, Pageable pageable) {
        List<UserUserEvent> userUserEventList = rUserUserEvent.findPageableNotFinishedUserUserEventsPaymentsWithAuthUserIdByEvent(authUserId, pageable).getContent();
        return conUserUserEvent.conUserUserEventList(userUserEventList);
    }

    public synchronized List<MUserUserEvent> selectPageableUserUserEventsByAuthUserByDate(Long authUserId, Pageable pageable) {
        List<UserUserEvent> userUserEventList = rUserUserEvent.findPageableUserUserEventsWithAuthUserIdByDate(authUserId, pageable).getContent();
        return conUserUserEvent.conUserUserEventList(userUserEventList);
    }

    public synchronized List<MUserUserEvent> selectPageableUserUserEventsDebtsByAuthUserByDate(Long authUserId, Pageable pageable) {
        List<UserUserEvent> userUserEventList = rUserUserEvent.findPageableUserUserEventsDebtsWithAuthUserIdByDate(authUserId, pageable).getContent();
        return conUserUserEvent.conUserUserEventList(userUserEventList);
    }

    public synchronized List<MUserUserEvent> selectPageableUserUserEventsPaymentsByAuthUserByDate(Long authUserId, Pageable pageable) {
        List<UserUserEvent> userUserEventList = rUserUserEvent.findPageableUserUserEventsPaymentsWithAuthUserIdByDate(authUserId, pageable).getContent();
        return conUserUserEvent.conUserUserEventList(userUserEventList);
    }

    public synchronized List<MUserUserEvent> selectPageableNotFinishedUserUserEventsByAuthUserByDate(Long authUserId, Pageable pageable) {
        List<UserUserEvent> userUserEventList = rUserUserEvent.findPageableNotFinishedUserUserEventsWithAuthUserIdByDate(authUserId, pageable).getContent();
        return conUserUserEvent.conUserUserEventList(userUserEventList);
    }

    public synchronized List<MUserUserEvent> selectPageableNotFinishedUserUserEventsDebtsByAuthUserByDate(Long authUserId, Pageable pageable) {
        List<UserUserEvent> userUserEventList = rUserUserEvent.findPageableNotFinishedUserUserEventsDebtsWithAuthUserIdByDate(authUserId, pageable).getContent();
        return conUserUserEvent.conUserUserEventList(userUserEventList);
    }

    public synchronized List<MUserUserEvent> selectPageableNotFinishedUserUserEventsPaymentsByAuthUserByDate(Long authUserId, Pageable pageable) {
        List<UserUserEvent> userUserEventList = rUserUserEvent.findPageableNotFinishedUserUserEventsPaymentsWithAuthUserIdByDate(authUserId, pageable).getContent();
        return conUserUserEvent.conUserUserEventList(userUserEventList);
    }

    public synchronized Long countUserUserEventsByEvent(long eventId){
        return rUserUserEvent.countUserUserEvents(eventId);
    }

    public synchronized Long countUserUserEventsByAuthUser(long authUserId){
        return rUserUserEvent.countUserUserEventsWithAuthUserId(authUserId);
    }

    public synchronized Long countUserUserEventsDebtsByAuthUser(long authUserId){
        return rUserUserEvent.countUserUserEventsDebtsWithAuthUserId(authUserId);
    }

    public synchronized Long countUserUserEventsPaymentsByAuthUser(long authUserId){
        return rUserUserEvent.countUserUserEventsPaymentsWithAuthUserId(authUserId);
    }

    public synchronized Long countNotFinishedUserUserEventsByAuthUser(long authUserId){
        return rUserUserEvent.countNotFinishedUserUserEventsWithAuthUserId(authUserId);
    }

    public synchronized Long countNotFinishedUserUserEventsDebtsByAuthUser(long authUserId){
        return rUserUserEvent.countNotFinishedUserUserEventsDebtsWithAuthUserId(authUserId);
    }

    public synchronized Long countNotFinishedUserUserEventsPaymentsByAuthUser(long authUserId){
        return rUserUserEvent.countNotFinishedUserUserEventsPaymentsWithAuthUserId(authUserId);
    }

    public synchronized List<MUserUserEvent> selectPageableSearchUserUserEventsByEvent(String transactionActorName, long eventId, Pageable pageable) {
        return conUserUserEvent.conUserUserEventList(rUserUserEvent.findPageableSearchUserUserEvents(eventId, transactionActorName, pageable).getContent());
    }

    public synchronized List<MUserUserEvent> selectPageableSearchUserUserEventsByAuthUser(String transactionSearchValue, long authUserId, Pageable pageable) {
        return conUserUserEvent.conUserUserEventList(rUserUserEvent.findPageableSearchUserUserEventsWithActorId(authUserId, transactionSearchValue, pageable).getContent());
    }

    public synchronized Long countSearchUserUserEventsByEvent(String transactionActorName, long eventId){
        return rUserUserEvent.countSearchUserUserEvents(eventId, transactionActorName);
    }

   public synchronized Long countSearchUserUserEventsByAuthUser(String transactionSearchValue, long authUserId){
        return rUserUserEvent.countSearchUserUserEventsWithActorId(authUserId, transactionSearchValue);
    }

    public synchronized boolean updatePaid(long eventId, long senderId, long receiverId, boolean paid) {
        Event existingEvent = rEvent.findByEventId(eventId);
        User existingSender = rUser.findByUserId(senderId);
        User existingReceiver = rUser.findByUserId(senderId);
        UserUserEvent existingUserUserEvent = rUserUserEvent.findByUserUserEventId(new UserUserEventId(senderId, receiverId, eventId));

        if (existingEvent != null && existingSender != null && existingReceiver != null && existingUserUserEvent != null) {
            existingUserUserEvent.setPaid(paid);
            rUserUserEvent.save(existingUserUserEvent);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean updateConfirmed(long eventId, long senderId, long receiverId, boolean confirmed) {
        Event existingEvent = rEvent.findByEventId(eventId);
        User existingSender = rUser.findByUserId(senderId);
        User existingReceiver = rUser.findByUserId(senderId);
        UserUserEvent existingUserUserEvent = rUserUserEvent.findByUserUserEventId(new UserUserEventId(senderId, receiverId, eventId));

        if (existingEvent != null && existingSender != null && existingReceiver != null && existingUserUserEvent != null) {
            existingUserUserEvent.setConfirmed(confirmed);
            rUserUserEvent.save(existingUserUserEvent);
            return true;
        } else {
            return false;
        }
    }
}
