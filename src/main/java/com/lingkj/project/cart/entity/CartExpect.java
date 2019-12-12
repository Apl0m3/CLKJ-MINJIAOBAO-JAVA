package com.lingkj.project.cart.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单商品 预计到货时间
 *
 * @author chenyongsong
 * @date 2019-10-22 15:27:26
 */
@Data
@TableName("cart_expect")
public class CartExpect implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 购物车
     */
    private Long cartId;
    /**
     * commodity expect id
     */
    private Long expectId;
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
