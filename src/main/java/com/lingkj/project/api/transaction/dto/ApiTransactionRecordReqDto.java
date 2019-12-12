package com.lingkj.project.api.transaction.dto;

import com.lingkj.project.cart.entity.CartNumberAttributes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * ApiTransactionRecordReqDto
 *
 * @author chen yongsong
 * @className ApiTransactionRecordReqDto
 * @date 2019/10/9 15:16
 */
@Data
@ApiModel(value = "下单实体")
public class ApiTransactionRecordReqDto {
    @ApiModelProperty(value = "订单商品")
    private List<ApiTransactionCommodityReqDto> commodityList;
    @ApiModelProperty(value = "订单收货地址")
    private ApiTransactionReceivingAddressDto address;
    @ApiModelProperty(value = "订单发票信息")
    private ApiTransactionInvoiceDto invoice;
    @ApiModelProperty(value = "优惠券")
    private Long couponMapId;
    @ApiModelProperty(value = "手动输入数量")
    private Integer totalQuantity;
    @ApiModelProperty(value = "服装数量")
    private CartNumberAttributes cartNumberAttributes;
    @ApiModelProperty(value = "备注")
    private String remark;

    private BigDecimal totalAmount;
}
