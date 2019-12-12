package com.lingkj.project.api.transaction.dto;


import com.lingkj.project.transaction.entity.TransactionReceivingAddress;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "积分下单实体")
public class ApiIntegralTransactionRecordDto {
    @ApiModelProperty(value = "订单商品id")
    private Long commodityId;
    @ApiModelProperty(value = "订单收货地址")
    private TransactionReceivingAddress address;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "兑换积分商品数量")
    private Integer num;



}
