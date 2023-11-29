package daw.project.apachas.model;


import daw.project.apachas.entity.UserProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Setter
@Getter
@NoArgsConstructor
public class MUserProduct {
    private long productId;
    private long userId;
    private Timestamp userProductCreation;
    private Timestamp userProductRemoval;
    private boolean userProductActive;

    public MUserProduct(UserProduct userProduct) {
        this.productId = userProduct.getUserProductId().getProductId();
        this.userId = userProduct.getUserProductId().getUserId();
        this.userProductActive = userProduct.isUserProductActive();
        this.userProductCreation = userProduct.getUserProductCreation();
        this.userProductRemoval = userProduct.getUserProductRemoval();
    }
}
