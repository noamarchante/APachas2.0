package daw.project.apachas.service;

import daw.project.apachas.converter.ConUser;
import daw.project.apachas.converter.ConUserProduct;
import daw.project.apachas.entity.*;
import daw.project.apachas.model.MProduct;
import daw.project.apachas.model.MUser;
import daw.project.apachas.model.MUserProduct;
import daw.project.apachas.repository.*;
import daw.project.apachas.entity.id.UserProductId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service("SUserProduct")
public class SUserProduct {

    @Autowired
    @Qualifier("RUserProduct")
    private RUserProduct rUserProduct;

    @Autowired
    @Qualifier("RProduct")
    private RProduct rProduct;

    @Autowired
    @Qualifier("RUserEvent")
    private RUserEvent rUserEvent;

    @Autowired
    @Qualifier("RUser")
    private RUser rUser;

    @Autowired
    @Qualifier("REvent")
    private REvent rEvent;

    @Autowired
    @Qualifier("ConUserProduct")
    private ConUserProduct conUserProduct;

    @Autowired
    @Qualifier("ConUser")
    private ConUser conUser;

    @Autowired
    @Qualifier("SProduct")
    private SProduct sProduct;

    public synchronized boolean insertUserProduct(MUserProduct mUserProduct) {
        UserProduct userProduct = conUserProduct.conMUserProduct(mUserProduct);
        UserProduct existingUserProduct = rUserProduct.findByUserProductId(userProduct.getUserProductId());
        Product existingProduct = rProduct.findByProductId(userProduct.getUserProductId().getProductId());
        User existingUser = rUser.findByUserId(userProduct.getUserProductId().getUserId());
        if (existingUserProduct != null || existingProduct == null || existingUser == null) {
            return false;
        } else {
            userProduct.setUserProductActive(true);
            userProduct.setUserProductRemoval(null);
            userProduct.setUserProductCreation(new Timestamp(System.currentTimeMillis()));
            userProduct.setProduct(existingProduct);
            userProduct.setUser(existingUser);
            rUserProduct.save(userProduct);
            setUserDebt(existingProduct.getEvent().getEventId());
            return true;
        }
    }

    public synchronized boolean updateUserProduct(MUserProduct mUserProduct) {
        Product existingProduct = rProduct.findByProductId(mUserProduct.getProductId());
        User existingUser = rUser.findByUserId(mUserProduct.getUserId());
        if (existingProduct != null && existingUser != null){
            UserProduct existingUserProduct = rUserProduct.findByUserProductId(new UserProductId(mUserProduct.getProductId(), mUserProduct.getUserId()));
            if (existingUserProduct != null){
                existingUserProduct.setUserProductRemoval(null);
                existingUserProduct.setUserProductActive(true);
                rUserProduct.save(existingUserProduct);
                setUserDebt(existingProduct.getEvent().getEventId());
            }
            return true;
        }else{
            return false;
        }
    }

    public synchronized boolean deleteUserProduct(long productId, long userId) {
        UserProduct existingUserProduct = rUserProduct.findByUserProductId(new UserProductId(productId, userId));
        Product existingProduct = rProduct.findByProductId(productId);
        User existingUser = rUser.findByUserId(userId);
        if (existingUserProduct != null || existingProduct != null || existingUser != null) {
            existingUserProduct.setUserProductActive(false);
            existingUserProduct.setUserProductRemoval(new Timestamp(System.currentTimeMillis()));
            rUserProduct.save(existingUserProduct);
            setUserDebt(existingProduct.getEvent().getEventId());
            return true;
        } else {
            return false;
        }
    }

    public synchronized Long countPartakers(long productId){
        return rUserProduct.countPartakers(productId);
    }

    public synchronized List<MUser> selectPageablePartakers(long productId, Pageable pageable) {
        return conUser.conUserList(rUserProduct.findPageablePartakers(productId, pageable).getContent());
    }

    public synchronized List<MUser> selectPartakers(long productId) {
        List<User> userList = rUserProduct.findPartakers(productId);
        return conUser.conUserList(userList);
    }

    public synchronized MUserProduct selectUserProduct(long productId, long authId) {
        return conUserProduct.conUserProduct(rUserProduct.findUserProductByUserProductId_ProductIdAndUserProductId_UserId(productId, authId));
    }

    @Transactional
    public synchronized void setUserDebt(long eventId){
        Event existingEvent = rEvent.findByEventId(eventId);
        if (existingEvent != null) {
            List<MProduct> productList = sProduct.selectProducts(eventId);
            List<UserEvent> existingUserEventList = rUserEvent.findByUserEventId_EventId(eventId);
            existingUserEventList.forEach((userEvent) -> {
                userEvent.setDebt(0.0);
                rUserEvent.save(userEvent);
            });
            productList.forEach((product)->{
                List<MUserProduct> userProductList = conUserProduct.conUserProductList(rUserProduct.findByProduct_ProductIdAndUserProductActiveTrue(product.getProductId()));
                if (userProductList.size() != 0) {
                    double productDebt = Math.round(((product.getProductCost() * product.getProductQuantity()) / userProductList.size())*100.0)/100.0;
                    userProductList.forEach((userProduct) -> existingUserEventList.forEach((userEvent) -> {
                        if (userProduct.getUserId() == userEvent.getUser().getUserId()){
                            userEvent.setDebt(userEvent.getDebt() + productDebt);
                            rUserEvent.save(userEvent);
                        }
                    }));
                }
            });
        }
    }
}
