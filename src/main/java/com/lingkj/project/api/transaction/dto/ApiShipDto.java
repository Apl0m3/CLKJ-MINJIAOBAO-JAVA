package com.lingkj.project.api.transaction.dto;

import lombok.Data;

/**
 * ApiShipDto
 *
 * @author chen yongsong
 * @className ApiShipDto
 * @date 2019/10/21 18:30
 */
@Data
public class ApiShipDto {
    private Long recordId;
    private Long transactionCommodityId;
    private String num;
}
