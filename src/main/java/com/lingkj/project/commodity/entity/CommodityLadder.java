package com.lingkj.project.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品数量阶梯
 *
 * @author chenyongsong
 * @date 2019-09-17 15:13:42
 */
@Data
@TableName("commodity_ladder")
public class CommodityLadder implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     *
     */
    private Long commodityId;
    /**
     * 数量
     */
    private Integer num;
    /**
     * 折扣
     */
    private BigDecimal discount;
    /**
     * 排序  asc
     */
    private Integer sort;
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
