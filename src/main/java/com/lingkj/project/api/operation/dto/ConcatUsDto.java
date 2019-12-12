package com.lingkj.project.api.operation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ConcatUsDto
 *
 * @author chen yongsong
 * @className ConcatUsDto
 * @date 2019/7/12 9:27
 */
@ApiModel("关于我们查询返回")
@Data
public class ConcatUsDto {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("传值")
    private String fax;
    @ApiModelProperty("上午工作时间")
    private String amWorkTime;
    @ApiModelProperty("下午工作时间")
    private String pmWorkTime;


}
