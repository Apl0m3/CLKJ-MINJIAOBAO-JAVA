package com.lingkj.project.transaction.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
@ApiModel(value = "设计师/供应商  返回实体")
public class TransactionCommodityDeliveryRespDto {

    @ApiModelProperty(value = "订单号")
    private String transactionId;
    @ApiModelProperty(value = "订单编号")
    private Long recordId;
    @ApiModelProperty(value = "清单号")
    private Long deliveryId;
    @ApiModelProperty(value = "订单号")
    private Long transactionCommodityId;

    @ApiModelProperty(value = "派送时间")
    private Date createTime;
    @ApiModelProperty(value = "订单状态")
    private Integer status;
    @ApiModelProperty(value = "订单子状态")
    private Integer substate;
    @ApiModelProperty(value = "派送状态")
    private Integer deliveryStatus;
    @ApiModelProperty(value = "顾客姓名")
    private String userName;
    @ApiModelProperty(value = "设计师/供应商 获得的佣金")
    private BigDecimal commission;

    @ApiModelProperty(value = "商品名称")
    private String image;
    @ApiModelProperty(value = "商品名称")
    private String  name;
    @ApiModelProperty(value = "稿件")
    private  String manuscriptUrl;

    private Integer transactionServiceType;
}
