package com.lingkj.project.transaction.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 交易平台配送信息
 *
 * @author chenyongsong
 * @date 2019-07-19 11:59:54
 */
@Data
@TableName("transaction_delivery")
public class TransactionDelivery implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 交易订单号
     */
    private String transactionId;
    /**
     * 配送员
     */
    private Long  deliveryStaffId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private Long createSysUserId;

}
