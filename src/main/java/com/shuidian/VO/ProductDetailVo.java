package com.shuidian.VO;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: wangcheng
 * @Date: Created in 15:28 2018/4/10
 * 使用VO时为了向前端展示需要展示的字段，而不需要把所有的实体类字段都像前段展示
 */
@Data
public class ProductDetailVo {

    private Integer  id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private String subImages;
    private String detail;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
    private String createTime;
    private String updateTime;


    private String imageHost;
    private Integer parentCategoryId;
}
