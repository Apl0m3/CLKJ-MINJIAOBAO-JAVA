package com.lingkj.project.api.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * ApiUserCouponMapDto
 *
 * @author chen yongsong
 * @className ApiUserCouponMapDto
 * @date 2019/9/30 18:09
 */
@ApiModel("查询用户优惠")
@Data
public class ApiUserCouponMapReqDto {
    private Long commodityId;
    private BigDecimal amount = BigDecimal.ZERO;
}
