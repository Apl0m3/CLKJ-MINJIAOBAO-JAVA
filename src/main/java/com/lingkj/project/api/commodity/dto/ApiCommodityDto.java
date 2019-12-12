package com.lingkj.project.api.commodity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * ApiCommodityDto
 *
 * @author chen yongsong
 * @className ApiCommodityDto
 * @date 2019/9/24 15:00
 */
@Data
public class ApiCommodityDto {
    @ApiModelProperty("商品id")
    private Long id;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品描述")
    private String remark;

    @ApiModelProperty("商品详情")
    private String description;

    @ApiModelProperty("商品类型")
    private Long commodityTypeId;

    @ApiModelProperty(" 商品图片")
    private String image;

    @ApiModelProperty("1-普通商品 2-积分商品")
    private Integer type;
    @ApiModelProperty("收藏状态 0-未收藏 1-已收藏")
    private Integer collectionStatus;
    /**
     * 数量填写方式 1：输入，2：选择，3：服装
     */
    private Integer numberSelectType;
    /**
     * 稿件
     */
    private String manuscriptUrl;
    /**
     * 数量填写标签
     */
    private String numberInputLabel;
    @ApiModelProperty("商品轮播图")
    private List<ApiCommodityFileDto> list;

    private ApiCommodityTypeDto commodityType;


}
