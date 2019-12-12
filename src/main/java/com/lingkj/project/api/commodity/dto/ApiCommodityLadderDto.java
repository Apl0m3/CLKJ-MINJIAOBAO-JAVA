package com.lingkj.project.api.commodity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * ApiCommodityLadderDto
 *
 * @author chen yongsong
 * @className ApiCommodityLadderDto
 * @date 2019/9/29 9:03
 */
@ApiModel("商品详情 阶梯价 返回实体")
@Data
public class ApiCommodityLadderDto {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("数量")
    private Integer num;
    @ApiModelProperty("折扣")
    private BigDecimal discount;
}
