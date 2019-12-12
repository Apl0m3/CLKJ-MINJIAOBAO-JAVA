package com.lingkj.project.api.transaction.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * ApiTransactionCommodityLadderDto
 *
 * @author chen yongsong
 * @className ApiTransactionCommodityLadderDto
 * @date 2019/10/22 19:45
 */
@Data
public class ApiTransactionCommodityLadderDto {
    private Long id;
    /**
     *
     */
    private Long commodityId;
    /**
     * 数量
     */
    private Integer num;
    /**
     * 折扣
     */
    private BigDecimal discount;
}
