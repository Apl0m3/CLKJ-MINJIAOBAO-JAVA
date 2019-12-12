package com.lingkj.project.user.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CommissionDto {
    private Long id;
    private Long userId;
    private Long commodityTypeId;
    /**
     * 商品名
     */
    private String name;
    /**
     * 商品佣金
     */
    private float commission;
    /**
     * 设计师佣金
     */
    private BigDecimal designCommission;


    private Date createTime;
    private Date updateTime;

    private Long createBy;
    private Long updateBy;


}
