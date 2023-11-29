package daw.project.apachas.repository;

import daw.project.apachas.entity.Event;
import daw.project.apachas.entity.Product;
import daw.project.apachas.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.sql.Timestamp;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RProductTests {

    @Autowired
    @Qualifier("RProduct")
    RProduct rProduct;

    @Autowired
    private TestEntityManager em;

    @Test
    public void testFindByProductId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Product product = new Product(0, event, "producto1", "descripción producto 1", 5.6, 2, null,  new Timestamp(12));
        Product product2 = new Product(0, event, "producto2", "descripción producto 2", 5.6, 2, null,  new Timestamp(12));

        em.persist(user);
        em.persist(event);
        em.persist(product);
        em.persist(product2);


        Product savedProduct = rProduct.findByProductId(product.getProductId());

        assertThat(savedProduct).isEqualTo(product);
    }

    @Test
    public void testFindByEvent_EventId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "descripcion del evento 2", new Timestamp(12) , new Timestamp(12),"loc2", null, user);
        Product product = new Product(0, event, "producto1", "descripción producto 1", 5.6, 2, null,  new Timestamp(12));
        Product product2 = new Product(0, event, "producto2", "descripción producto 2", 5.6, 2, null,  new Timestamp(12));
        Product product3 = new Product(0, event2, "producto3", "descripción producto 3", 5.6, 2, null,  new Timestamp(12));

        em.persist(user);
        em.persist(event);
        em.persist(event2);
        em.persist(product);
        em.persist(product2);
        em.persist(product3);

        List<Product> savedProduct = rProduct.findByEvent_EventId(event.getEventId());

        assertThat(savedProduct).hasSize(2).contains(product, product2);
    }

    @Test
    public void testFindAll() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "descripcion del evento 2", new Timestamp(12) , new Timestamp(12),"loc2", null, user);
        Product product = new Product(0, event, "producto1", "descripción producto 1", 5.6, 2, null,  new Timestamp(12));
        Product product2 = new Product(0, event, "producto2", "descripción producto 2", 5.6, 2, null,  new Timestamp(12));
        Product product3 = new Product(0, event2, "producto3", "descripción producto 3", 5.6, 2, null,  new Timestamp(12));

        em.persist(user);
        em.persist(event);
        em.persist(event2);
        em.persist(product);
        em.persist(product2);
        em.persist(product3);

        Page<Product> savedProduct = rProduct.findAll(Pageable.unpaged());

        assertThat(savedProduct).hasSize(3).contains(product, product2, product3);
    }

    @Test
    public void testCountByEvent_EventIdAndProductActiveTrue() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "descripcion del evento 2", new Timestamp(12) , new Timestamp(12),"loc2", null, user);
        Product product = new Product(0, event, "producto1", "descripción producto 1", 5.6, 2, null,  new Timestamp(12));
        Product product2 = new Product(0, event, "producto2", "descripción producto 2", 5.6, 2, null,  new Timestamp(12));
        Product product3 = new Product(0, event2, "producto3", "descripción producto 3", 5.6, 2, null,  new Timestamp(12));

        em.persist(user);
        em.persist(event);
        em.persist(event2);
        em.persist(product);
        em.persist(product2);
        em.persist(product3);

        Long savedProduct = rProduct.countByEvent_EventIdAndProductActiveTrue(event.getEventId());

        assertThat(savedProduct).isEqualTo(2);
    }

    @Test
    public void testCountByEvent_EventIdAndProductNameContainingAndProductActiveTrue() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "descripcion del evento 2", new Timestamp(12) , new Timestamp(12),"loc2", null, user);
        Product product = new Product(0, event, "producto1", "descripción producto 1", 5.6, 2, null,  new Timestamp(12));
        Product product2 = new Product(0, event, "producto2", "descripción producto 2", 5.6, 2, null,  new Timestamp(12));
        Product product3 = new Product(0, event2, "producto3", "descripción producto 3", 5.6, 2, null,  new Timestamp(12));

        em.persist(user);
        em.persist(event);
        em.persist(event2);
        em.persist(product);
        em.persist(product2);
        em.persist(product3);

        Long savedProduct = rProduct.countByEvent_EventIdAndProductNameContainingAndProductActiveTrue(event.getEventId(), "1");

        assertThat(savedProduct).isEqualTo(1);
    }

    @Test
    public void testFindByEvent_EventIdAndProductNameContainingAndProductActiveTrueOrderByProductNameAsc() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "descripcion del evento 2", new Timestamp(12) , new Timestamp(12),"loc2", null, user);
        Product product = new Product(0, event, "producto1", "descripción producto 1", 5.6, 2, null,  new Timestamp(12));
        Product product2 = new Product(0, event, "producto2", "descripción producto 2", 5.6, 2, null,  new Timestamp(12));
        Product product3 = new Product(0, event2, "producto3", "descripción producto 3", 5.6, 2, null,  new Timestamp(12));

        em.persist(user);
        em.persist(event);
        em.persist(event2);
        em.persist(product);
        em.persist(product2);
        em.persist(product3);

        Page<Product> savedProduct = rProduct.findByEvent_EventIdAndProductNameContainingAndProductActiveTrueOrderByProductNameAsc(event.getEventId(), "p", Pageable.unpaged());

        assertThat(savedProduct).hasSize(2).contains(product);
    }

    @Test
    public void testFindByEvent_EventIdAndProductActiveTrueOrderByProductNameAsc() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "descripcion del evento 2", new Timestamp(12) , new Timestamp(12),"loc2", null, user);
        Product product = new Product(0, event, "producto1", "descripción producto 1", 5.6, 2, null,  new Timestamp(12));
        Product product2 = new Product(0, event, "producto2", "descripción producto 2", 5.6, 2, null,  new Timestamp(12));
        Product product3 = new Product(0, event2, "producto3", "descripción producto 3", 5.6, 2, null,  new Timestamp(12));

        em.persist(user);
        em.persist(event);
        em.persist(event2);
        em.persist(product);
        em.persist(product2);
        em.persist(product3);

        List<Product> savedProduct = rProduct.findByEvent_EventIdAndProductActiveTrueOrderByProductNameAsc(event.getEventId());

        assertThat(savedProduct).hasSize(2).contains(product, product2);
    }

    @Test
    public void testFindByEvent_EventIdAndProductActiveTrueOrderByProductNameAscPage() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "descripcion del evento 2", new Timestamp(12) , new Timestamp(12),"loc2", null, user);
        Product product = new Product(0, event, "producto1", "descripción producto 1", 5.6, 2, null,  new Timestamp(12));
        Product product2 = new Product(0, event, "producto2", "descripción producto 2", 5.6, 2, null,  new Timestamp(12));
        Product product3 = new Product(0, event2, "producto3", "descripción producto 3", 5.6, 2, null,  new Timestamp(12));

        em.persist(user);
        em.persist(event);
        em.persist(event2);
        em.persist(product);
        em.persist(product2);
        em.persist(product3);

        Page<Product> savedProduct = rProduct.findByEvent_EventIdAndProductActiveTrueOrderByProductNameAsc(event.getEventId(), Pageable.unpaged());

        assertThat(savedProduct).hasSize(2).contains(product, product2);
    }

    @Test
    public void testSumTotalProductCost() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "descripcion del evento 2", new Timestamp(12) , new Timestamp(12),"loc2", null, user);
        Product product = new Product(0, event, "producto1", "descripción producto 1", 5.6, 2, null,  new Timestamp(12));
        Product product2 = new Product(0, event, "producto2", "descripción producto 2", 5.6, 2, null,  new Timestamp(12));
        Product product3 = new Product(0, event2, "producto3", "descripción producto 3", 5.6, 2, null,  new Timestamp(12));

        em.persist(user);
        em.persist(event);
        em.persist(event2);
        em.persist(product);
        em.persist(product2);
        em.persist(product3);

        Double savedProduct = rProduct.sumTotalProductCost(event.getEventId());

        assertThat(savedProduct).isEqualTo(22.4);
    }
}