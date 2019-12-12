package com.lingkj.project.transaction.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:25
 */
@Data
@TableName("transaction_record")
public class TransactionRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Integer integral_use_status_no = 0;
    public static final Integer integral_use_status_yes = 1;


    /**
     * 1-普通商品订单  2-积分商品订单
     */
    public static final Integer type_ordinary=1;
    public static final Integer type_integral=2;
    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 订单号
     */
    private String transactionId;
    /**
     * 第三方订单号
     */
    private String tripartiteTransactionId;

    /**
     * 买家留言
     */
    private String remarks;
    /**
     * 用户id
     */
    private Long userId;


    /**
     * 物流方式id
     */
    private Long deliveryMethodId;
    /**
     * 支付方式id
     */
    private Long paymentMethodId;
    /**
     * 支付总金额
     */
    private BigDecimal totalAmount;
    /**
     * 实际支付金额
     */
    private BigDecimal amount;
    /**
     * 费率 金额
     */
    private BigDecimal rateAmount;
    /**
     * 费率
     */
    private BigDecimal rate;
    /**
     * 优惠劵id
     */
    private Long couponMapId;
    /**
     * 优惠金额
     */
    private BigDecimal couponAmount;
    private BigDecimal discount;
    private BigDecimal discountAmount;
    /**
     * 1-普通商品订单  2-积分商品订单
     */
    private Integer type;
    /**
     * 订单状态
     */
    private Integer status;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     *
     */
    private Date createTime;


}
