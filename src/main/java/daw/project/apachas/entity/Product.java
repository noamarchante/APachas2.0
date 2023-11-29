package daw.project.apachas.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "product")
@Table(name = "product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "productId")
    private long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventId", nullable = false)
    @NotNull
    @NotBlank
    private Event event;

    @Column(name = "productName", length = 50, nullable = false)
    @Size(min = 4, max = 50)
    @NotNull
    @NotBlank
    private String productName;

    @Column(name = "productDescription", length = 155)
    @Size(min=0,max = 155)
    @NotBlank
    private String productDescription;

    @Column(name = "productCost", length = 12, nullable = false)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 12)
    @ColumnDefault(value = "0")
    private Double productCost;

    @Column(name = "productQuantity", length = 12, nullable = false)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 12)
    @ColumnDefault(value = "1")
    private int productQuantity;

    @Column(name = "productPhoto",  length = 100000)
    private String productPhoto;

    @Column(name = "productPurchaseDate", nullable = false, length = 19)
    @Size(min = 16, max = 16)
    @NotNull
    @NotBlank
    private Timestamp productPurchaseDate;

    @Column(name = "productCreation", length = 19, nullable = false)
    private Timestamp productCreation;

    @Column(name = "productRemoval", length = 19)
    private Timestamp productRemoval;

    @Column(name = "productActive", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    private boolean productActive;

    public Product(long productId, Event event, String productName, String productDescription, Double productCost, int productQuantity, String productPhoto, Timestamp productPurchaseDate) {
        this.productId = productId;
        this.setEvent(event);
        this.setProductName(productName);
        this.setProductDescription(productDescription);
        this.setProductCost(productCost);
        this.setProductQuantity(productQuantity);
        this.setProductPhoto(productPhoto);
        this.setProductPurchaseDate(productPurchaseDate);
        this.setProductActive(true);
        this.setProductCreation(new Timestamp(System.currentTimeMillis()));
        this.setProductRemoval(null);
    }
}
