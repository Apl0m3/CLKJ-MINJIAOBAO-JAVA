package com.lingkj.project.api.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * ApiCouponDto
 *
 * @author chen yongsong
 * @className ApiCouponDto
 * @date 2019/9/30 18:12
 */
@Data
public class ApiCouponDto {
    private Long id;
    private Long couponId;
    private String name;
    private Integer couponType;
    private String image;
    private String regulation;
    /**
     * 优惠
     */
    private String result;
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal couponAmount;
}
