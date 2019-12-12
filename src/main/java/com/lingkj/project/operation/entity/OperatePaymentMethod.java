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
@TableName("operate_payment_method")
public class OperatePaymentMethod implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Integer paymentType_integral = 0;
    public static final Integer paymentType_bank = 1;
    public static final Integer paymentType_visa = 2;
    public static final Integer paymentType_alipay = 3;
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
     *
     */
    private String image;
    /**
     *  appId
     */
    private String appId;
    /**
     *  mch id
     */
    private String mchId;
    /**
     * key
     */
    private String key;
    /**
     * 支付宝  key
     */
    private String publicKey;
    /**
     * 商户私key
     */
    private String privateKey;
    /**
     * 0 正常 1 删除
     */
    private Integer status;
    /**
     * 支付方式类型（1 银行转账，2 visa，3 支付宝）
     */
    private Integer paymentType;
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
