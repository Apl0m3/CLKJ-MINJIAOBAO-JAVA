package com.lingkj.project.operation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * AdvertisementRespDto
 *
 * @author chen yongsong
 * @className AdvertisementRespDto
 * @date 2019/7/12 9:27
 */
@ApiModel("广告列表返回")
@Data
public class AdvertisementRespDto {

    @ApiModelProperty("广告id")
    private Long id;

    @ApiModelProperty("广告图片")
    private String image;

    @ApiModelProperty("跳转方式（1-详情，2-外部跳转，3-板块跳转）")
    private Integer jumpMode;

    @ApiModelProperty("广告标题")
    private String title;

    @ApiModelProperty("广告内容")
    private String content;
    @ApiModelProperty("url")
    private String url;
    /***
     * 板块跳转类型
     * 1 商城 2 积分商城 3 商品
     */
    @ApiModelProperty("板块跳转类型")
    private Integer type;


}
