package com.lingkj.project.transaction.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

    import java.io.Serializable;
import java.util.Date;

/**
 * 订单status 改变记录表
 *
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
@Data
@TableName("transaction_commodity_status_log")
public class TransactionCommodityStatusLog implements Serializable{
private static final long serialVersionUID=1L;

/**
 *
 */
        @TableId
    private Long id;
/**
 *
 */
    private Long transactionCommodityId;
/**
 *
 */
    private Long userId;


    private  Long recordId;

/**
 *  类型：0-用户  1-平台  2-设计师  3-供应商
 */
    private Integer type;
/**
 * 状态
 */
    private Integer status;

    /**
     * 子状态
     */
    private Integer substate;
/**
 * 描述
 */
    private String remark;
/**
 * 创建时间
 */
    private Date createTime;

}
