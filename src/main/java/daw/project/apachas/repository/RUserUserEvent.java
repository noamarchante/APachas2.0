package daw.project.apachas.repository;

import daw.project.apachas.entity.id.UserUserEventId;
import daw.project.apachas.entity.User;
import daw.project.apachas.entity.UserUserEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("RUserUserEvent")
public interface RUserUserEvent extends CrudRepository<UserUserEvent, UserUserEventId>, PagingAndSortingRepository<UserUserEvent, UserUserEventId> {

    UserUserEvent findByUserUserEventId(UserUserEventId userUserEventId);

    List<UserUserEvent> findByEvent_EventId(long eventId);

    UserUserEvent findByUserUserEventIdAndUserUserEventActiveTrue(UserUserEventId userUserEventId);

    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE uUe.userUserEventId.eventId = :eventId AND uUe.userUserEventActive = TRUE ORDER BY uUe.cost ASC")
    Page<UserUserEvent> findPageableUserUserEvents(@Param("eventId") long eventId, Pageable pageable);

    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE (uUe.userUserEventId.senderId = :authUserId OR uUe.userUserEventId.receiverId = :authUserId) AND uUe.userUserEventActive = TRUE ORDER BY uUe.userUserEventCreation ASC")
    Page<UserUserEvent> findPageableUserUserEventsWithAuthUserId(@Param("authUserId") long authUserId, Pageable pageable);

    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE (uUe.userUserEventId.senderId = :authUserId OR uUe.userUserEventId.receiverId = :authUserId) AND uUe.userUserEventActive = TRUE ORDER BY uUe.event.eventName ASC")
    Page<UserUserEvent> findPageableUserUserEventsWithAuthUserIdByEvent(@Param("authUserId") long authUserId, Pageable pageable);

    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE uUe.userUserEventId.receiverId = :authUserId AND uUe.userUserEventActive = TRUE ORDER BY uUe.userUserEventCreation ASC")
    Page<UserUserEvent> findPageableUserUserEventsDebtsWithAuthUserId(@Param("authUserId") long authUserId, Pageable pageable);

    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE uUe.userUserEventId.receiverId = :authUserId AND uUe.userUserEventActive = TRUE ORDER BY uUe.event.eventName ASC")
    Page<UserUserEvent> findPageableUserUserEventsDebtsWithAuthUserIdByEvent(@Param("authUserId") long authUserId, Pageable pageable);

    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE uUe.userUserEventId.senderId = :authUserId AND uUe.userUserEventActive = TRUE ORDER BY uUe.userUserEventCreation ASC")
    Page<UserUserEvent> findPageableUserUserEventsPaymentsWithAuthUserId(@Param("authUserId") long authUserId, Pageable pageable);

    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE uUe.userUserEventId.senderId = :authUserId AND uUe.userUserEventActive = TRUE ORDER BY uUe.event.eventName ASC")
    Page<UserUserEvent> findPageableUserUserEventsPaymentsWithAuthUserIdByEvent(@Param("authUserId") long authUserId, Pageable pageable);

    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE (uUe.userUserEventId.senderId = :authUserId OR uUe.userUserEventId.receiverId = :authUserId) AND uUe.userUserEventActive = TRUE AND uUe.confirmed = FALSE ORDER BY uUe.userUserEventCreation ASC")
    Page<UserUserEvent> findPageableNotFinishedUserUserEventsWithAuthUserId(@Param("authUserId") long authUserId, Pageable pageable);

    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE (uUe.userUserEventId.senderId = :authUserId OR uUe.userUserEventId.receiverId = :authUserId) AND uUe.userUserEventActive = TRUE AND uUe.confirmed = FALSE ORDER BY uUe.event.eventName ASC")
    Page<UserUserEvent> findPageableNotFinishedUserUserEventsWithAuthUserIdByEvent(@Param("authUserId") long authUserId, Pageable pageable);

    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE uUe.userUserEventId.receiverId = :authUserId AND uUe.userUserEventActive = TRUE AND uUe.confirmed = FALSE ORDER BY uUe.userUserEventCreation ASC")
    Page<UserUserEvent> findPageableNotFinishedUserUserEventsDebtsWithAuthUserId(@Param("authUserId") long authUserId, Pageable pageable);

    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE uUe.userUserEventId.receiverId = :authUserId AND uUe.userUserEventActive = TRUE AND uUe.confirmed = FALSE ORDER BY uUe.event.eventName ASC")
    Page<UserUserEvent> findPageableNotFinishedUserUserEventsDebtsWithAuthUserIdByEvent(@Param("authUserId") long authUserId, Pageable pageable);

    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE uUe.userUserEventId.senderId = :authUserId AND uUe.userUserEventActive = TRUE AND uUe.confirmed = FALSE ORDER BY uUe.userUserEventCreation ASC")
    Page<UserUserEvent> findPageableNotFinishedUserUserEventsPaymentsWithAuthUserId(@Param("authUserId") long authUserId, Pageable pageable);

    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE uUe.userUserEventId.senderId = :authUserId AND uUe.userUserEventActive = TRUE AND uUe.confirmed = FALSE ORDER BY uUe.event.eventName ASC")
    Page<UserUserEvent> findPageableNotFinishedUserUserEventsPaymentsWithAuthUserIdByEvent(@Param("authUserId") long authUserId, Pageable pageable);

    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE (uUe.userUserEventId.senderId = :authUserId OR uUe.userUserEventId.receiverId = :authUserId) AND uUe.userUserEventActive = TRUE ORDER BY uUe.event.eventName DESC")
    Page<UserUserEvent> findPageableUserUserEventsWithAuthUserIdByDate(@Param("authUserId") long authUserId, Pageable pageable);

    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE uUe.userUserEventId.receiverId = :authUserId AND uUe.userUserEventActive = TRUE ORDER BY uUe.event.eventName DESC")
    Page<UserUserEvent> findPageableUserUserEventsDebtsWithAuthUserIdByDate(@Param("authUserId") long authUserId, Pageable pageable);

    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE uUe.userUserEventId.senderId = :authUserId AND uUe.userUserEventActive = TRUE ORDER BY uUe.event.eventName DESC")
    Page<UserUserEvent> findPageableUserUserEventsPaymentsWithAuthUserIdByDate(@Param("authUserId") long authUserId, Pageable pageable);

    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE (uUe.userUserEventId.senderId = :authUserId OR uUe.userUserEventId.receiverId = :authUserId) AND uUe.userUserEventActive = TRUE AND uUe.confirmed = FALSE ORDER BY uUe.event.eventName DESC")
    Page<UserUserEvent> findPageableNotFinishedUserUserEventsWithAuthUserIdByDate(@Param("authUserId") long authUserId, Pageable pageable);

    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE uUe.userUserEventId.receiverId = :authUserId AND uUe.userUserEventActive = TRUE AND uUe.confirmed = FALSE ORDER BY uUe.event.eventName DESC")
    Page<UserUserEvent> findPageableNotFinishedUserUserEventsDebtsWithAuthUserIdByDate(@Param("authUserId") long authUserId, Pageable pageable);

    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE uUe.userUserEventId.senderId = :authUserId AND uUe.userUserEventActive = TRUE AND uUe.confirmed = FALSE ORDER BY uUe.event.eventName DESC")
    Page<UserUserEvent> findPageableNotFinishedUserUserEventsPaymentsWithAuthUserIdByDate(@Param("authUserId") long authUserId, Pageable pageable);

