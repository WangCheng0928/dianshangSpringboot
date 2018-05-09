package com.shuidian.service;

import com.github.pagehelper.PageInfo;
import com.shuidian.VO.ProductDetailVo;
import com.shuidian.domain.Product;
import com.shuidian.util.ServerResponse;

import java.io.IOException;

/**
 * @Author: wangcheng
 * @Date: Created in 10:54 2018/4/10
 */
public interface IProductService {

    ServerResponse saveOrUpdateProduct(Product product);
    ServerResponse<String> setSaleStatus(Integer productId, Integer status);
    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);
    ServerResponse<PageInfo> getProductList(Integer pageNum, Integer pageSize);
    ServerResponse<PageInfo> searchProduct(String productName, Integer productId, Integer pageNum, Integer pageSize);
    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);
    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy) throws IOException, Exception;
}
