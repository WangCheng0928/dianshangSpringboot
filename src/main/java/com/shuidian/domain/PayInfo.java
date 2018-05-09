package com.shuidian.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: wangcheng
 * @Date: Created in 17:05 2018/4/26
 */
@Data
@Entity
public class PayInfo {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer userId;
    private Long orderNo;
    private Integer payPlatform;
    private String platformNumber;
    private String platformStatus;
    private Date createTime;
    private Date updateTime;

}
