package com.lingkj.project.api.cart.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * ApiCartAttributesDto
 *
 * @author chen yongsong
 * @className ApiCartAttributesDto
 * @date 2019/10/22 18:11
 */
@Data
public class ApiCartAttributesDto {
    /**
     *
     */
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
     * 属性分类 1：材料 2：尺寸 3：包装
     */
    private Integer category;
    /**
     * 0：默认 1：输入 2：选择
     */
    private Integer type;
    /**
     * 输入类型 值
     */
    private String inputValue;
    /**
     * 0：文字 2：图片 3：图文
     */
    private Integer selectType;
    /**
     * 按数量计价
     */
    private BigDecimal quantity;
    private BigDecimal amount;
    /**
     * 供应商价格
     */
    private BigDecimal factoryPrice;


    /**
     * 属性值id
     */
    private Long valueId;
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
     * 供应商价格
     */
    private BigDecimal valueFactoryPrice;
    /**
     * 属性图片
     */
    private String valueUrl;
}
