package daw.project.apachas.repository;

import daw.project.apachas.entity.User;
import daw.project.apachas.entity.UserProduct;
import daw.project.apachas.entity.id.UserProductId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("RUserProduct")
public interface RUserProduct extends CrudRepository<UserProduct, UserProductId>, PagingAndSortingRepository<UserProduct, UserProductId> {
    UserProduct findByUserProductId(UserProductId userProductId);

    List<UserProduct> findByProduct_ProductIdAndUserProductActiveTrue(long productId);

    @Override
    Page<UserProduct> findAll(Pageable pageable);

    @Query("SELECT COUNT(DISTINCT uP) FROM userProduct uP WHERE uP.userProductId.productId = :productId AND uP.userProductActive = TRUE")
    Long countPartakers(@Param("productId") long productId);

    @Query("SELECT DISTINCT uP.user FROM userProduct uP WHERE uP.userProductId.productId = :productId AND uP.userProductActive = TRUE ORDER BY uP.user.userLogin ASC")
    Page<User> findPageablePartakers(@Param("productId") long productId, Pageable pageable);

    @Query("SELECT DISTINCT uP.user FROM  userProduct uP WHERE uP.userProductId.productId = :productId AND uP.userProductActive = TRUE ORDER BY uP.user.userLogin ASC")
    List<User> findPartakers(@Param("productId") long productId);

    List<UserProduct> findUserProductsByProduct_Event_EventIdOrderByProductAsc(@Param("eventId") long eventId);

    UserProduct findUserProductByUserProductId_ProductIdAndUserProductId_UserId(@Param("productId") long productId, @Param("authId") long authId);

}
