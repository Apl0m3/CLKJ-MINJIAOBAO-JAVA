package com.lingkj.project.api.transaction.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * ApiTransactionAttributeDto
 *
 * @author chen yongsong
 * @className ApiTransactionAttributeDto
 * @date 2019/10/9 16:51
 */
@Data
public class ApiTransactionAttributeValueDto {
    /**
     *
     */
    private Long id;
    /**
     * 规格id
     */
    private Long commodityAttributesId;
    /**
     * 名称
     */
    private String name;
    /**
     * 长度
     */
    private Double length;

    /**
     * 宽度
     */
    private Double width;
    /**
     * 尺寸是否自定义
     * 0：不自定义 1：自定义
     */
    private Integer sizeCustomizable;
    /**
     * 描述（显示在底部）
     */
    private String remark;
    /**
     * 加价
     */
    private BigDecimal amount;
    /**
     * 图片地址
     */
    private String url;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 父类 attribute value id
     */
    private Long parentId;
    /**
     * 是否推荐
     * 0：不推荐 1：推荐
     */
    private Integer recommend;
}
