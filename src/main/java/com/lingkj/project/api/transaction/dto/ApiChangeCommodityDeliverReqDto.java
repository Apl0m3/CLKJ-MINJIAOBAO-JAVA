package com.lingkj.project.api.transaction.dto;

import lombok.Data;

/**
 * ApiChangeCommodityDeliverReqDto
 *
 * @author chen yongsong
 * @className ApiChangeCommodityDeliverReqDto
 * @date 2019/10/17 14:40
 */
@Data
public class ApiChangeCommodityDeliverReqDto {
    private Long deliveryId;
    private Integer status;
}
