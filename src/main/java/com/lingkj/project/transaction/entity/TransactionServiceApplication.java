package com.lingkj.project.transaction.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 售后  申请表
 *
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
@Data
@TableName("transaction_service_application")
public class TransactionServiceApplication implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 状态  0 审核中  1 审核成功  2审核失败
     */
    public static final Integer STATUS_PENDING =0;
    public static final Integer STATUS_SUCCESS =1;
    public static final Integer STATUS_FAIL =2;
    /**
     * 售后类型  2 退款 1 返工
     */
    public static final Integer TYPE_REWORK =1;
    public static final Integer TYPE_REFUND =2;
    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 售后类型   2 退款 1 返工
     */
    private Integer type;
    /**
     * 申请人
     */
    private Long userId;
    /**
     * 申请人姓名
     *
     * @date 2019/10/21 14:47
     */
    @TableField(exist = false)
    private String name;
    /**
     * 用户名
     */
    @TableField(exist = false)
    private String userName;
    /**
     * 申请时间
     */
    private Date createTime;
    /**
     * 申请退货 产品
     */
    private Long transactionCommodityId;
    /**
     * 产品名
     *
     * @date 2019/10/21 14:47
     */
    @TableField(exist = false)
    private String commodityName;

    /**
     * 订单号
     *
     * @date 2019/10/21 14:47
     */
    @TableField(exist = false)
    private String transactionId;
    /**
     * 退货原因
     */
    private String returnReason;
    /**
     * 状态  0 审核中  1 审核成功  2审核失败
     */
    private Integer status;
    /**
     * 审核人
     */
    private Long applicationBy;
    /**
     * 审核人姓名
     *
     * @date 2019/10/21 14:47
     */
    @TableField(exist = false)
    private String applicationName;

    /**
     * 审核时间
     */
    private Date applicationTime;
    /**
     * 审核失败原因
     */
    private String reason;

    /**
     * 退货说明
     */
    private String returnExplain;

    @TableField(exist = false)
    private List<TransactionServiceFile> fileList;

}
