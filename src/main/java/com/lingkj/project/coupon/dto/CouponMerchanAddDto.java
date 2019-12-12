package com.lingkj.project.coupon.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CouponMerchanAddDto {
    private long couponId;
    private long commodityId;
    private Date createDate;
    private long createBy;
}
