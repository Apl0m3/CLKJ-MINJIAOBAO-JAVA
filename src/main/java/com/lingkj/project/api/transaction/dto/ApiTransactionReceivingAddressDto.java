package com.lingkj.project.api.transaction.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * TransactionReceivingAddressDto
 *
 * @author chen yongsong
 * @className TransactionReceivingAddressDto
 * @date 2019/9/20 15:08
 */
@ApiModel("订单收货地址")
@Data
public class ApiTransactionReceivingAddressDto {
    @ApiModelProperty(value = "收货地址id")
    private Long id;
    @ApiModelProperty(value = "收货人名称")
    private String name;
    @ApiModelProperty(value = "收货人电话号")
    private String phone;
    @ApiModelProperty(value = "详细地址")
    private String address;
    @ApiModelProperty(value = "所在地区")
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
    @ApiModelProperty(value = "邮政编码")
    private String postalCode;
    /**
     * 市
     */
    private String cityStr;
    @ApiModelProperty(value = "所在地区详情")
    private String areaCodeName;
    @ApiModelProperty(value = "国家")
    private String country;
}
