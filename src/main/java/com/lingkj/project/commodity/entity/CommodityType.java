package com.lingkj.project.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:26
 */
@Data
@TableName("commodity_type")
public class CommodityType implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     *
     */
    private String name;
    /**
     * 分类图片（一级分类）
     */
    private String image;
    /**
     *
     */
    private Long parentId;
    /**
     * 需要设计师时用户支付佣金
     */
    private BigDecimal commission;
    /**
     * 分类等级
     * 1级分类,2级分类,3级分类
     */
    private Integer level;
    /**
     * 0 正常 1 删除
     */
    private Integer status;
    /**
     *
     */
    @TableField(exist = false)
    private String createSysUserName;
    private Long createSysUserId;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    @TableField(exist = false)
    private String updateSysUserName;
    private Long updateSysUserId;
    /**
     *
     */
    private Date updateTime;


}
