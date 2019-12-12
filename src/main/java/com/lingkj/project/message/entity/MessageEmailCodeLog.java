package com.lingkj.project.message.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 验证码记录表
 *
 * @author chenyongsong
 *
 * @date 2019-06-27 10:02:37
 */
@Data
@TableName("message_email_code_log")
public class MessageEmailCodeLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     *
     */
    private String phone;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private String code;
    /**
     * 0-成功
     * 1-失败
     */
    private Integer status;
    /**
     *
     */
    private String msg;
    /**
     * 1-注册  2-登录  3-忘记密码 4-修改登录密码
     */
    private Integer type;


}
