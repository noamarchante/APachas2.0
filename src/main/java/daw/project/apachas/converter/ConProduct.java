package daw.project.apachas.converter;

import daw.project.apachas.entity.Event;
import daw.project.apachas.entity.Product;
import org.springframework.stereotype.Component;
import daw.project.apachas.model.MProduct;
import java.util.ArrayList;
import java.util.List;

@Component("ConProduct")
public class ConProduct {
    public List<MProduct> conProductList(List<Product> productList) {
        List<MProduct> mProductList = new ArrayList<>();
        for (Product product : productList) {
            mProductList.add(new MProduct(product));
        }
        return mProductList;
    }

    public MProduct conProduct(Product product) {
        return new MProduct(product);
    }

    public Product conMProduct(MProduct mProduct) {
        return new Product(mProduct.getProductId(), new Event(mProduct.getEventId()), mProduct.getProductName(), mProduct.getProductDescription(), mProduct.getProductCost(), mProduct.getProductQuantity(), mProduct.getProductPhoto(), mProduct.getProductPurchaseDate());
    }
}
