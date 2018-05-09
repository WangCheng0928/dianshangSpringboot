package com.shuidian.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 首先购物车自身的属性，先不考虑产品，把产品当作一个黑盒子，购物车需要的属性
 * 包括购物车ID、购物车产品数量、是否被选中、用户ID、产品ID
 * 创建时间、更新时间
 * @Author: wangcheng
 * @Date: Created in 10:04 2018/4/23
 */

@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer quantity;
    private Integer checked;
    private Date createTime;
    private Date updateTime;
}
