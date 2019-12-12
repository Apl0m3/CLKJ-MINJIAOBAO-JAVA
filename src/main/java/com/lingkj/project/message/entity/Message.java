package com.lingkj.project.message.entity;

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
@TableName("message")
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Integer type_sys = 1;
    public static final Integer type_user = 2;
    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 用户id
     */
    @TableField(exist = false)
    private String userName;
    private Long userId;
    /**
     * 消息类型 （1 系统消息，2 用户消息）
     */
    private Integer type;
    /**
     * 0 正常 1 删除
     */
    private Integer status;
    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 交易订单号
     */
    private String transactionId;
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
