package com.lingkj.project.cart.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单商品数量阶梯价
 *
 * @author chenyongsong
 * @date 2019-10-22 15:27:25
 */
@Data
@TableName("cart_ladder")
public class CartLadder implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     *
     */
    private Long cartId;
    /**
     * commodity ladder id
     */
    private Long ladderId;
    /**
     * 数量
     */
    private Integer num;
    /**
     * 折扣
     */
    private BigDecimal discount;

}
