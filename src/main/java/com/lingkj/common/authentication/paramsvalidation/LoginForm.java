package com.lingkj.common.authentication.paramsvalidation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 登录表单
 *
 * @author admin
 * @since 3.1.0 2018-01-25
 */
@Data
@ApiModel(value = "登录表单")
public class LoginForm {
    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号格式不正确")
    private String mobile;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, message = "密码格式不正确")
    private String password;

    @ApiModelProperty(value = "是否自动登录")
    @NotNull(message = "自动登录状态不能为空")
    private Integer autoLoginStatus;
    @ApiModelProperty(value = "WX openId")
    private String openId;


}
