package com.lingkj.project.api.operation.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * ApiOperatePaymentDto
 *
 * @author chen yongsong
 * @className ApiOperatePaymentDto
 * @date 2019/10/10 17:48
 */
@Data
@ApiModel(value = "支付方式实体")
public class ApiOperatePaymentDto {
    private Long id;
    private String image;
    private String name;
    private String key;
    private Integer paymentType;
}
