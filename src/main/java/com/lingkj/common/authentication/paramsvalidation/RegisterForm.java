/**
 * Copyright 2018 辰领科技 lingkj.com
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.lingkj.common.authentication.paramsvalidation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 注册表单
 *
 * @author admin
 * @since 3.1.0 2018-01-25
 */
@Data
@ApiModel(value = "注册表单")
public class RegisterForm {
    @ApiModelProperty(value = "手机号")
//    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号格式不正确")
//    @Pattern(regexp = "1[3|4|5|7|8][0-9]\\\\d{8}", message = "手机号格式不合格")

    private String mobile;

    @ApiModelProperty(value = "密码")
//    @NotBlank(message = "密码不能为空")
    @Length(min = 6, message = "密码格式不正确")
    private String password;

    @ApiModelProperty(value = "验证码")
//    @NotBlank(message = "验证码不能为空")
    @Length(min = 6, max = 6, message = "验证码格式不正确")
    private String code;

    @ApiModelProperty(value = "邀请二维码")
    private String qrCode;
    @ApiModelProperty(value = "微信OpenId")
    private String openId;


}
