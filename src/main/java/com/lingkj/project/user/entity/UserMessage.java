package com.lingkj.project.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenyongsong
 * @date 2019-09-11 14:22:51
 */
@Data
@TableName("user_message")
public class UserMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 接受人消息
     */
    private Long userId;
    /**
     * 接收人名字
     */
    @TableField(exist = false)
    private String acceptUserName;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息文件
     */
    private String fileUrl;
    /**
     * 发送人消息
     */
    private Long senderId;

    /**
     * 订单站内消息
     */
    private Long recordId;
    /**
     * 订单商品
     */
    private Long transactionCommodityId;
    /**
     * 发送者用户名
     */
    @TableField(exist = false)
    private String senderUserName;
    /**
     * 消息读取状态（0 未读，1 已读）
     */
    private Integer readStatus;
    /**
     * 发送时间
     */
    private Date createTime;
    /**
     * 判断当前订单是否是本人，且本人是否为设计师    ( 0 否 1 是 )
     */

    private  Integer type;
}
