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
@TableName("operate_terms_agreement")
public class OperateTermsAgreement implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     *   1 注册协议
     */
    private Integer type;
    /**
     * 0 正常 1 删除
     */
    private Integer status;
    /**
     *
     */
    private String title;
    /**
     *
     */
    private String icon;
    /**
     *
     */
    private String content;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    @TableField(exist = false)
    private String createSysUserName;
    private Long createSysUserId;
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
