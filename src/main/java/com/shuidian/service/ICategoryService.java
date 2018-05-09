package com.shuidian.service;

import com.shuidian.domain.Category;
import com.shuidian.util.ServerResponse;

import java.io.IOException;
import java.util.List;

/**
 * @Author: wangcheng
 * @Date: Created in 10:09 2018/4/3
 */
public interface ICategoryService {
    ServerResponse addCategory(String categoryName, Integer parentId);
    ServerResponse updateCategory(Integer categoryId, String categoryName);
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);
    ServerResponse<List<Integer>> getChildrenDeepCategory(Integer categoryId);
//    ServerResponse<List<Category>> contextLoads();
}
