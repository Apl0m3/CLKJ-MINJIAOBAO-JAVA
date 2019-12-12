package com.lingkj.project.api.commodity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: ApiDetailIntegralCommodityDto
 * @description:
 * @author: xxx
 * @createDate: 2019/10/8 16:04
 * @version: 1.0
 */
@ApiModel("积分商品详细信息")
@Data
public class ApiDetailIntegralCommodityDto {
    @ApiModelProperty("商品id")
    private Long id;
    @ApiModelProperty("商品名称")
    private String name;
    @ApiModelProperty("商品描述")
    private String description;
    @ApiModelProperty(" 商品轮播图片")
    private List<String> imageList;
    @ApiModelProperty(" 商品积分")
    private Integer amount;
    @ApiModelProperty(" 销售数量")
    private Integer soldNum;

    @ApiModelProperty(" 剩余数量")
    private Integer num;
}
