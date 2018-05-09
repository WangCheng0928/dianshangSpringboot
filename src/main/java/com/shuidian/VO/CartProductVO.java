package com.shuidian.VO;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 这里封装了购物车和产品，这个时候就把产品黑盒子打开，看看产品黑盒子里有哪些属性
 * 首先包含了购物车的前四个属性，没有checked，因为这里有产品的勾选，所以checked属性封装到购物车列表里
 * 产品的属性就要看product里的属性了 就包含了下面的属性
 * @Author: wangcheng
 * @Date: Created in 10:21 2018/4/23
 */
@Data
public class CartProductVO {

    //结合了产品和购物车的字段
    private Integer id;
    private Integer userId;
    private Integer  productId;
    private Integer quantity;  //购物车中此商品的数量
    private String productName;
    private String productSubtitle;
    private String productMainImage;
    private BigDecimal productPrice;
    private Integer productStatus;
    private BigDecimal productTotalPrice;
    private Integer productStock;
    private Integer productChecked; //此商品是否勾选
    private String limitQuantity; // 限制数量的一个返回结果
}
