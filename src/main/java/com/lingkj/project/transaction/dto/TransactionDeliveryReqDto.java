package com.lingkj.project.transaction.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * TransactionDeliveryReqDto
 *
 * @author chen yongsong
 * @className TransactionDeliveryReqDto
 * @date 2019/10/14 10:49
 */

@Data
@ApiModel(value = "查询商品分配方向")
public class TransactionDeliveryReqDto {
    @ApiModelProperty(value = "订单商品id")
    private Long transactionCommodityId;
    @ApiModelProperty(value = "分配类型 1 设计师  2 供应商")
    private Integer type;
    @ApiModelProperty(value = "派送给设计师/供应商")
    private Long userId;
}
