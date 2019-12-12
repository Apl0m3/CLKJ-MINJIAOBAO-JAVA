package com.lingkj.project.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lingkj.project.user.dto.CommissionDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author chenyongsong
 * @date 2019-09-12 09:17:38
 */
@Data
@TableName("user_designer_application")
public class UserDesignerApplication implements Serializable {
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
     * 邮箱
     * @author XXX <XXX@163.com>
     * @date 2019/11/4 11:15
     * @param
     * @return
     */
    @TableField(exist = false)
    private String userName;
    /**
     * 设计师名称
     */
    private String name;
    /**
     * 简历文件url 下载地址
     */
    private String resumeUrl;
    /**
     *
     */
    private Date createTime;
    /**
     * 0 提交审核 1 审核成功  2 审核失败
     */
    private Integer status;
    /**
     * 失败原因
     */
    private String reason;
    /**
     * 审核时间
     */
    private Date applicationTime;
    /**
     * 审核人员
     */
    private Long applicationBy;
    /**
     * 擅长的软件ids
     */
    private String typeIds;
    /**
     * 审核人员名称
     */
    @TableField(exist = false)
    private String applicationByName;
    /**
     * 擅长的软件名称字符串
     */
    @TableField(exist = false)
    private String operateTypeNames;
    /**
     * 相关技术案例文件url
     */
    @TableField(exist = false)
    private List<String> applicationFiles;


    /**
     * 佣金信息
     */
    @TableField(exist = false)
    private List<CommissionDto> commissionDto;
    /**
     * 简历名称
     */
    private String resumeName;
}
