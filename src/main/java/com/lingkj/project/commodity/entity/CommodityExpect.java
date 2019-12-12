package com.lingkj.project.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 预计到货时间
 *
 * @author chenyongsong
 * @date 2019-09-26 11:41:34
 */
@Data
@TableName("commodity_expect")
public class CommodityExpect implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 商品id
     */
    private Long commodityId;
    /**
     * 预计 n 天后，送达
     */
    private Integer day;
    /**
     * 商品数量上限
     */
    private Integer maxNum;
    /**
     * 额外金额
     */
    private BigDecimal amount;
    /**
     * 供应商 价格
     */
    private BigDecimal factoryPrice;
    /**
     * 状态 0 正常 1 删除
     */
    private Integer status;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Long createBy;
    /**
     *
     */
    private Date updateTime;
    /**
     *
     */
    private Long updateBy;

}
