package com.lingkj.project.coupon.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CouponUserAddDto {
    private long userId;
    private long couponId;
    private Date createTime;
    private String startTime;
    private String expireTime;
}
