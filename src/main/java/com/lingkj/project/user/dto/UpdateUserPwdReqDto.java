package com.lingkj.project.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * UserDto
 *
 * @author chen yongsong
 * @className UserDto
 * @date 2019/7/11 18:07
 */
@ApiModel("用户请求")
@Data
public class UpdateUserPwdReqDto {
   @ApiModelProperty("旧密码")
   @NotBlank(message = "旧密码不能为空")
    private String password;
    @ApiModelProperty("新密码")
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
    @ApiModelProperty("重复密码")
    @NotBlank(message = "重复密码不能为空")
    private String affirmPassword;

}
