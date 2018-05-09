package com.shuidian.repository;

import com.shuidian.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: wangcheng
 * @Date: Created in 10:54 2018/5/3
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    @Query("select orderItem from OrderItem orderItem where orderItem.userId = ?1 and orderItem.orderNo = ?2")
    List<OrderItem> getByOrderNoAndUserId(Integer userId, Long orderNo);

    List<OrderItem> findByUserIdAndOrderNo(Integer userId, Long orderNo);
    List<OrderItem> findByOrderNo(Long orderNo);

}
