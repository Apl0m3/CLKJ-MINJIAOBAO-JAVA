package com.lingkj.project.transaction.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenyongsong
 * @date 2019-08-06 11:43:36
 */
@Data
@TableName("transaction_refund")
public class TransactionRefund implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Integer type_refunding = 0;
    public static final Integer type_refund = 1;
    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 订单号(申请退款订单)
     */
    private String transactionId;
    /**
     * wx 退款订单号
     */
    private String wxRefundTransactionId;
    /**
     * 退款完成时间
     */
    private Date refundTime;
    /**
     * 退款状态（0 退款中  1 已退款）
     */
    private Integer status;
    /**
     * 申请退款时间
     */
    private Date createTime;
    /**
     * 退款金额
     */
    private BigDecimal returnAmount;
    /**
     * 退款积分
     */
    private Integer returnIntegral;

}
