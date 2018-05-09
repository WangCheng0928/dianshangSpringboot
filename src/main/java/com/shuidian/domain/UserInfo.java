package com.shuidian.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class UserInfo {

    @GeneratedValue
    @Id
    private int id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String weixin;

    private String sina;

    private String QQ;

    private String HeadImgUrl;

    private String question;

    private String answer;

    private Integer role;

    private Date createTime;

    private Date updateTime;
}
