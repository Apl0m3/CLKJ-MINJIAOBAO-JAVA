package com.lingkj.common.authentication.paramsvalidation;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * ReceivingAddressForm
 *
 * @author chen yongsong
 * @className ReceivingAddressForm
 * @date 2019/6/27 16:47
 */
@Data
@ApiModel("收货地址表单")
public class ReceivingAddressForm {
    /**
     *
     */
    private Long id;
    /**
     * 收货人名称
     */
    @ApiModelProperty("收货人名称")
    @NotBlank(message = "收货人名称不能为空")
    private String receiveName;
    /**
     * 收货人电话
     */
    @ApiModelProperty("收货人电话")
    @NotBlank(message = "收货人电话不能为空")
    private String receivePhone;
    /**
     * 是否为默认收货地址（1-否 2-是）
     */
    @ApiModelProperty("默认收货地址")
    @NotNull(message = "默认收货地址状态不能为空")
    private Integer isDefault;
    /**
     *
     */
    @ApiModelProperty("地址")
    @NotBlank(message = "默认收货地址状态不能为空")
    private String address;
    /**
     * 1,2,3
     */
    private String areaCode;
}
