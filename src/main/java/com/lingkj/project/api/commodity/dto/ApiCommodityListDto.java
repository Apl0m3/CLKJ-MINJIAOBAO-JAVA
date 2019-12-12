package com.lingkj.project.api.commodity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ApiCommodityDto
 *
 * @author chen yongsong
 * @className ApiCommodityDto
 * @date 2019/9/24 9:28
 */
@ApiModel("列表返回实体")
@Data
public class ApiCommodityListDto {
    @ApiModelProperty("商品id")
    private Long id;
    @ApiModelProperty("收藏状态")
    private Integer collectionStatus = 0;
    @ApiModelProperty("推荐状态")
    private Integer recommend = 0;
    @ApiModelProperty("商品名称")
    private String name;
    @ApiModelProperty("商品图片")
    private String image;
    @ApiModelProperty("")
    private String obj;

}
