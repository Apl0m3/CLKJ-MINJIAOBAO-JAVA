package com.lingkj.project.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单 用户发票信息
 *
 * @author chenyongsong
 * @date 2019-09-30 16:44:13
 */
@Data
@TableName("user_invoice")
public class UserInvoice implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     *
     */
    private Long userId;
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
     * 省
     */
    private Long province;
    /**
     * 市
     */
    private Long city;

    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private String postalCode;

}
