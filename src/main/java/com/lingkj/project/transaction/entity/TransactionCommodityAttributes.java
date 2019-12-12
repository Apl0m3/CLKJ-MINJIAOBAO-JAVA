package com.lingkj.project.transaction.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单 商品属性
 *
 * @author chenyongsong
 * @date 2019-10-22 14:55:54
 */
@Data
@TableName("transaction_commodity_attributes")
public class TransactionCommodityAttributes implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * transaction Commodity id
     */
    private Long commodityId;
    /**
     * 属性名称
     */
    private String name;
    /**
     * 属性分类 1：材料 2：尺寸 3：包装
     */
    private Integer category;
    /**
     * 0：默认 1：输入 2：选择
     */
    private Integer type;
    /**
     * 0：文字 2：图片 3：图文
     */
    private Integer selectType;

    /**
     * '输入类型的值'
     */
    private String inputValue;
    /**
     * 按数量计价
     */
    private BigDecimal quantity;
    private BigDecimal amount;
    /**
     * 供应商 价格
     */
    private BigDecimal factoryPrice;
    /**
     * 属性值名称
     */
    private String valueName;
    /**
     * 属性值 长度
     */
    private Double valueLength;
    /**
     * 属性值 宽度
     */
    private Double valueWidth;
    /**
     * 属性值 自定义
     */
    private Integer valueSizeCustomizable;
    /**
     * 金额
     */
    private BigDecimal valueAmount;
    /**
     * 供应商 价格
     */
    private BigDecimal valueFactoryPrice;
    /**
     * 属性图片
     */
    private String valueUrl;

}
