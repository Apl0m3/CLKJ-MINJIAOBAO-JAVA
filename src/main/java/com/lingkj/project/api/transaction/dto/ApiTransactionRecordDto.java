package com.lingkj.project.api.transaction.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * TransactionCommodityDto
 *
 * @author chen yongsong
 * @className TransactionCommodityDto
 * @date 2019/9/20 15:25
 */
@ApiModel("订单实体类")
@Data
public class ApiTransactionRecordDto {

    @ApiModelProperty("订单id")
    private Long id;
    @ApiModelProperty("订单号")
    private String transactionId;
    @ApiModelProperty("金额")
    private BigDecimal amount;
    @ApiModelProperty("订单状态")
    private Integer status;
    @ApiModelProperty("订单类型  1 普通订单  2 积分订单")
    private Integer type;
    @ApiModelProperty("新信息消息展示")
    private Integer messageStatus = 0;
    @ApiModelProperty("下单时间")
    private Date createTime;
    @ApiModelProperty("订单商品信息")
    private List<ApiTransactionCommodityRespDto> commodityDto;
    @ApiModelProperty("订单收货地址信息")
    private ApiTransactionReceivingAddressDto receivingAddressDto;
    @ApiModelProperty("订单发票信息")
    private ApiTransactionInvoiceDto invoiceDto;
    private Long paymentMethodId;
    private String methodPay;
    private BigDecimal rateAmount;
    private BigDecimal rate;
    private BigDecimal discount;
    private BigDecimal discountAmount;
    private BigDecimal couponAmount;
}
