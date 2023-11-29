package daw.project.apachas.repository;

import daw.project.apachas.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("RProduct")
public interface RProduct extends CrudRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {

    Product findByProductId(long productId);

    List<Product> findByEvent_EventId(long eventId);

    @Query("SELECT SUM(p.productQuantity*p.productCost) FROM product p WHERE p.event.eventId = :eventId AND p.productActive = TRUE")
    Double sumTotalProductCost(@Param("eventId") long eventId);

    Long countByEvent_EventIdAndProductActiveTrue(@Param("eventId") long eventId);

    Long countByEvent_EventIdAndProductNameContainingAndProductActiveTrue(@Param("eventId")long eventId, @Param("productName") String productName);

    Page<Product> findByEvent_EventIdAndProductNameContainingAndProductActiveTrueOrderByProductNameAsc(@Param("eventId") long eventId, @Param("productName") String productName, Pageable pageable);

    @Override
    Page<Product> findAll(Pageable pageable);
    List<Product> findByEvent_EventIdAndProductActiveTrueOrderByProductNameAsc(@Param("eventId") long eventId);

    Page<Product> findByEvent_EventIdAndProductActiveTrueOrderByProductNameAsc(@Param("eventId") long eventId, Pageable pageable);
}
