package com.shuidian.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: wangcheng
 * @Date: Created in 19:40 2018/4/23
 */
@Entity
@Data
public class Shipping {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer userId;
    private String receiverName;
    private String receiverPhone;
    private String receiverMobile;
    private String receiverProvince;
    private String receiverCity;
    private String receiverDistrict;
    private String receiverAddress;
    private String receiverZip;
    private Date createTime;
    private Date updateTime;
}
