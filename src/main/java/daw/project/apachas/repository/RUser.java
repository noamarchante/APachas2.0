package daw.project.apachas.repository;

import daw.project.apachas.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository("RUser")
public interface RUser extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long> {

    User findByUserId(long userId);

    User findByUserEmail(String email);

    User findByUserEmailAndUserActiveTrueAndUserVerifiedTrue(String email);

    User findByUserEmailAndUserVerifiedFalse(String email);

    User findByUserLoginAndUserActiveTrueAndUserVerifiedTrue(String userLogin);

    User findByUserIdAndUserActiveTrueAndUserVerifiedTrue(long userId);

    Page<User> findUsersByRolesEqualsAndUserIdIsNotAndUserActiveTrueAndUserVerifiedTrueOrderByUserLoginAsc(String roles, long authId, Pageable pageable);

    Page<User> findUsersByUserLoginContainingAndUserIdIsNotAndRolesEqualsAndUserActiveTrueAndUserVerifiedTrueOrderByUserLoginAsc(String userLogin, long authId, String roles, Pageable pageable);

    Long countByRolesAndUserIdIsNotAndUserActiveTrueAndUserVerifiedTrue(String roles, long authId);

    Long countByRolesAndUserLoginContainingAndUserIdIsNotAndUserActiveTrueAndUserVerifiedTrue(String roles, String userLogin, long authId);

    @Query("SELECT COUNT(u) FROM user u WHERE EXISTS (SELECT u FROM user u WHERE u.userLogin= :login)")
    Long findLoginAvailable(String login);

    User findByUserEmailAndTokenPasswordNull(String userEmail);
}
