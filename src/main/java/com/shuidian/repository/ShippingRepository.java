package com.shuidian.repository;

import com.shuidian.domain.Shipping;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: wangcheng
 * @Date: Created in 19:45 2018/4/23
 */
public interface ShippingRepository extends JpaRepository<Shipping, Integer> {

    @Transactional
    int deleteByIdAndUserId(Integer shippingId, Integer userId);

    Shipping findByIdAndUserId(Integer shippingId, Integer useId);

    List<Shipping> findByUserId(Integer userId);

//    @Transactional
//    @Modifying
//    @Query("update shipping set shipping = ?1 where ")
//    Integer updateShipping(Shipping shipping);
}
