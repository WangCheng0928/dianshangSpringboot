package com.shuidian.repository;

import com.shuidian.domain.Cart;
import com.shuidian.domain.UserInfo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: wangcheng
 * @Date: Created in 10:06 2018/4/23
 */
public interface CartRepository extends JpaRepository<Cart, Integer>, JpaSpecificationExecutor<Cart> {

    Cart findByUserIdAndProductId(Integer userId, Integer productId);
    List<Cart> findByUserId(Integer userId);

    //删除那些坑，要加一个@Transactional
    @Transactional
    void deleteByProductIdAndUserId(Integer productId, Integer userId);

    @Query("select count(cart) from Cart cart where cart.checked=0 and cart.userId = ?1")
    Integer findCartProductCheckedStatusByUserId(Integer userId);

    @Transactional
    @Modifying
    @Query("UPDATE Cart cart set cart.quantity = ?1 where cart.id = ?2")
    void updateCountByCartId(Integer count, Integer cartId);


    //如果试根据唯一主键来更新，就只会更新一条，如果不是根据主键更新，就会更新所有满足下面条件的数据
    //以下两种写法都对的，真nice
    @Transactional
    @Modifying
    @Query("update Cart set checked = ?1 where userId = ?2")
    void CheckedAllOrUnCheckedAllByUserId(Integer checked, Integer userId);

    @Transactional
    @Modifying
    @Query("update Cart cart set cart.checked = ?1 where cart.userId = ?2 and cart.productId = ?3")
    void CheckedOrUnCheckedByUserIdAndProductId(Integer checked, Integer userId, Integer productId);

    @Query("select cart from Cart cart where cart.checked=1 and cart.userId = ?1")
    List<Cart> findCartCheckedStatusByUserId(Integer userId);

//    @Transactional
//    @Modifying
//    @Query("select count(cart.quantity) from Cart cart where userId = ?1")
//    Integer selectCartProductCount(Integer userId);
}
