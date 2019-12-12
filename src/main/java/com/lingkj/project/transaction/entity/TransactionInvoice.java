package com.lingkj.project.transaction.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单 用户发票信息
 *
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
@Data
@TableName("transaction_invoice")
public class TransactionInvoice implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     *
     */
    private Long recordId;
    /**
     * company name 公司名称
     */
    private String companyName;
    /**
     * 税号
     */
    private String taxNum;
    /**
     * 联系人
     */
    private String name;
    /**
     * 电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 是否为默认收货地址（1-否 2-是）
     */
    private Integer isDefault;
    /**
     * 国家
     */
    private String country;
    /**
     * 0 正常 1 禁用
     */
    private Integer status;
    /**
     *
     */
    private String address;
    /**
     * 1,2,3
     */
    private String areaCode;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private String postalCode;
    /**
     * 省
     */
    private Long province;
    /**
     * 市
     */
    private Long city;


}
