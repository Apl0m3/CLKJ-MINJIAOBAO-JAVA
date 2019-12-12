package com.lingkj.project.api.transaction.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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
public class ApiTransactionInvoiceDto {

    /**
     *
     */
    @TableId
    private Long id;
    /**
     *
     */
    private Long transactionId;
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
     *
     */
    private String address;
    /**
     * 1,2,3
     */
    private String areaCode;


    /**
     * 省
     */
    private Long province;
    /**
     * 市
     */
    private Long city;
    /**
     * 省
     */
    private String provinceStr;
    /**
     * 市
     */
    private String cityStr;
    @ApiModelProperty(value = "邮政编码")
    private String postalCode;

    @ApiModelProperty(value = "所在地区详情")
    private String areaCodeName;

}
