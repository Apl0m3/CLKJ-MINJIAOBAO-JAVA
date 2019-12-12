package com.lingkj.project.api.transaction.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * TransactionCommodityDto
 *
 * @author chen yongsong
 * @className TransactionCommodityDto
 * @date 2019/9/20 15:25
 */
@ApiModel("订单商品请求实体类")
@Data
public class ApiTransactionCommodityReqDto {
    @ApiModelProperty("购物车id")
    private Long cartId;
    @ApiModelProperty("商品id")
    private Long commodityId;
    @ApiModelProperty(value = "商品属性")
    private List<ApiTransactionAttributeDto> attribute;
    @ApiModelProperty(value = "商品预计到货和时间")
    private ApiTransactionLadderExpectDto ladderExpect;
    @ApiModelProperty(value = "输入或服装 商品数量")
    private List<ApiTransactionCommodityNumberAttributeDto> numberAttributesList;
    @ApiModelProperty(value = "设计师状态 0 默认稿件  1 上传稿件  2 设计师设计稿件 ")
    private Integer designStatus;
    @ApiModelProperty(value = "稿件")
    private String manuscriptUrl;
    @ApiModelProperty(value = "备注")
    private String remark;

    private BigDecimal amount;
    private Integer totalQuantity;

    private BigDecimal factoryPrice;
}
