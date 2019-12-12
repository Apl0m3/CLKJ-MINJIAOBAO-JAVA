package com.lingkj.project.api.cart.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * ApiCartAttributesDto
 *
 * @author chen yongsong
 * @className ApiCartAttributesDto
 * @date 2019/10/22 18:11
 */
@Data
public class ApiCartLadderDto {
    private Long id;
    /**
     *
     */
    private Long cartId;
    /**
     * commodity ladder id
     */
    private Long ladderId;
    /**
     * 数量
     */
    private Integer num;
    /**
     * 折扣
     */
    private BigDecimal discount;
}
