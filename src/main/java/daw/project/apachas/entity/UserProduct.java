package daw.project.apachas.entity;

import daw.project.apachas.entity.id.UserProductId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "userProduct")
@Table(name = "userProduct")
public class UserProduct implements Serializable {
    @EmbeddedId
    private UserProductId userProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "productId", referencedColumnName = "productId", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user;

    @Column(name = "userProductCreation", nullable = false)
    private Timestamp userProductCreation;

    @Column(name = "userProductRemoval")
    private Timestamp userProductRemoval;

    @Column(name = "userProductActive", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    private boolean userProductActive;

    public UserProduct(UserProductId userProductId) {
        this.userProductId = userProductId;
        this.setUserProductActive(false);
        this.setUserProductRemoval(null);
        this.setUserProductCreation(new Timestamp(System.currentTimeMillis()));
    }
}
