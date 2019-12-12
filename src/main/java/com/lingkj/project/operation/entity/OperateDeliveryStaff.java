package com.lingkj.project.operation.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 配送员信息
 *
 * @author chenyongsong
 * @date 2019-07-19 11:59:54
 */
@Data
@TableName("operate_delivery_staff")
public class OperateDeliveryStaff implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 配送员名称
     */
    private String name;
    /**
     * 配送员联系电话
     */
    private String phone;
    /**
     * 0 正常  1 删除
     */
    private Integer status;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Long createSysUserId;
    @TableField(exist = false)
    private String createSysUserName;
    /**
     *
     */
    private Date updateTime;
    /**
     *
     */
    @TableField(exist = false)
    private String updateSysUserName;
    private Long updateSysUserId;

}
