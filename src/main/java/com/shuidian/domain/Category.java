package com.shuidian.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: wangcheng
 * @Date: Created in 10:22 2018/4/3
 */
@Data
@Entity
public class Category {

    @GeneratedValue
    @Id
//    @GeneratedValue(generator = "snowFlakeId")
//    @GenericGenerator(name = "snowFlakeId", strategy = "com.shuidian.util.SnowflakeId")
    private Integer id;

    private Integer parentId;

    private String name;

    private Boolean status;

    private Integer sortOrder;

    private Date createTime;

    private Date updateTime;
}
