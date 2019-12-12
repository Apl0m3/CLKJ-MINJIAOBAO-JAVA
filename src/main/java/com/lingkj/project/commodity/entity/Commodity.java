package com.lingkj.project.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:26
 */
@Data
@TableName("commodity")
public class Commodity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品分享描述
     */
    private String remark;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 积分
     */
    private Integer amount;
    /**
     *
     */
    private Long commodityTypeId;
    /**
     * 商品列表图片
     */
    private String image;
    /**
     * 稿件
     */
    private String manuscriptUrl;
    /**
     * 1-普通商品 2-积分商品
     */
    private Integer type;
    /**
     * 数量填写方式 1：输入，2：选择，3：服装
     */
    private Integer numberSelectType;
    /**
     * 数量填写标签
     */
    private String numberInputLabel;
    /**
     * 是否推荐
     * 0：不推荐 1：推荐
     */
    private Integer recommend;

    /**
     *销售数量
     */
    private Integer soldNum;

    /**
     * 总数量
     */
    private Integer totalNum;
    /**
     * 剩余数量
     * @date 2019/10/12 15:56
     *
     */
    private Integer num;

    /**
     * 商品状态（0 正常  1 下架 2 删除）
     */
    private Integer status;
    /**
     *
     */
    @TableField(exist = false)
    private String createSysUserName;
    private Long createSysUserId;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    @TableField(exist = false)
    private String updateSysUserName;
    private Long updateSysUserId;
    /**
     *
     */
    private Date updateTime;



    @TableField(exist = false)
    private String commodityTypeName;

}
