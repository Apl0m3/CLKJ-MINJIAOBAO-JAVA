package com.lingkj.project.transaction.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 管理人员 分配订单给  设计师/供应商
 *
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
@Data
@TableName("transaction_commodity_delivery")
public class TransactionCommodityDelivery implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Integer TYPE_DESIGNER = 1;
    public static final Integer TYPE_SUPPLIER = 2;

    /**
     * 状态  0 待接收  1 已接收  2 拒绝接受
     */
    public static final Integer STATUS_WAIT =0;
    public static final Integer STATUS_ACCEPTED = 1;
    public static final Integer STATUS_REFUSE = 2;
    /**
     * 结算状态
     */
    public static final Integer SETTLEMENT_STATUS_NO =0;
    public static final Integer SETTLEMENT_STATUS_YES = 1;
    public static final Integer SETTLEMENT_STATUS_SETTLED = 2;


    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 设计师 id/供货商 id
     */
    private Long userId;
    /**
     * 类型 1 设计师  2 供货商
     */
    private Integer type;
    /**
     * 派送 订单产品
     */
    private Long transactionCommodityId;
    /**
     * 订单id
     */
    private Long recordId;
    /**
     * 派送时间
     */
    private Date createTime;
    /**
     * 状态  0 待接收  1 已接收  2 拒绝接受
     */
    private Integer status;
    /**
     * 0 不可结算 1 可结算  2已结算
     */
    private Integer settlementStatus;
    /**
     * 拒绝原因
     */
    private String reason;

}
