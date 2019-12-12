package com.lingkj.project.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenyongsong
 * @date 2019-09-26 15:07:17
 */
@Data
@TableName("user_receiving_address")
public class UserReceivingAddress implements Serializable {
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
     * 收货人名称
     */
    private String name;
    /**
     * 收货人电话
     */
    private String phone;
    /**
     * 是否为默认收货地址（1-否 2-是）
     */
    private Integer isDefault;
    /**
     * 国家
     */
    private String country;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 详细地址
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
     * 0 正常 1 禁用
     */
    private Integer status;
    /**
     *
     */
    private Date createTime;
    /**
     * 邮编
     */
    private String postalCode;

}
