package com.lingkj.project.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户钱包记录
 *
 * @author chenyongsong
 * @date 2019-09-24 11:37:31
 */
@Data
@TableName("user_account_log")
public class UserAccountLog implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Integer type_out = 2;
    public static final Integer type_in = 1;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     *
     */
    private Long userId;
    /**
     * 以前的金额
     */
    private BigDecimal previousValue;
    /**
     * 当前的金额
     */
    private BigDecimal currentValue;
    /**
     * 改变金额
     */
    private BigDecimal changeValue;
    /**
     * 备注
     */
    private String remark;
    /**
     * 1 进账，2 出账
     */
    private Integer type;
    /**
     *
     */
    private Date createTime;
    /**
     * 订单id
     */
    private Long recordId;
    /**
     * 订单商品id
     */
    private Long transactionCommodityId;


}
