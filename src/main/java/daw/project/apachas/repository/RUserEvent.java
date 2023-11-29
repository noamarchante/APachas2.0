package daw.project.apachas.repository;

import daw.project.apachas.entity.Event;
import daw.project.apachas.entity.User;
import daw.project.apachas.entity.UserEvent;
import daw.project.apachas.entity.id.UserEventId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("RUserEvent")
public interface RUserEvent extends CrudRepository<UserEvent, UserEventId>, PagingAndSortingRepository<UserEvent, UserEventId> {

    UserEvent findByUserEventId(UserEventId userEventId);

    List<UserEvent> findByUserEventId_EventId(long eventId);

    @Query("SELECT uE.event.eventName FROM userEvent uE WHERE uE.userEventId.userId = :authId AND uE.accept = FALSE AND uE.userEventActive = TRUE")
    String[] findNotifications(@Param("authId") long authId);

    @Query("SELECT DISTINCT uE FROM userEvent uE WHERE uE.userEventId.eventId = :eventId AND uE.userEventActive = TRUE AND uE.debt > 0 ORDER BY uE.debt ASC")
    List<UserEvent> findByEvent_EventIdAndDebtGreaterThanEqualOrderByDebtAsc(@Param("eventId") long eventId);

    @Query("SELECT DISTINCT uE FROM userEvent uE WHERE uE.userEventId.eventId = :eventId AND uE.userEventActive = TRUE AND uE.debt < 0 ORDER BY uE.debt DESC")
    List<UserEvent> findByEvent_EventIdAndDebtGreaterThanEqualOrderByDebtDesc(long eventId);

    @Override
    Page<UserEvent> findAll(Pageable pageable);

    @Query("SELECT DISTINCT e FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :authId AND uE.userEventActive = TRUE AND e.eventActive = TRUE AND e.eventOpen = TRUE AND e.eventName LIKE %:eventName% ORDER BY e.eventName ASC")
    Page<Event> findPageableSearchEvents(@Param("authId") long authId, @Param("eventName") String eventName, Pageable pageable);

    @Query("SELECT DISTINCT e FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :authId AND uE.userEventActive = TRUE AND e.eventActive = TRUE  AND e.eventOpen = FALSE AND e.eventName LIKE %:eventName% ORDER BY e.eventName ASC")
    Page<Event> findPageableSearchEventsWithFinished(@Param("authId") long authId, @Param("eventName") String eventName, Pageable pageable);

    @Query("SELECT DISTINCT e FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :authId AND uE.userEventActive = TRUE AND e.eventActive = TRUE AND e.eventOpen = TRUE ORDER BY e.eventName ASC")
    Page<Event> findPageableEvents(@Param("authId") long authId, Pageable pageable);

    @Query("SELECT DISTINCT e FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :authId AND uE.userEventActive = TRUE AND e.eventActive = TRUE AND e.eventOpen = FALSE ORDER BY e.eventName ASC")
    Page<Event> findPageableEventsWithFinished(@Param("authId") long authId, Pageable pageable);

    @Query("SELECT COUNT(DISTINCT e) FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :authId AND uE.userEventActive = TRUE AND e.eventActive = TRUE AND e.eventOpen = TRUE")
    Long countEvents(@Param("authId") long authId);

    @Query("SELECT SUM(uE.totalExpense) FROM userEvent uE WHERE uE.userEventId.eventId = :eventId AND uE.userEventActive = TRUE")
    Double sumTotalEventExpense(@Param("eventId") long eventId);

    @Query("SELECT COUNT(DISTINCT e) FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :authId AND uE.userEventActive = TRUE AND e.eventActive = TRUE AND e.eventOpen = FALSE")
    Long countEventsWithFinished(@Param("authId") long authId);

    @Query("SELECT COUNT(DISTINCT e) FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :authId AND uE.userEventActive = TRUE AND e.eventActive = TRUE AND e.eventOpen = TRUE AND e.eventName LIKE %:eventName%")
    Long countSearchEvents(@Param("authId")long authId, @Param("eventName") String eventName);

    @Query("SELECT COUNT(DISTINCT e) FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :authId AND uE.userEventActive = TRUE AND e.eventActive = TRUE AND e.eventOpen = FALSE AND e.eventName LIKE %:eventName%")
    Long countSearchEventsWithFinished(@Param("authId")long authId, @Param("eventName") String eventName);
    
    @Query("SELECT COUNT(DISTINCT e) FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :userId AND uE.accept = TRUE AND uE.userEventActive = TRUE AND e.eventActive = TRUE AND e.eventId IN (SELECT DISTINCT e FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :authId AND uE.accept = TRUE AND uE.userEventActive = TRUE AND e.eventActive = TRUE)")
    Long countMutualEvents(@Param("userId")long userId, @Param("authId") long authId);

    @Query("SELECT DISTINCT e FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :userId AND uE.accept = TRUE AND uE.userEventActive = TRUE AND e.eventActive = TRUE AND e.eventId IN (SELECT DISTINCT e FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :authId AND uE.accept = TRUE AND uE.userEventActive = TRUE AND e.eventActive = TRUE) ORDER BY e.eventName ASC")
    Page<Event> findPageableMutualEvents(@Param("userId") long userId, @Param("authId") long authId, Pageable pageable);

