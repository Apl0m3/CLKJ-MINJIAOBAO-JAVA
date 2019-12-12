package com.lingkj.project.api.commodity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * ApiCommodityDto
 *
 * @author chen yongsong
 * @className ApiCommodityDto
 * @date 2019/9/24 9:28
 */
@ApiModel("列表返回实体")
@Data
public class ApiCommodityTypeDto {
    @ApiModelProperty("商品id")
    private Long id;
    @ApiModelProperty("商品类型名称")
    private String name;
    @ApiModelProperty("商品类型图片")
    private String image;
    @ApiModelProperty("需要设计师时用户支付佣金 ")
    private BigDecimal commission;

}
