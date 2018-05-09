package com.shuidian.controller.user;

import com.github.pagehelper.PageInfo;
import com.shuidian.VO.ProductDetailVo;
import com.shuidian.service.IProductService;
import com.shuidian.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author: wangcheng
 * @Date: Created in 9:47 2018/4/14
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @RequestMapping("/getProductDetail")
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId){
        return iProductService.getProductDetail(productId);
    }

    @RequestMapping("/getProductList")
    public ServerResponse<PageInfo> getProductList(@RequestParam(value = "keyword", required = false)String keyword,
                                                   @RequestParam(value = "categoryId", required = false)Integer categoryId,
                                                   @RequestParam(value = "pageNum", defaultValue = "1")int pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "10")int pageSize,
                                                   @RequestParam(value = "orderBy", required = false)String orderBy){
        try {
            return iProductService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
        }catch (Exception e) {
            return ServerResponse.createByErrorMessage("查找失败");
        }
    }
}
