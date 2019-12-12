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
@ApiModel("广告列表返回")
@Data
public class AdvertisementDto {
    @ApiModelProperty("广告id")
    private Long id;

    @ApiModelProperty("广告图片")
    private String image;

    @ApiModelProperty("跳转方式（1-详情，2-外部跳转，3-板块跳转）")
    private Integer jumpMode;

    @ApiModelProperty("板块跳转类型（1-商品分类 ，2-积分商城，3-商品 ）")
    private Integer module;

    @ApiModelProperty("url")
    private String url;

    @ApiModelProperty("板块跳转类型")
    private Integer type;

    @ApiModelProperty("位置 1 首页  2 商品列表页 3 积分商品页")
    private Integer position;

}
