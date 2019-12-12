package com.lingkj.project.cart.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenyongsong
 * @date 2019-10-22 15:27:26
 */
@Data
@TableName("cart_number_attributes")
public class CartNumberAttributes implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * cart id
     */
    private Long cartId;
    /**
     * 属性名称
     */
    private String name;
    /**
     * 	值
     */
    private Integer num;


}
