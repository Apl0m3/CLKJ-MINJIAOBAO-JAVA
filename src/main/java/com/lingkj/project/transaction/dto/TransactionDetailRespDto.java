package com.lingkj.project.transaction.dto;

import com.lingkj.project.api.transaction.dto.ApiTransactionInvoiceDto;
import com.lingkj.project.api.transaction.dto.ApiTransactionReceivingAddressDto;
import com.lingkj.project.user.dto.ReceivingAddressRespDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * TransactionDetailRespDto
 *
 * @author chen yongsong
 * @className TransactionDetailRespDto
 * @date 2019/7/11 17:37
 */
@ApiModel("订单详情返回")
@Data
public class TransactionDetailRespDto {
    @ApiModelProperty("订单号")
    private String transactionId;
    @ApiModelProperty("订单号")
    private Long id;
    @ApiModelProperty("支付金额")
    private String amount;
    @ApiModelProperty("支付总金额")
    private String totalAmount;
    @ApiModelProperty("优惠劵抵扣金额")
    private String couponAmount;
    private BigDecimal rate;
    private BigDecimal rateAmount;
    private BigDecimal discount;
    private BigDecimal discountAmount;
    @ApiModelProperty("优惠劵id")
    private Long couponId;
    @ApiModelProperty("订单类型")
    private Integer type;
    @ApiModelProperty("订单状态")
    private Integer status;
    @ApiModelProperty("订单商品")
    private List<TransactionCommodityRespDto> list;
    @ApiModelProperty("收货地址")
    private ApiTransactionReceivingAddressDto address;
    @ApiModelProperty("发票信息")
    private ApiTransactionInvoiceDto invoice;
    @ApiModelProperty("支付方式")
    private Long paymentMethodId;
    @ApiModelProperty("下单时间")
    private Date createTime;
    @ApiModelProperty("留言")
    private String remarks;

    /**
     * 物流号
     */
    private String deliveryLogistics; //发货物流
    private  String userReturnLogistics;//用户退换货物流
    private  String exchangeLogistics;//供应商换货物流
    /**
     * 用户信息
     */
    private String userName;
    private String name;



}
