package com.lingkj.project.cart.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:25
 */
@Data
@TableName("cart")
public class Cart implements Serializable {
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
     * 用户上传设计稿件
     */
    private String manuscriptUrl;
    /**
     * 是否需要设计师
     */
    private Integer needDesignerStatus;
    /**
     * 数量填写方式 1：输入，2：选择，3：服装
     */
    private Integer numberSelectType;
    /**
     * 总数
     */
    private Integer totalQuantity;
    /**
     * 设计要求
     */
    private String designRequirements;
    /**
     * 商品id
     */
    private Long commodityId;

    /**
     * 商品价格
     */
    private BigDecimal amount;
    /**
     * 	供应商 结算价格
     */
    private BigDecimal factoryPrice;

    /**
     *
     */
    private Date createTime;


}
