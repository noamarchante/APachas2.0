package daw.project.apachas.model;


import daw.project.apachas.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Setter
@Getter
@NoArgsConstructor
public class MProduct {

    private long productId;
    private String productName;
    private String productDescription;
    private Double productCost;
    private int productQuantity;
    private String productPhoto;
    private long eventId;
    private Timestamp productPurchaseDate;
    private boolean productActive;
    private Timestamp productCreation;
    private Timestamp productRemoval;

    public MProduct(Product product) {
        this.productId = product.getProductId();
        this.eventId = product.getEvent().getEventId();
        this.productName = product.getProductName();
        this.productDescription = product.getProductDescription();
        this.productCost = product.getProductCost();
        this.productQuantity = product.getProductQuantity();
        this.productPhoto = product.getProductPhoto();
        this.productPurchaseDate = product.getProductPurchaseDate();
        this.productActive = product.isProductActive();
        this.productCreation = product.getProductCreation();
        this.productRemoval = product.getProductRemoval();
    }
}
