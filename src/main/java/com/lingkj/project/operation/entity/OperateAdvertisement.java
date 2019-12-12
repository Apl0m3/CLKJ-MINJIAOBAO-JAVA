package com.lingkj.project.operation.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:26
 */
@Data
@TableName("operate_advertisement")
public class OperateAdvertisement implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 广告图片
     */
    private String image;
    /***
     * banner （1-滚动，2-固定）
     */
    private Integer type;
    /**
     * 跳转方式（1-详情，2-外部跳转，3-板块跳转）
     */
    private Integer jumpMode;

    private Integer position;
    /***
     * 板块跳转类型
     * 1 商品分类 2 积分商城 3 普通商品
     */
    private Integer module;

    /**
     * 广告标题
     */
    private String title;
    /**
     *
     */
    private String content;
    /**
     * 商品信息/商品类型 根据model决定
     */
    private String url;
    /**
     * 0 正常 1 删除
     */
    private Integer status;
    /**
     *
     */
    @TableField(exist=false)
    private String createSysUserName;

    private Long createBy;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    @TableField(exist=false)
    private String updateSysUserName;

    private Long updateBy;
    /**
     *
     */
    private Date updateTime;


    /***
     * 商品id
     */
    @TableField(exist=false)
    private Integer commodityId;
    @TableField(exist=false)
    private Integer banner;
    @TableField(exist=false)
    private String commodityName;

}
