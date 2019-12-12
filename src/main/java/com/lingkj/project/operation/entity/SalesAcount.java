package com.lingkj.project.operation.entity;

import lombok.Data;

/**
 * @author XXX <XXX@163.com>
 * @version V1.0.0
 * @description 销售额实体
 * @date 2019/11/2 15:03
 */
@Data
public class SalesAcount {

    /**
     * 商品id
     * @author XXX <XXX@163.com>
     * @date 2019/11/2 15:09
     * @param
     * @return
     */
    private Long commodityId;

    /**
     * 商品名
     * @author XXX <XXX@163.com>
     * @date 2019/11/2 15:09
     * @param
     * @return
     */
    private String name;

    /**
     * 商品销售数量
     * @author XXX <XXX@163.com>
     * @date 2019/11/2 15:10
     * @param
     * @return
     */
    private Integer quantity;

    /**
     * 销售额
     * @author XXX <XXX@163.com>
     * @date 2019/11/2 15:11
     * @param
     * @return
     */
    private  Integer salesAmount;
}
