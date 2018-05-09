package com.shuidian.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.shuidian.VO.ProductDetailVo;
import com.shuidian.VO.ProductListVo;
import com.shuidian.domain.Category;
import com.shuidian.domain.Product;
import com.shuidian.enums.Const;
import com.shuidian.enums.ResponseCode;
import com.shuidian.myjpasql.Criteria;
import com.shuidian.myjpasql.Restrictions;
import com.shuidian.repository.CategoryRepository;
import com.shuidian.repository.ProductRepository;
import com.shuidian.service.ICategoryService;
import com.shuidian.service.IProductService;
import com.shuidian.util.DateTimeUtil;
import com.shuidian.util.PropertiesUtil;
import com.shuidian.util.ServerResponse;
import com.shuidian.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: wangcheng
 * @Date: Created in 10:55 2018/4/10
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ICategoryService iCategoryService;

    public ServerResponse saveOrUpdateProduct(Product product){
        if (product != null){
            if(StringUtils.isNotBlank(product.getSubImages())){
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length > 0){
                    product.setMainImage(subImageArray[0]);
                }
            }
            if (product.getId() != null){
                try {
                    Product currentProduct = productRepository.findOne(product.getId());
                    currentProduct.setSubtitle(product.getSubtitle());
                    currentProduct.setSubImages(product.getSubImages());
                    currentProduct.setStock(product.getStock());
                    currentProduct.setStatus(product.getStatus());
                    currentProduct.setPrice(product.getPrice());
                    currentProduct.setName(product.getName());
                    currentProduct.setDetail(product.getDetail());
                    currentProduct.setCategoryId(product.getCategoryId());
                    currentProduct.setMainImage(product.getMainImage());
                    currentProduct.setUpdateTime(new Date());
                    productRepository.save(currentProduct);
                    return ServerResponse.createBySuccessMessage("更新产品成功");
                }catch (Exception e){
                    return ServerResponse.createByErrorMessage("更新产品失败");
                }
            }else {
                try {
                    productRepository.save(product);
                    return ServerResponse.createBySuccessMessage("新增产品成功");
                }catch (Exception e){
                    return ServerResponse.createBySuccessMessage("新增产品失败");
                }
            }
        }
        return ServerResponse.createByErrorMessage("新增或更新产品参数不正确");
    }

    public ServerResponse<String> setSaleStatus(Integer productId, Integer status){
        if (productId == null || status == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGA_ARGUMENT.getCode(), ResponseCode.ILLEGA_ARGUMENT.getDesc());
        }
        int rowCount = productRepository.updateStatusById(status, productId);
        if (rowCount > 0){
            return ServerResponse.createBySuccess("修改产品销售状态成功");
        }
        return ServerResponse.createByErrorMessage("修改产品销售状态失败");
    }

    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId){
        if (productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGA_ARGUMENT.getCode(), ResponseCode.ILLEGA_ARGUMENT.getDesc());
        }
        Product product = productRepository.findOne(productId);
        if (product == null){
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    private ProductDetailVo assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());
        //imageHost
        productDetailVo.setImageHost(PropertiesUtil.getProperty(Const.FTP_PREFIX_KEY,Const.FTP_PREFIX_VALUE));
        //parentCategoryId
        Category category = categoryRepository.findById(product.getCategoryId());
        if (category == null){
            productDetailVo.setParentCategoryId(0);
        }else {
            productDetailVo.setParentCategoryId(category.getParentId());
        }
        //createTime
        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        //updateTime
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }

    public ServerResponse<PageInfo> getProductList(Integer pageNum, Integer pageSize){
        //startPage -- start
        //填充自己的sql查询逻辑
        //pageHelper-收尾
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productRepository.findAll();
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product productItem : productList){
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        //分页后每一页的数据
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    private ProductListVo assembleProductListVo(Product product){
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty(Const.FTP_PREFIX_KEY,Const.FTP_PREFIX_VALUE));
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        return productListVo;
    }

    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId, Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        Criteria<Product> productCriteria = new Criteria<>();
        productCriteria.add(Restrictions.like("name", productName, true));
        productCriteria.add(Restrictions.eq("id", productId, true));
        List<Product> productList = productRepository.findAll(productCriteria);
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product productItem : productList){
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId){
        if (productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGA_ARGUMENT.getCode(), ResponseCode.ILLEGA_ARGUMENT.getDesc());
        }
        Product product = productRepository.findOne(productId);
        if (product == null){
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()){
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy) throws Exception{
        if (StringUtils.isBlank(keyword) && categoryId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGA_ARGUMENT.getCode(), ResponseCode.ILLEGA_ARGUMENT.getDesc());
        }
        //用来列出所有该分类的子分类
        List<Integer> categoryIdList = new ArrayList<Integer>();
        if (categoryId != null){
            Category category = categoryRepository.findById(categoryId);
            if (category == null && StringUtils.isBlank(keyword)){
                // 没有该分类，并且还没有关键字，这个时候返回一个空的结果集，不报错
                PageHelper.startPage(pageNum, pageSize);
                List<ProductListVo> productListVoList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVoList);
                return ServerResponse.createBySuccess(pageInfo);
            }
              categoryIdList = iCategoryService.getChildrenDeepCategory(category.getId()).getData();
        }
        PageHelper.startPage(pageNum, pageSize);
        //排序处理
        if (StringUtils.isNotBlank(orderBy)){
            if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
            }
        }
        //模糊查询
        Criteria<Product> productCriteria = new Criteria<>();
        productCriteria.add(Restrictions.eq("status", 1, true));
        productCriteria.add(Restrictions.like("name", StringUtils.isBlank(keyword)?null:keyword, true));
        if (categoryIdList.size() == 0 ){
            productCriteria.add(Restrictions.eq("categoryId", null, true));
        }else {
            for (int i = 0; i < categoryIdList.size(); i++){
                productCriteria.add(Restrictions.eq("categoryId", categoryIdList.get(i), true));
            }
        }
        List<Product> productList = productRepository.findAll(productCriteria);
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product product : productList){
            ProductListVo productListVo = assembleProductListVo(product);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
