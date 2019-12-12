package com.lingkj.project.api.operation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * AdvertisementDto
 *
 * @author chen yongsong
 * @className AdvertisementRespDto
 * @date 2019/7/12 9:27
 */
@ApiModel("常见问题列表返回")
@Data
public class CommonProblemDto {

    @ApiModelProperty("问题id")
    long id;

    @ApiModelProperty("问题标题")
    private  String title;

    @ApiModelProperty("问题描述")
    private String content;
}
