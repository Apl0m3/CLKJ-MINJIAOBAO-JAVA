package com.lingkj.project.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:26
 */
@Data
@TableName("user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String default_pwd = "123456";
    /**
     * 1	普通用户
     * 2	代理商
     * 3	设计师
     * 4	供应商
     */
    public static final Long member_role_Ordinary = 1L;
    public static final Long member_role_agent = 2L;
    public static final Long member_role_designer = 3L;
    public static final Long member_role_supplier = 4L;
    public static final Long platform_user_id = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     *
     */
    private String userName;
    /**
     *
     */
    private String password;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private String nickName;
    /**
     *
     */
    private String avatar;
    /**
     *
     */
    private String birthday;
    /**
     *
     */
    private Integer gender;
    /**
     * 是否自动登录（0 否 1 是）
     */
    private Integer autoLoginStatus;

    /**
     *
     */
    private Date createTime;

    /**
     * 所在地区详细地址
     */
    private String address;

    /**
     * 0 正常  1 禁用  2 删除
     */
    private Integer status;

    @TableField(exist = false)
    private int integral = 0;
    /**
     * 国家
     */
    private String country;
    /**
     * 邮编
     */
    private String postalCode;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 固定电话号码
     */
    private String telephoneNumber;

    /**
     * 交易订单数
     */
    @TableField(exist = false)
    private Integer countPay = 0;
    /**
     * 交易订单金额
     */
    @TableField(exist = false)
    private Integer sumPay = 0;
    /**
     * 省
     */
    private Long province;
    /**
     * 市
     */
    private Long city;

    private Long userRoleId;


}
