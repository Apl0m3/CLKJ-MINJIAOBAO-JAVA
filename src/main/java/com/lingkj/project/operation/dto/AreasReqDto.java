package com.lingkj.project.operation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * areasReqDto
 *
 * @author chen yongsong
 * @className areasReqDto
 * @date 2019/7/5 16:58
 */
@ApiModel("区域查询请求实体")
@Data
public class AreasReqDto {
    @ApiModelProperty("省市区等级")
    @NotNull(message = "请输入省市区等级")
    private Integer level = 1;
    @ApiModelProperty("上一级省市id")
    @NotBlank(message = "省市id不能为空")
    private String parentId = "100000";
}
