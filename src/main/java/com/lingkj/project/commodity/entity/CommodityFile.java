package com.lingkj.project.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:26
 */
@Data
@TableName("commodity_file")
public class CommodityFile implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 文件类型（1 图片，2 视频）
     */
    private Integer fileType;

    /**
     * 商品id
     */
    private Long commodityId;
    /**
     * 文件路劲
     */
    private String fileUrl;
    /**
     *
     */
    private Date createTime;
    /**
     * type=2 null
     */
    @TableField(exist=false)
    private String createSysUserName;
    private Long createSysUserId;
    /**
     * type=2 null
     */
    @TableField(exist=false)
    private String updateSysUserName;
    private Long updateSysUserId;
    /**
     * type=2 null
     */
    private Date updateTime;
    /**
     * asc 排序
     */
    private Integer sequence;



}
