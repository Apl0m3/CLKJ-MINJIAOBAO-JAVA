package com.lingkj.common.authentication.paramsvalidation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


/**
 * 发送验证码表单
 *
 * @author chen yongsong
 * @className SendCodeForm
 * @date 2019/6/27 10:44
 */
@Data
@ApiModel("发送验证码表单")
public class VerifyCodeForm {
    @ApiModelProperty(value = "手机号")
//    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号格式不合格")
//    @Pattern(regexp = "1[3|4|5|7|8][0-9]\\\\d{8}", message = "手机号格式不合格")
    private String mobile;
    @ApiModelProperty(value = "验证码类型")
//    @NotBlank(message = "验证码类型不能为空")
    private Integer type;
    @ApiModelProperty(value = "验证码")
//    @NotBlank(message = "验证码不能为空")
    @Length(min = 6, max = 6, message = "验证码格式不合格")
    private String code;

}
