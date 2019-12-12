package com.lingkj.project.integral.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户积分记录
 *
 * @author chenyongsong
 * @date 2019-06-26 16:10:25
 */
@Data
@TableName("user_integral_log")
public class IntegralUserLog implements Serializable {
    /**
     * 进账
     */
    public static final Integer type_in = 1;
    /**
     * 出账
     */
    public static final Integer type_out = 2;

    private static final long serialVersionUID = 1L;

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
     * 以前的积分
     */
    private Integer previousValue;
    /**
     * 当前的积分
     */
    private Integer currentValue;
    /**
     * 改变积分
     */
    private Integer changeValue;
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
     * 交易订单号
     */
    private String transactionId;
    private Long recordId;
    private Long transactionCommodityId;

}
