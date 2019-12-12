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
@TableName("commodity_number_attributes")
public class CommodityNumberAttributes implements Serializable {
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
     * 属性名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 0 正常  1 删除
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
