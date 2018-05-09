package com.shuidian.VO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: wangcheng
 * @Date: Created in 18:11 2018/5/3
 */
@Data
public class OrderProductVo {
    private List<OrderItemVo> orderItemVoList;
    private BigDecimal productTotalPrice;
    private String imageHost;
}
