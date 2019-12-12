package com.lingkj.project.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenyongsong
 * @date 2019-10-11 16:30:01
 */
@Data
@TableName("commodity_number_attributes_value")
public class CommodityNumberAttributesValue implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 规格id
     */
    private Long commodityNumberAttributesId;
    /**
     * 名称（值）
     */
    private String name;
    /**
     * 图片地址
     */
    private String url;
    /**
     *
     */
    private Integer sort;
    /**
     * 0 正常 1 删除
     */
    private Integer status;
    /**
     *
     */
    private Long createBy;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Long updateBy;
    /**
     *
     */
    private Date updateTime;

}
