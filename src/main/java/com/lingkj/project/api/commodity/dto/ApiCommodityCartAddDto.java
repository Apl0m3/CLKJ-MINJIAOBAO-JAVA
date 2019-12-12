package com.lingkj.project.api.commodity.dto;

import com.lingkj.project.api.cart.dto.ApiCartNumberAttributeDto;
import com.lingkj.project.api.transaction.dto.ApiTransactionAttributeDto;
import com.lingkj.project.api.transaction.dto.ApiTransactionLadderExpectDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * ApiCommodityCartAddDto
 *
 * @author chen yongsong
 * @className ApiCommodityCartAddDto
 * @date 2019/9/29 17:25
 */
@Data
@ApiModel("添加购物车实体")
public class ApiCommodityCartAddDto {
    @ApiModelProperty("用户上传设计稿件")
    private String manuscriptUrl;
    @ApiModelProperty("设计要求")
    private String designRequirements;
    @ApiModelProperty("商品id")
    private Long commodityId;
    @ApiModelProperty(value = "商品属性")
    private List<ApiTransactionAttributeDto> attribute;
    @ApiModelProperty(value = "服装 商品数量")
    private List<ApiCartNumberAttributeDto> numberAttributesList;
    @ApiModelProperty(value = "商品预计到货和时间")
    private ApiTransactionLadderExpectDto ladderExpect;
    @ApiModelProperty(value = "设计师状态 0 默认稿件  1 上传稿件  2 设计师设计稿件 ")
    private Integer designStatus;


}
