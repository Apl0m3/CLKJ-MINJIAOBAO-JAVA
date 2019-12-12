package com.lingkj.project.transaction.dto;

import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "商品分配信息返回")
public class TransactionDeliveryRespDto {
    private Long id;
    private String name;
    private String userName;
    private String avatar;
    private BigDecimal commission;
}
