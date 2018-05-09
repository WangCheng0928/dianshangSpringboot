package com.shuidian.service.Impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.shuidian.domain.Category;
import com.shuidian.myjpasql.Criteria;
import com.shuidian.myjpasql.Restrictions;
import com.shuidian.repository.CategoryRepository;
import com.shuidian.service.ICategoryService;
import com.shuidian.util.ServerResponse;
import com.shuidian.util.StringUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: wangcheng
 * @Date: Created in 10:09 2018/4/3
 */
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    public ServerResponse addCategory(String categoryName, Integer parentId){
        try {
            if (parentId == null || StringUtils.isBlank(categoryName)){
                return ServerResponse.createByErrorMessage("添加品类参数错误");
            }
            Category category = new Category();
            category.setName(categoryName);
            category.setParentId(parentId);
            category.setStatus(true); //这个分类是可用的
            categoryRepository.save(category);
            return ServerResponse.createBySuccess("添加品类成功");
        }catch (Exception e){
            System.out.println(e.toString());
            return ServerResponse.createByErrorMessage("添加品类失败");
        }
    }

    public ServerResponse updateCategory(Integer categoryId, String categoryName){
        if (categoryId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("添加品类参数错误");
        }
        int rowCount = categoryRepository.updateCategoryNameById(categoryName, categoryId);
        if (rowCount > 0){
            return ServerResponse.createBySuccess("更新品类名字成功");
        }
        return ServerResponse.createByErrorMessage("更新品类名字失败");
    }

    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId){
        List<Category> categoryList = categoryRepository.findCategoryByParentId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)){
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    public ServerResponse<List<Integer>> getChildrenDeepCategory(Integer categoryId){
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet, categoryId);
        List<Integer> categoryIdList = Lists.newArrayList();
        if (categoryId != null){
            for (Category categoryItem : categorySet){
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }

    //递归算法，算出子节点
    private Set<Category> findChildCategory(Set<Category> categorySet, Integer categoryId){
        Category category = categoryRepository.findById(categoryId);
        if (category != null){
            categorySet.add(category);
        }
        //查找子节点
        List<Category> categoryList = categoryRepository.findCategoryByParentId(categoryId);
        for (Category categoryItem : categoryList){
            findChildCategory(categorySet, categoryItem.getId());
        }
        return categorySet;
    }

//    public ServerResponse<List<Category>> contextLoads(){
//        Criteria<Category> categoryCriteria = new Criteria<>();
//        categoryCriteria.add(Restrictions.like("name", "情感", true));
//        categoryCriteria.add(Restrictions.eq("parentId", "101", true));
//        List<Category> categoryList = categoryRepository.findAll(categoryCriteria);
//        for (Category category : categoryList){
//            System.out.println(category);
//        }
//        return ServerResponse.createBySuccess(categoryList);
//    }

}
