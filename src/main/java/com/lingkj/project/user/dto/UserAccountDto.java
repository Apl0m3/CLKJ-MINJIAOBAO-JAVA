package com.lingkj.project.user.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * UserCommisionDto
 *
 * @author chen yongsong
 * @className UserCommisionDto
 * @date 2019/10/28 9:41
 */
@Data
public class UserAccountDto {

    private Long id;
    /**
     *
     */
    private Long userId;
    /**
     * 佣金
     */
    private BigDecimal amount;
    /**
     * 总金额(包含未结算)
     */
    private BigDecimal totalAmount;
    /**
     * 未结算订单数量
     */
    private Integer orderNum;
    /**
     * 已结算金额
     */
    private BigDecimal settlementAmount;

    private String name;
    private String userName;
    private Long userRoleId;
}