    @Query("SELECT COUNT(DISTINCT uUe) FROM userUserEvent uUe WHERE uUe.userUserEventId.eventId = :eventId AND uUe.userUserEventActive = TRUE")
    Long countUserUserEvents(@Param("eventId") long eventId);

    @Query("SELECT COUNT(DISTINCT uUe) FROM userUserEvent uUe WHERE (uUe.userUserEventId.senderId = :authUserId OR uUe.userUserEventId.receiverId = :authUserId) AND uUe.userUserEventActive = TRUE")
    Long countUserUserEventsWithAuthUserId(@Param("authUserId") long authUserId);

    @Query("SELECT COUNT(DISTINCT uUe) FROM userUserEvent uUe WHERE uUe.userUserEventId.receiverId = :authUserId AND uUe.userUserEventActive = TRUE")
    Long countUserUserEventsDebtsWithAuthUserId(@Param("authUserId") long authUserId);

    @Query("SELECT COUNT(DISTINCT uUe) FROM userUserEvent uUe WHERE uUe.userUserEventId.senderId = :authUserId AND uUe.userUserEventActive = TRUE")
    Long countUserUserEventsPaymentsWithAuthUserId(@Param("authUserId") long authUserId);

    @Query("SELECT COUNT(DISTINCT uUe) FROM userUserEvent uUe WHERE (uUe.userUserEventId.senderId = :authUserId OR uUe.userUserEventId.receiverId = :authUserId) AND uUe.userUserEventActive = TRUE AND uUe.confirmed = FALSE")
    Long countNotFinishedUserUserEventsWithAuthUserId(@Param("authUserId") long authUserId);

