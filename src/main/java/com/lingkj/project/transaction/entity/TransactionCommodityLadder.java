package com.lingkj.project.transaction.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单商品数量阶梯价
 *
 * @author chenyongsong
 * @date 2019-10-22 14:17:43
 */
@Data
@TableName("transaction_commodity_ladder")
public class TransactionCommodityLadder implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     *
     */
    private Long commodityId;
    /**
     * 数量
     */
    private Integer num;
    /**
     * 折扣
     */
    private BigDecimal discount;

}
