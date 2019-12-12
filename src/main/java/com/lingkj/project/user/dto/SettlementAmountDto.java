package com.lingkj.project.user.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * SettlementAmountDto
 *
 * @author chen yongsong
 * @className SettlementAmountDto
 * @date 2019/10/28 15:06
 */
@Data
public class SettlementAmountDto {
    private Long id;
    private BigDecimal amount;
}