    @Query("SELECT COUNT(DISTINCT uE) FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.eventId = :eventId AND uE.userEventActive = TRUE AND uE.accept = TRUE")
    Long countPartakers(@Param("eventId") long eventId);

    @Query("SELECT DISTINCT uE.user FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.eventId = :eventId AND uE.userEventActive = TRUE AND uE.accept = TRUE ORDER BY uE.user.userLogin ASC")
    Page<User> findPageablePartakers(@Param("eventId") long eventId, Pageable pageable);

    @Query("SELECT DISTINCT uE.user FROM event e, userEvent uE where e.eventId = uE.userEventId.eventId AND uE.userEventId.eventId = :eventId AND uE.userEventActive = TRUE ORDER BY uE.user.userLogin ASC")
    List<User> findPartakers(@Param("eventId") long eventId);

    UserEvent findUserEventByUserEventId_EventIdAndUserEventId_UserId(@Param("eventId") long eventId, @Param("authId") long authId);

    @Query("SELECT DISTINCT uE FROM userEvent uE, user u WHERE uE.userEventId.eventId = :eventId AND uE.userEventId.userId = u.userId AND uE.userEventActive = TRUE AND uE.accept = TRUE ORDER BY u.userLogin ASC")
    Page<UserEvent> findPageableUserEvents(@Param("eventId") long eventId, Pageable pageable);

    @Query("SELECT DISTINCT uE FROM userEvent uE WHERE uE.userEventId.eventId = :eventId AND uE.userEventActive = TRUE AND uE.accept = TRUE ORDER BY uE.user.userLogin ASC")
    List<UserEvent> findUserEvents(@Param("eventId") long eventId);

    @Query("SELECT COUNT(DISTINCT uE) FROM userEvent uE WHERE uE.userEventId.eventId = :eventId AND uE.userEventActive = TRUE AND uE.accept = TRUE")
    Long countUserEvents(@Param("eventId") long eventId);

    @Query("SELECT DISTINCT uE FROM userEvent uE WHERE uE.userEventId.eventId = :eventId AND uE.userEventActive = TRUE AND uE.accept = TRUE AND uE.user.userName LIKE %:userName% ORDER BY uE.user.userName ASC")
    Page<UserEvent> findPageableSearchUserEvents(@Param("eventId") long eventId, @Param("userName") String userName, Pageable pageable);

    @Query("SELECT COUNT(DISTINCT uE) FROM userEvent uE WHERE uE.userEventId.eventId = :eventId AND uE.userEventActive = TRUE AND uE.accept = TRUE AND uE.user.userName LIKE %:userName%")
    Long countSearchUserEvents(@Param("eventId")long eventId, @Param("userName") String userName);

    @Query("SELECT DISTINCT e FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :authId AND uE.userEventActive = TRUE AND e.eventActive = TRUE AND e.eventOpen = TRUE ORDER BY e.eventName ASC")
    List<Event> findOpenEvents(@Param("authId") long authId);

    @Query("SELECT DISTINCT e FROM event e, userEvent uE WHERE e.eventId = uE.userEventId.eventId AND uE.userEventId.userId = :authId AND uE.userEventActive = TRUE AND e.eventActive = TRUE AND e.eventOpen = FALSE ORDER BY e.eventName ASC")
    List<Event> findClosedEvents(@Param("authId") long authId);

    @Query("SELECT DISTINCT u FROM user u, event e, userEvent uE WHERE u.userId = uE.userEventId.userId AND e.eventId = uE.userEventId.eventId AND u.userActive = TRUE AND e.eventActive = TRUE AND uE.userEventActive = TRUE AND e.eventOpen = TRUE AND u.userNotify = TRUE AND uE.userEventCreation >= :previousDate AND uE.userEventCreation <= :currentDate")
    List<User> findAddedNewEvent(@Param("currentDate") Date currentDate, @Param("previousDate") Date previousDate);

    @Query("SELECT DISTINCT u FROM user u, event e, userEvent uE WHERE u.userId = uE.userEventId.userId AND e.eventId = uE.userEventId.eventId AND u.userActive = TRUE AND e.eventActive = TRUE AND uE.userEventActive = TRUE AND e.eventOpen = TRUE AND u.userNotify = TRUE AND e.eventStart = :date")
    List<User> findNearEvent(@Param("date") Date date);

    @Query("SELECT DISTINCT u FROM user u, event e, userEvent uE WHERE u.userId = uE.userEventId.userId AND e.eventId = uE.userEventId.eventId AND u.userActive = TRUE AND e.eventActive = TRUE AND uE.userEventActive = TRUE AND e.eventOpen = TRUE AND u.userNotify = TRUE AND e.eventStart = :date")
    List<User> findEventStart(@Param("date") Date date);

    @Query("SELECT DISTINCT u FROM user u, event e, userUserEvent uUe WHERE (u.userId = uUe.userUserEventId.receiverId OR u.userId = uUe.userUserEventId.senderId) AND e.eventId = uUe.userUserEventId.eventId AND u.userActive = TRUE AND e.eventActive = TRUE AND uUe.userUserEventActive = TRUE AND e.eventOpen = FALSE AND u.userNotify = TRUE AND uUe.userUserEventCreation >= :previousDate AND uUe.userUserEventCreation <= :currentDate")
    List<User> findEventFinished(@Param("currentDate") Date currentDate, @Param("previousDate") Date previousDate);
}