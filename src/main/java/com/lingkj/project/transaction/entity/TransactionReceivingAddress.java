package com.lingkj.project.transaction.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
@Data
@TableName("transaction_receiving_address")
public class TransactionReceivingAddress implements Serializable {
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
     * 收货人名称
     */
    private String name;
    /**
     * 收货人电话
     */
    private String phone;
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
     *
     */
    private Date createTime;
    /**
     * 省
     */
    private Long province;
    /**
     * 市
     */
    private Long city;
    @ApiModelProperty(value = "邮政编码")
    private String postalCode;

}
