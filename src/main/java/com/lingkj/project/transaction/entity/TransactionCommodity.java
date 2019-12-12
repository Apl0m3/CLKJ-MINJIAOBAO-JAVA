package com.lingkj.project.transaction.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
@Data
@TableName("transaction_commodity")
public class TransactionCommodity implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Integer need_designer_default = 0;
    public static final Integer need_designer_manuscript = 1;
    public static final Integer need_designer_design= 2;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 支付订单号
     */
    private String transactionId;

    private Long recordId;
    /**
     * 数量填写方式 1：输入，2：选择，3：服装
     */
    private Integer numberSelectType;
    /**
     * 商品数量
     */
    private Integer commodityNum;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品图片
     */
    private String image;

    /**
     * 稿件
     */
    private String manuscriptUrl;
    /**
     * 商品id
     */
    private Long commodityId;
    /**
     * 商品类型id
     */
    private Long commodityTypeId;
    /**
     * 是否需要设计师
     */
    private Integer needDesignerStatus;
    /**
     * 设计要求
     */
    private String designRequirements;
    /**
     * 预计达到时间
     */
    private Date expectTime;
    /**
     * 商品价格
     */
    private BigDecimal amount;
    /**
     * 	供应商 结算价格
     */
    private BigDecimal factoryPrice;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 子状态
     */
    private Integer substate;
    /**
     *
     */
    private Date createTime;

    /**
     * 留言
     */
    private String remark;

    private Integer type;

}
