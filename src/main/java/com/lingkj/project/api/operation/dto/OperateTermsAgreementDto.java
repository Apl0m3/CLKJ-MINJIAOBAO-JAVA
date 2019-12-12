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
@ApiModel("条款与协议列表返回")
@Data
public class OperateTermsAgreementDto {
    @ApiModelProperty("条款与协议id")
    long id;

    @ApiModelProperty("条款与协议类型")
    private  Integer type;

    @ApiModelProperty("条款与协议标题")
    private String title;

    @ApiModelProperty("条款与协议内容")
    private String content;

}
