package com.lingkj.project.operation.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:25
 */
@Data
@TableName("operate_project")
public class OperateProject implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * logo
     */
    private String logo;
    /**
     * 公司名称
     */
    @TableField(exist=false)
    private String companyName;
    /**
     * 应用名称
     */
    private String name;
    /**
     *
     */
    private String email;
    /**
     *
     */
    private String address;
    /**
     *
     */
    private String phone;
    /**
     *
     */
    private String remark;
    /**
     *
     */
    private String fax;
    /**
     *
     */
    @TableField(exist=false)
    private String createSysUserName;
    private Long createSysUserId;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    @TableField(exist=false)
    private String updateSysUserName;
    private Long updateSysUserId;
    /**
     *
     */
    private Date updateTime;

    private String amWorkTime;
    private String pmWorkTime;

  }
