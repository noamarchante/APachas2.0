package daw.project.apachas.repository;

import daw.project.apachas.entity.User;
import daw.project.apachas.entity.UserUser;
import daw.project.apachas.entity.id.UserUserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("RUserUser")
public interface RUserUser extends CrudRepository<UserUser, UserUserId>, PagingAndSortingRepository<UserUser, UserUserId> {

    UserUser findByUserUserId(UserUserId userUserId);

    @Query("SELECT uU.friend.userLogin FROM userUser uU WHERE uU.userUserId.userId = :authId AND uU.accept = FALSE AND uU.userUserActive = TRUE")
    String[] findNotifications(@Param("authId") long authId);

    @Query("SELECT uU.user FROM userUser uU WHERE uU.userUserId.friendId = :friendId AND uU.userUserActive = TRUE")
    List<User> findFriendsByFriendId(@Param("friendId") long friendId);

    @Query("SELECT uU.friend FROM userUser uU WHERE uU.userUserId.userId = :userId AND uU.userUserActive = TRUE")
    List<User> findFriendsByUserId(@Param("userId") long userId);

    @Query("SELECT COUNT(DISTINCT u) FROM user u, userUser uU WHERE " +
            "(u.userId IN " +
                "(SELECT DISTINCT u FROM user u, userUser uU WHERE u.userId = uU.userUserId.friendId AND uU.userUserId.userId = :authId AND uU.accept = TRUE AND u.userActive = TRUE AND u.userVerified = TRUE AND uU.userUserActive = TRUE) " +
            "OR u.userId IN " +
                "(SELECT DISTINCT u FROM user u, userUser uU WHERE u.userId = uU.userUserId.userId AND uU.userUserId.friendId = :authId AND uU.accept = TRUE AND u.userActive = TRUE AND u.userVerified = TRUE AND uU.userUserActive = TRUE))" +
            "AND (u.userId IN " +
                "(SELECT DISTINCT u FROM user u, userUser uU WHERE u.userId = uU.userUserId.friendId AND uU.userUserId.userId = :userId AND uU.accept = TRUE AND u.userActive = TRUE AND u.userVerified = TRUE AND uU.userUserActive = TRUE) " +
            "OR u.userId IN " +
                "(SELECT DISTINCT u FROM user u, userUser uU WHERE u.userId = uU.userUserId.userId AND uU.userUserId.friendId = :userId AND uU.accept = TRUE AND u.userActive = TRUE AND u.userVerified = TRUE AND uU.userUserActive = TRUE))" +
            "AND u.userActive = TRUE AND u.userVerified = TRUE AND uU.userUserActive = TRUE AND uU.accept = TRUE")
    Long countMutualFriends(@Param("userId")long userId, @Param("authId") long authId);

    @Query("SELECT DISTINCT u FROM user u, userUser uU WHERE " +
            "(u.userId IN " +
                "(SELECT DISTINCT u FROM user u, userUser uU WHERE u.userId = uU.userUserId.friendId AND uU.userUserId.userId = :authId AND uU.accept = TRUE AND u.userActive = TRUE AND u.userVerified = TRUE AND uU.userUserActive = TRUE) " +
            "OR u.userId IN " +
                "(SELECT DISTINCT u FROM user u, userUser uU WHERE u.userId = uU.userUserId.userId AND uU.userUserId.friendId = :authId AND uU.accept = TRUE AND u.userActive = TRUE AND u.userVerified = TRUE AND uU.userUserActive = TRUE))" +
            "AND (u.userId IN " +
                "(SELECT DISTINCT u FROM user u, userUser uU WHERE u.userId = uU.userUserId.friendId AND uU.userUserId.userId = :userId AND uU.accept = TRUE AND u.userActive = TRUE AND u.userVerified = TRUE AND uU.userUserActive = TRUE) " +
            "OR u.userId IN " +
                "(SELECT DISTINCT u FROM user u, userUser uU WHERE u.userId = uU.userUserId.userId AND uU.userUserId.friendId = :userId AND uU.accept = TRUE AND u.userActive = TRUE AND u.userVerified = TRUE AND uU.userUserActive = TRUE))" +
            "AND u.userActive = TRUE AND u.userVerified = TRUE AND uU.userUserActive = TRUE AND uU.accept = TRUE")
    Page<User> findPageableMutualFriends(@Param("userId") long userId, @Param("authId") long authId, Pageable pageable);

    @Query("SELECT DISTINCT u FROM user u, user f, userUser uU WHERE f.userId = uU.userUserId.friendId AND u.userId = uU.userUserId.userId AND u.userActive = TRUE AND f.userActive = TRUE AND uU.userUserActive = TRUE AND u.userNotify = TRUE AND uU.userUserCreation BETWEEN :previousDate AND :currentDate")
    List<User> findNewRequest(Date currentDate, Date previousDate);
}
