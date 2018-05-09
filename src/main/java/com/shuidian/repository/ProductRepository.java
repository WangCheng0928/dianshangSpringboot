package com.shuidian.repository;

import com.shuidian.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * @Author: wangcheng
 * @Date: Created in 11:22 2018/4/10
 */
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    @Transactional
    @Modifying
    @Query("update Product product set product.status = ?1 where product.id = ?2")
    int updateStatusById(Integer status, Integer productId);
}
