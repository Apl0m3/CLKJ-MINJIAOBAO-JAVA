package com.lingkj.project.user.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserAccountLogaAndTransactionIdDto {
    /**
     * 交易订单号
     */
    private String transactionId;
    /**
     * 改变金额
     */
    private BigDecimal changeValue;
    /**
     * 1 进账，2 出账
     */
    private Integer type;
    /**
     *
     */
    private Date createTime;
    /**
     * 备注
     */
    private String remark;
    /**
     *
     */
    private String name;

    private Integer settlementStatus;
    /**
     * 商品名称
     */
    private String commodityName;
}
