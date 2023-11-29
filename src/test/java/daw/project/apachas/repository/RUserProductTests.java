package daw.project.apachas.repository;

import daw.project.apachas.entity.Event;
import daw.project.apachas.entity.Product;
import daw.project.apachas.entity.User;
import daw.project.apachas.entity.UserProduct;
import daw.project.apachas.entity.id.UserProductId;
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
public class RUserProductTests {
    @Autowired
    @Qualifier("RUserProduct")
    RUserProduct rUserProduct;

    @Autowired
    private TestEntityManager em;

    @Test
    public void testFindByUserProductId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "descripcion del evento 2", new Timestamp(12) , new Timestamp(12),"loc2", null, user);
        Product product = new Product(0, event, "producto1", "descripción producto 1", 5.6, 2, null,  new Timestamp(12));
        Product product2 = new Product(0, event, "producto2", "descripción producto 2", 5.6, 2, null,  new Timestamp(12));
        Product product3 = new Product(0, event2, "producto3", "descripción producto 3", 5.6, 2, null,  new Timestamp(12));

        UserProduct userProduct = new UserProduct(new UserProductId(1,1));
        userProduct.setUser(user);
        userProduct.setProduct(product);
        UserProduct userProduct2 = new UserProduct(new UserProductId(1,2));
        userProduct2.setUser(user2);
        userProduct2.setProduct(product);

        em.persist(user);
        em.persist(user2);
        em.persist(event);
        em.persist(event2);
        em.persist(product);
        em.persist(product2);
        em.persist(product3);
        em.persist(userProduct);
        em.persist(userProduct2);

        UserProduct savedUserProduct = rUserProduct.findByUserProductId(userProduct.getUserProductId());

        assertThat(savedUserProduct).isEqualTo(userProduct);
    }

    @Test
    public void testFindByProduct_ProductIdAndUserProductActiveTrue() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "descripcion del evento 2", new Timestamp(12) , new Timestamp(12),"loc2", null, user);
        Product product = new Product(0, event, "producto1", "descripción producto 1", 5.6, 2, null,  new Timestamp(12));
        Product product2 = new Product(0, event, "producto2", "descripción producto 2", 5.6, 2, null,  new Timestamp(12));
        Product product3 = new Product(0, event2, "producto3", "descripción producto 3", 5.6, 2, null,  new Timestamp(12));

        UserProduct userProduct = new UserProduct(new UserProductId(1,1));
        userProduct.setUser(user);
        userProduct.setProduct(product);
        userProduct.setUserProductActive(true);
        UserProduct userProduct2 = new UserProduct(new UserProductId(1,2));
        userProduct2.setUser(user2);
        userProduct2.setProduct(product);
        userProduct2.setUserProductActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(event);
        em.persist(event2);
        em.persist(product);
        em.persist(product2);
        em.persist(product3);
        em.persist(userProduct);
        em.persist(userProduct2);

        List<UserProduct> savedUserProduct = rUserProduct.findByProduct_ProductIdAndUserProductActiveTrue(userProduct.getUserProductId().getProductId());

        assertThat(savedUserProduct).hasSize(2).contains(userProduct, userProduct2);
    }

    @Test
    public void testFindAll() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "descripcion del evento 2", new Timestamp(12) , new Timestamp(12),"loc2", null, user);
        Product product = new Product(0, event, "producto1", "descripción producto 1", 5.6, 2, null,  new Timestamp(12));
        Product product2 = new Product(0, event, "producto2", "descripción producto 2", 5.6, 2, null,  new Timestamp(12));
        Product product3 = new Product(0, event2, "producto3", "descripción producto 3", 5.6, 2, null,  new Timestamp(12));

        UserProduct userProduct = new UserProduct(new UserProductId(1,1));
        userProduct.setUser(user);
        userProduct.setProduct(product);
        userProduct.setUserProductActive(true);
        UserProduct userProduct2 = new UserProduct(new UserProductId(1,2));
        userProduct2.setUser(user2);
        userProduct2.setProduct(product);
        userProduct2.setUserProductActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(event);
        em.persist(event2);
        em.persist(product);
        em.persist(product2);
        em.persist(product3);
        em.persist(userProduct);
        em.persist(userProduct2);

        Page<UserProduct> savedUserProduct = rUserProduct.findAll(Pageable.unpaged());

        assertThat(savedUserProduct).hasSize(2).contains(userProduct, userProduct2);
    }

    @Test
    public void testCountPartakers() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "descripcion del evento 2", new Timestamp(12) , new Timestamp(12),"loc2", null, user);
        Product product = new Product(0, event, "producto1", "descripción producto 1", 5.6, 2, null,  new Timestamp(12));
        Product product2 = new Product(0, event, "producto2", "descripción producto 2", 5.6, 2, null,  new Timestamp(12));
        Product product3 = new Product(0, event2, "producto3", "descripción producto 3", 5.6, 2, null,  new Timestamp(12));

        UserProduct userProduct = new UserProduct(new UserProductId(1,1));
        userProduct.setUser(user);
        userProduct.setProduct(product);
        userProduct.setUserProductActive(true);
        UserProduct userProduct2 = new UserProduct(new UserProductId(1,2));
        userProduct2.setUser(user2);
        userProduct2.setProduct(product);
        userProduct2.setUserProductActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(event);
        em.persist(event2);
        em.persist(product);
        em.persist(product2);
        em.persist(product3);
        em.persist(userProduct);
        em.persist(userProduct2);

        Long savedUserProduct = rUserProduct.countPartakers(userProduct.getUserProductId().getProductId());

        assertThat(savedUserProduct).isEqualTo(2);
    }

    @Test
    public void testFindPageablePartakers() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "descripcion del evento 2", new Timestamp(12) , new Timestamp(12),"loc2", null, user);
        Product product = new Product(0, event, "producto1", "descripción producto 1", 5.6, 2, null,  new Timestamp(12));
        Product product2 = new Product(0, event, "producto2", "descripción producto 2", 5.6, 2, null,  new Timestamp(12));
        Product product3 = new Product(0, event2, "producto3", "descripción producto 3", 5.6, 2, null,  new Timestamp(12));

        UserProduct userProduct = new UserProduct(new UserProductId(1,1));
        userProduct.setUser(user);
        userProduct.setProduct(product);
        userProduct.setUserProductActive(true);
        UserProduct userProduct2 = new UserProduct(new UserProductId(1,2));
        userProduct2.setUser(user2);
        userProduct2.setProduct(product);
        userProduct2.setUserProductActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(event);
        em.persist(event2);
        em.persist(product);
        em.persist(product2);
        em.persist(product3);
        em.persist(userProduct);
        em.persist(userProduct2);

        Page<User> savedUserProduct = rUserProduct.findPageablePartakers(userProduct.getUserProductId().getProductId(), Pageable.unpaged());

        assertThat(savedUserProduct).hasSize(2).contains(user,user2);
    }

    @Test
    public void testFindPartakers() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "descripcion del evento 2", new Timestamp(12) , new Timestamp(12),"loc2", null, user);
        Product product = new Product(0, event, "producto1", "descripción producto 1", 5.6, 2, null,  new Timestamp(12));
        Product product2 = new Product(0, event, "producto2", "descripción producto 2", 5.6, 2, null,  new Timestamp(12));
        Product product3 = new Product(0, event2, "producto3", "descripción producto 3", 5.6, 2, null,  new Timestamp(12));

        UserProduct userProduct = new UserProduct(new UserProductId(1,1));
        userProduct.setUser(user);
        userProduct.setProduct(product);
        userProduct.setUserProductActive(true);
        UserProduct userProduct2 = new UserProduct(new UserProductId(1,2));
        userProduct2.setUser(user2);
        userProduct2.setProduct(product);
        userProduct2.setUserProductActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(event);
        em.persist(event2);
        em.persist(product);
        em.persist(product2);
        em.persist(product3);
        em.persist(userProduct);
        em.persist(userProduct2);

        List<User> savedUserProduct = rUserProduct.findPartakers(userProduct.getUserProductId().getProductId());

        assertThat(savedUserProduct).hasSize(2).contains(user,user2);
    }

    @Test
    public void testFindUserProductsByProduct_Event_EventIdOrderByProductAsc() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "descripcion del evento 2", new Timestamp(12) , new Timestamp(12),"loc2", null, user);
        Product product = new Product(0, event, "producto1", "descripción producto 1", 5.6, 2, null,  new Timestamp(12));
        Product product2 = new Product(0, event, "producto2", "descripción producto 2", 5.6, 2, null,  new Timestamp(12));
        Product product3 = new Product(0, event2, "producto3", "descripción producto 3", 5.6, 2, null,  new Timestamp(12));

        UserProduct userProduct = new UserProduct(new UserProductId(1,1));
        userProduct.setUser(user);
        userProduct.setProduct(product);
        userProduct.setUserProductActive(true);
        UserProduct userProduct2 = new UserProduct(new UserProductId(1,2));
        userProduct2.setUser(user2);
        userProduct2.setProduct(product);
        userProduct2.setUserProductActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(event);
        em.persist(event2);
        em.persist(product);
        em.persist(product2);
        em.persist(product3);
        em.persist(userProduct);
        em.persist(userProduct2);

        List<UserProduct> savedUserProduct = rUserProduct.findUserProductsByProduct_Event_EventIdOrderByProductAsc(event.getEventId());

        assertThat(savedUserProduct).hasSize(2).contains(userProduct,userProduct2);
    }

    @Test
    public void testFindUserProductByUserProductId_ProductIdAndUserProductId_UserId() {
        User user = new User(0, "noa", "López Marchante", "noamarchante", "abc123.", "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");
        User user2 = new User(0, "laura", "Blanco Delgado", "lauraBlanco", "abc123.", "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        Event event = new Event(0, "e1", "descripcion del evento 1", new Timestamp(12) , new Timestamp(12),"loc1", null, user);
        Event event2 = new Event(0, "e2", "descripcion del evento 2", new Timestamp(12) , new Timestamp(12),"loc2", null, user);
        Product product = new Product(0, event, "producto1", "descripción producto 1", 5.6, 2, null,  new Timestamp(12));
        Product product2 = new Product(0, event, "producto2", "descripción producto 2", 5.6, 2, null,  new Timestamp(12));
        Product product3 = new Product(0, event2, "producto3", "descripción producto 3", 5.6, 2, null,  new Timestamp(12));

        UserProduct userProduct = new UserProduct(new UserProductId(1,1));
        userProduct.setUser(user);
        userProduct.setProduct(product);
        userProduct.setUserProductActive(true);
        UserProduct userProduct2 = new UserProduct(new UserProductId(1,2));
        userProduct2.setUser(user2);
        userProduct2.setProduct(product);
        userProduct2.setUserProductActive(true);


        em.persist(user);
        em.persist(user2);
        em.persist(event);
        em.persist(event2);
        em.persist(product);
        em.persist(product2);
        em.persist(product3);
        em.persist(userProduct);
        em.persist(userProduct2);

        UserProduct savedUserProduct = rUserProduct.findUserProductByUserProductId_ProductIdAndUserProductId_UserId(userProduct.getUserProductId().getProductId(), userProduct.getUserProductId().getUserId());

        assertThat(savedUserProduct).isEqualTo(userProduct);
    }
}