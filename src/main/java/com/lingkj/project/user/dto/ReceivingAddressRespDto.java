package com.lingkj.project.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ReceivingAddressRespDto
 *
 * @author chen yongsong
 * @className ReceivingAddressRespDto
 * @date 2019/7/5 17:52
 */
@Data
@ApiModel("收货地址")
public class ReceivingAddressRespDto {
    @ApiModelProperty(value = "收货地址id")
    private Long id;
    @ApiModelProperty(value = "收货人名称")
    private String name;
    @ApiModelProperty(value = "收货人电话号")
    private String phone;
    @ApiModelProperty(value = "是否为默认收货地址")
    private Integer isDefault;
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
    private String  country;
}
