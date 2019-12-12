package com.lingkj.project.operation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * FeedBackReqDto
 *
 * @author chen yongsong
 * @className FeedBackReqDto
 * @date 2019/7/12 9:07
 */
@ApiModel("反馈消息实体")
@Data
public class FeedBackReqDto {
    @ApiModelProperty("反馈内容")
    @NotBlank(message = "反馈意见不能为空")
    private String content;
    @ApiModelProperty("反馈联系方式")
    @NotBlank(message = "联系方式不能为空")
    private String phone;
}
