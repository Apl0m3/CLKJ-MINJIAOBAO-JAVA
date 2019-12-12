package com.lingkj.project.user.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 代理商折扣率实体
 * @author XXX <XXX@163.com>
 * @date 2019/11/21 11:56
 * @param
 * @return
 */
@Data
public class DiscountDto {
    private Long id;
    /**
        * 类型id
     */
    private Long commodityTypeId;
    /**
     * 类型名
     */
    private String name;
    /**
     * 代理商折扣率
     */
    private BigDecimal discount;

    /**
     * 代理商id
     */
    private Long userId;

    private Date createTime;
    private Date updateTime;

    private Long createBy;
    private Long updateBy;
}
