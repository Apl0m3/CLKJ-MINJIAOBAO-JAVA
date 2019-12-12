package com.lingkj.project.user.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserCouponAndCouponDto {
    private  String name;
    private Date createTime;
    private String couponTypeName;
    private String image;
    private String instructions;
}
