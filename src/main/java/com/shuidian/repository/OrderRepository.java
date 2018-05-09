package com.shuidian.repository;

import com.shuidian.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: wangcheng
 * @Date: Created in 16:55 2018/4/26
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findByUserIdAndOrderNo(Integer userId, Long orderNo);
    Order findByOrderNo(Long orderNo);
    List<Order> findByUserId(Integer userId);
}

