package com.lingkj.project.api.transaction.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * ApiConfirmPaymentDto
 *
 * @author chen yongsong
 * @className ApiConfirmPaymentDto
 * @date 2019/10/11 15:30
 */
@Data
@ApiModel(value = "确认订单支付")
public class ApiConfirmPaymentDto {
    private String transactionId;
    private Long paymentMethodId;
}
