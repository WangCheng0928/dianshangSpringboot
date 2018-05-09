package com.shuidian.repository;

import com.shuidian.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: wangcheng
 * @Date: Created in 11:26 2018/4/3
 */
public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {

    List<Category> findCategoryByParentId(Integer parentId);
    Category findById(Integer categoryId);

    @Transactional
    @Modifying
    @Query("update Category cateory set cateory.name = ?1 where cateory.id = ?2")
    int updateCategoryNameById(String categoryName, Integer cateoryId);
}


