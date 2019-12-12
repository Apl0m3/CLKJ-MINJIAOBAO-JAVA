package com.lingkj.project.api.user.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * ApiUserInvoiceDto
 *
 * @author chen yongsong
 * @className ApiUserInvoiceDto
 * @date 2019/9/30 16:51
 */
@ApiModel("用户发票信息返回接口")
@Data
public class ApiUserInvoiceDto {

    @ApiModelProperty("发票id")
    private Long id;

    /**
     * company name 公司名称
     */
    @ApiModelProperty(" 公司名称")
    private String companyName;
    /**
     * 税号
     */
    @ApiModelProperty(" 税号")
    private String taxNum;
    /**
     * 联系人
     */
    @ApiModelProperty(" 联系人")
    private String name;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("国家")
    private String country;

    @ApiModelProperty("公司地址")
    private String address;

    @ApiModelProperty("省")
    private Long province;

    @ApiModelProperty("市")
    private Long city;

    @ApiModelProperty("省")
    private String provinceStr;

    @ApiModelProperty("市")
    private String cityStr;

    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("邮政编码")
    private String postalCode;
}
