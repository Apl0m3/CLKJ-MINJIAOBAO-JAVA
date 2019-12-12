package com.lingkj.project.message.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:25
 */
@Data
@TableName("message_user_log")
public class MessageUserLog implements Serializable {
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
	 *
	 */
	private Long messageId;
	/**
	 * 0 正常 1 删除
	 */
	private Integer status;
	/**
	 *
	 */
	private Date createTime;
}