    @Query("SELECT COUNT(DISTINCT uUe) FROM userUserEvent uUe WHERE uUe.userUserEventId.receiverId = :authUserId AND uUe.userUserEventActive = TRUE AND uUe.confirmed = FALSE")
    Long countNotFinishedUserUserEventsDebtsWithAuthUserId(@Param("authUserId") long authUserId);

    @Query("SELECT COUNT(DISTINCT uUe) FROM userUserEvent uUe WHERE uUe.userUserEventId.senderId = :authUserId AND uUe.userUserEventActive = TRUE AND uUe.confirmed = FALSE")
    Long countNotFinishedUserUserEventsPaymentsWithAuthUserId(@Param("authUserId") long authUserId);


    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE uUe.userUserEventId.eventId = :eventId AND uUe.userUserEventActive = TRUE AND (uUe.sender.userName LIKE %:transactionActorName% OR uUe.receiver.userName LIKE %:transactionActorName%) ORDER BY uUe.cost ASC")
    Page<UserUserEvent> findPageableSearchUserUserEvents(@Param("eventId") long eventId, @Param("transactionActorName") String transactionActorName, Pageable pageable);

    @Query("SELECT DISTINCT uUe FROM userUserEvent uUe WHERE (uUe.userUserEventId.senderId = :authUserId OR uUe.userUserEventId.receiverId = :authUserId)  AND uUe.userUserEventActive = TRUE AND (uUe.sender.userName LIKE %:transactionSearchValue% OR uUe.sender.userLogin LIKE %:transactionSearchValue% OR uUe.receiver.userLogin LIKE %:transactionSearchValue%  OR uUe.receiver.userName LIKE %:transactionSearchValue% OR uUe.event.eventName LIKE %:transactionSearchValue%) ORDER BY uUe.userUserEventCreation ASC")
    Page<UserUserEvent> findPageableSearchUserUserEventsWithActorId(@Param("authUserId") long authUserId, @Param("transactionSearchValue") String transactionSearchValue, Pageable pageable);


    @Query("SELECT COUNT(DISTINCT uUe) FROM userUserEvent uUe WHERE uUe.userUserEventId.eventId = :eventId AND uUe.userUserEventActive = TRUE AND (uUe.sender.userName LIKE %:transactionActorName% OR uUe.receiver.userName LIKE %:transactionActorName%)")
    Long countSearchUserUserEvents(@Param("eventId")long eventId, @Param("transactionActorName") String transactionActorName);

    @Query("SELECT COUNT(DISTINCT uUe) FROM userUserEvent uUe WHERE (uUe.userUserEventId.senderId = :authUserId OR uUe.userUserEventId.receiverId = :authUserId) AND uUe.userUserEventActive = TRUE AND (uUe.sender.userName LIKE %:transactionSearchValue% OR uUe.sender.userLogin LIKE %:transactionSearchValue% OR uUe.receiver.userLogin LIKE %:transactionSearchValue% OR uUe.receiver.userName LIKE %:transactionSearchValue% OR uUe.event.eventName LIKE %:transactionSearchValue% )")
    Long countSearchUserUserEventsWithActorId(@Param("authUserId") long authUserId, @Param("transactionSearchValue") String transactionSearchValue);

    @Query("SELECT DISTINCT s FROM user s, user r, event e, userUserEvent uUe WHERE s.userId = uUe.userUserEventId.senderId AND r.userId = uUe.userUserEventId.receiverId AND s.userActive = TRUE AND r.userActive = TRUE AND e.eventActive = TRUE AND uUe.userUserEventActive = TRUE AND s.userNotify = TRUE AND uUe.paid = FALSE AND uUe.userUserEventCreation >= :previousDate AND uUe.userUserEventCreation <= :currentDate")
    List<User> findPendingPay(@Param("currentDate") Date currentDate, @Param("previousDate") Date previousDate);

    @Query("SELECT DISTINCT r FROM user s, user r, event e, userUserEvent uUe WHERE s.userId = uUe.userUserEventId.senderId AND r.userId = uUe.userUserEventId.receiverId AND s.userActive = TRUE AND r.userActive = TRUE AND e.eventActive = TRUE AND uUe.userUserEventActive = TRUE AND s.userNotify = TRUE AND uUe.paid = TRUE AND uUe.confirmed = FALSE AND uUe.userUserEventCreation >= :previousDate AND uUe.userUserEventCreation <= :currentDate")
    List<User> findPendingConfirm(@Param("currentDate") Date currentDate, @Param("previousDate") Date previousDate);

}
