package com.lingkj.project.api.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * AmountDto
 *
 * @author chen yongsong
 * @className AmountDto
 * @date 2019/10/31 10:02
 */
@Data
public class AmountDto {
    private BigDecimal amount;
    /**
     * 供应商 结算价格
     */
    private BigDecimal factoryPrice;

    public AmountDto(BigDecimal amount, BigDecimal factoryPrice) {
        this.amount = amount;
        this.factoryPrice = factoryPrice;
    }

    public AmountDto() {
    }
}
