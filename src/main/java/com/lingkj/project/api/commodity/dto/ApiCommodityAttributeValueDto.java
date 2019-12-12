package com.lingkj.project.api.commodity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * ApiCommodityAttributeDto
 *
 * @author chen yongsong
 * @className ApiCommodityAttributeDto
 * @date 2019/9/25 9:14
 */
@ApiModel("商品属性值实体")
@Data
public class ApiCommodityAttributeValueDto {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("描述（显示在底部）")
    private String remark;
    @ApiModelProperty("加价")
    private BigDecimal amount;
    @ApiModelProperty("图片")
    private String url;
    @ApiModelProperty("是否推荐")
    private Integer recommend;
    @ApiModelProperty("尺寸 长度")
    private Double length;
    @ApiModelProperty("尺寸 宽度")
    private Double width;
    @ApiModelProperty("尺寸是否自定义( 0：不自定义 1：自定义)")
    private Integer sizeCustomizable;
}
