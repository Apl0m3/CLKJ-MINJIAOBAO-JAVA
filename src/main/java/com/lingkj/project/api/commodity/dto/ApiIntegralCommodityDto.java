package com.lingkj.project.api.commodity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * ApiIntegralCommodityDto
 *
 * @author chen yongsong
 * @className ApiIntegralCommodityDto
 * @date 2019/10/8 11:00
 */
@ApiModel("列表展示积分商品实体")
@Data
public class ApiIntegralCommodityDto {
    @ApiModelProperty("商品id")
    private Long id;
    @ApiModelProperty("商品名称")
    private String name;
    @ApiModelProperty(" 商品图片")
    private String image;
    @ApiModelProperty(" 商品积分")
    private Integer amount;

}
