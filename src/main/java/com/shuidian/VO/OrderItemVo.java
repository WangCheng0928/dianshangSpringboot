package com.shuidian.VO;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: wangcheng
 * @Date: Created in 18:11 2018/5/3
 */
@Data
public class OrderItemVo {
    private Long orderNo;

    private Integer productId;

    private String productName;
    private String productImage;

    private BigDecimal currentUnitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;

    private String createTime;
}
