package com.lingkj.project.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * updatePwdReqDto
 *
 * @author chen yongsong
 * @className updatePwdReqDto
 * @date 2019/7/9 16:21
 */
@ApiModel("修改登录密码")
@Data
public class UpdatePwdReqDto {
    @ApiModelProperty("密码")
//    @NotNull(message = "密码不能为空")
//    @Length(min = 6, message = "密码格式不正确")
    private String password;
    /**
     * 1-忘记密码  2-修改密码
     */
    @ApiModelProperty("类型")
//    @NotNull(message = "类型不能为空")
    private Integer type;
    @ApiModelProperty("验证码")
//    @NotNull(message = "验证码不能为空")
//    @Length(min = 6, max = 6, message = "验证码格式不正确")
    private String code;
    @ApiModelProperty("手机号")
//    @NotNull(message = "手机号不能为空")
//    @Length(min = 11, max = 11, message = "手机号格式不正确")
//    @Pattern(regexp = "1[3|4|5|7|8][0-9]\\\\d{8}", message = "手机号格式不合格")

    private String mobile;
}
