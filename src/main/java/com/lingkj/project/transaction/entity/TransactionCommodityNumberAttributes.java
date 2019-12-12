package com.lingkj.project.transaction.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenyongsong
 * @date 2019-10-22 14:55:54
 */
@Data
@TableName("transaction_commodity_number_attributes")
public class TransactionCommodityNumberAttributes implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * transaction commodity id
     */
    private Long transactionCommodityId;
    /**
     * 属性名称
     */
    private String name;
    /**
     * 	值
     */
    private Integer num;
}
