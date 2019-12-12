package com.lingkj.project.transaction.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * TransactionRecordRespDto
 *
 * @author chen yongsong
 * @className TransactionRecordRespDto
 * @date 2019/7/11 14:01
 */
@ApiModel("订单列表返回")
@Data
public class TransactionRecordRespDto {
    private Long id;
    private  Long trCommodityId;
    private String transactionId;
    private Date expectTime;
    private Date createTime;
    private Long paymentMethodId;
    private BigDecimal amount;
    private Integer type;
    private Integer status;
    private BigDecimal totalAmount;
    private BigDecimal couponAmount;
    private BigDecimal rate;
    private BigDecimal rateAmount;
    private BigDecimal discount;
    private BigDecimal discountAmount;


    /**
     * 用户信息
     */
    private String userName;

    /**
     * 订单商品信息
     */
    private String commodityName;
    private String commodityImage;
    private Integer branchStatus;
    private Integer substate;

    private  Integer transactionServiceType;

}
