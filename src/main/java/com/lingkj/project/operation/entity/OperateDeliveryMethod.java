package com.lingkj.project.operation.entity;

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
@TableName("operate_delivery_method")
public class OperateDeliveryMethod implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 外部物流
     */
    public static final Long External_Logistics = 1L;
    /**
     * 平台物流
     */
    public static final Long Platform_Logistics = 2L;


    /**
     *
     */
    @TableId
    private Long id;
    /**
     *
     */
    private String name;
    /**
     * 0-正常 1- 禁用
     */
    private Integer status;
    /**
     *
     */

    private Long createSysUserId;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Long updateSysUserId;
    /**
     *
     */
    private Date updateTime;
    @TableField(exist = false)
    private String createSysUserName;

    @TableField(exist = false)
    private String updateSysUserName;

    public OperateDeliveryMethod() {
    }

    public OperateDeliveryMethod(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
