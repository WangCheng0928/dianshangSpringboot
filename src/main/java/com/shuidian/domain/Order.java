package com.shuidian.domain;

import io.swagger.models.auth.In;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: wangcheng
 * @Date: Created in 16:54 2018/4/26
 */
@Data
@Entity
public class Order {

    @Id
    @GeneratedValue
    private Integer id;
    private Long orderNo;
    private Integer shippingId;
    private Integer userId;
    private BigDecimal payment;
    private Integer paymentType;
    private Integer postage;
    private Integer status;
    private Date paymentTime;
    private Date sendTime;
    private Date endTime;
    private Date closeTime;
    private Date createTime;
    private Date updateTime;
}
