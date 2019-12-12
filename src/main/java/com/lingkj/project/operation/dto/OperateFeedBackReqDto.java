package com.lingkj.project.operation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel("回复反馈消息实体")
@Data
public class OperateFeedBackReqDto {

    @ApiModelProperty("反馈编号")
    @NotBlank(message = "反馈编号不能为空")
    private Long id;
    @ApiModelProperty("回复内容")
    @NotBlank(message = "回复内容不能为空")
    private String content;

}
