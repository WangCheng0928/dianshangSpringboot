package com.shuidian.VO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 这里就是最后封装购物车集合的对象了
 * 包含购物车列表、是否全选、总价和一个图片地址
 * @Author: wangcheng
 * @Date: Created in 10:28 2018/4/23
 */
@Data
public class CartVO {

    List<CartProductVO> cartProductVOList;
    private BigDecimal cartTotalPrice;
    private Boolean  allChecked; //是否已经都勾选
    private String imageHost;
}
