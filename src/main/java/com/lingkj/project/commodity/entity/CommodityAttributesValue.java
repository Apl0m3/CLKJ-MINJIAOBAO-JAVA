package com.lingkj.project.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenyongsong
 * @date 2019-09-16 11:06:03
 */
@Data
@TableName("commodity_attributes_value")
public class CommodityAttributesValue implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
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
     * 供应商 原价
     */
    private BigDecimal factoryPrice;
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
    /**
     * 0 正常 1 删除
     */
    private Integer status;
    /**
     *
     */
    private Long createBy;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Long updateBy;
    /**
     *
     */
    private Date updateTime;

}
