package com.lingkj.project.transaction.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单商品物流
 *
 * @author chenyongsong
 * @date 2019-10-21 18:11:22
 */
@Data
@TableName("transaction_commodity_logistics")
public class TransactionCommodityLogistics implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final Integer type_common=1;
    public static final Integer type_return=2;
    public static final Integer type_exchange=3;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     *
     */
    private Long recordId;
    /**
     *
     */
    private Long transactionCommodityId;
    /**
     * 物流号
     */
    private String deliveryNum;
    /**
     *
     */
    private Date createTime;
    private Long userId;
    /**
     * 1 供应商发货物流  2 用户退换货物流 3 供应商换货物流
     */
    private Integer type;


}
