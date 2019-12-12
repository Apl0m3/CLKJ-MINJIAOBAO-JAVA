package com.lingkj.project.transaction.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单商品 预计到货时间
 *
 * @author chenyongsong
 * @date 2019-10-22 14:17:44
 */
@Data
@TableName("transaction_commodity_expect")
public class TransactionCommodityExpect implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 商品id
     */
    private Long commodityId;
    /**
     * 预计 n 天后，送达
     */
    private Integer day;
    /**
     * 商品数量上限
     */
    private Integer maxNum;
    /**
     * 额外金额
     */
    private BigDecimal amount;
    /**
     * 供应商 价格
     */
    private BigDecimal factoryPrice;

}
