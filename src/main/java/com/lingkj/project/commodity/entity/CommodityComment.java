package com.lingkj.project.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:26
 */
@Data
@TableName("commodity_comment")
public class CommodityComment implements Serializable {
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
     * 交易订单号
     */
    private String transactionId;
    /**
     * 商品id
     */
    private Long commodityId;

    private Date createTime;
    /**
     * 评论内容
     */
    private String comment;
    /**
     * 评分
     */
    private Double score;
    /**
     * 管理员是否已读 0：未读，1：已读
     */
    private Integer readStatus;
    /**
     * 评论图片
     */
    @TableField(exist = false)
    private String file;

    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String commodityName;


}
