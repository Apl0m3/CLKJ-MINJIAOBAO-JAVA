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
public class WorkUsDto {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("详情")
    private String content;

    @ApiModelProperty("类型 1:我们是谁 2:与我们合作")
    private String type;


}
