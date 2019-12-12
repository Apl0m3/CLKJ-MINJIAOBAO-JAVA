package com.lingkj.project.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chenyongsong
 * @date 2019-09-16 11:06:04
 */
@Data
@TableName("commodity_attributes")
public class CommodityAttributes implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 属性名称
     */
    private String name;

    /**
     * 描述
     */
    private String remark;
    /**
     * 父类 attribute id
     */
    private Long parentId;
    /**
     * 分类 1：材料 2：尺寸 3：包装 4：广告 5 内嵌页数
     */
    private Integer category;
    /**
     * 属性类型 0：默认 1：输入 2：选择 3：下拉框
     */
    private Integer type;

    /**
     * 选择类型 0：文字描述 1：图片 2：图文1 3：图文2
     */
    private Integer selectType;
    /**
     * 按数量计算金额
     */
    private BigDecimal quantity;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 供应商 原价
     */
    private BigDecimal factoryPrice;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 0 正常  1 删除
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
    /**
     * 商品id
     */
    private Long commodityId;

}
