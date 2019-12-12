package com.lingkj.project.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 站内消息图片
 *
 * @author chenyongsong
 * @date 2019-10-21 20:04:11
 */
@Data
@TableName("user_message_file")
public class UserMessageFile implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * userid
     */
    private Long userId;
    /**
     * 消息id
     */
    private Long messageId;
    /**
     *
     */
    private String url;
    /**
     *
     */
    private Date createTime;

}
