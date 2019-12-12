package com.lingkj.project.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 优惠券
 *
 * @author chenyongsong
 * @date 2019-09-11 10:38:24
 */
@Data
@TableName("coupon")
public class Coupon implements Serializable {
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

    @TableField(exist = false)
    private Long[] commodityId;
    @TableField(exist = false)
    private List commodityName;

    @TableField(exist = false)
    private Long[] userId;
    @TableField(exist = false)
    private List userName;
    /**
     *
     */
    private Long couponType;
    @TableField(exist = false)
    private String couponTypeName;
    /**
     * 领取开始时间
     */
    private Long receiveStartAt;
    /**
     * 领取结束时间
     */
    private Long receiveEndAt;
    /**
     * 有效期开始时间
     */
    private String useStartAt;
    /**
     * 有效期结束时间
     */
    private String useEndAt;
    /**
     * 是否删除 0：未删除，1：已删除
     */
    private Integer deleteStatus;
    /**
     * 剩余张数
     */
    private Integer surplusNum;
    /**
     * 总张数
     */
    private Integer totalNum;
    /**
     * 优惠劵条件
     */
    private String regulation;
    /**
     * 优惠
     */
    private String result;
    /**
     * 使用说明
     */
    private String instructions;
    /**
     * 国家
     */
    private String country;
    /**
     * 优惠券平台通用 0 否  1是
     */
    private Integer general;

    /**
     * 优惠券是否全部用户 0 否  1是
     */
    private Integer generalPeople;
    /**
     *
     */
    private String image;
    /**
     * 折扣金额 最高限额
     */
    private Long ceiling;
    /**
     *
     */
    private String qrcode;
    /**
     * 条款
     */
    private String terms;
    /**
     * 优惠码发布时间
     */
    private Date createAt;
    /**
     *
     */
    private Long createBy;
    /**
     *
     */
    private Date updateAt;
    /**
     *
     */
    private Long updateBy;

}
