package com.lingkj.project.operation.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 意见反馈
 *
 * @author chenyongsong
 * @date 2019-07-10 10:36:07
 */
@Data
@TableName("operate_feedback")
public class OperateFeedback implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 反馈内容
	 */
	private String content;
	/**
	 *
	 */
	private Date createTime;
	/**
	 * 0  未查看  1 已查看
	 */
	private Integer processingStatus;
	/**
	 *
	 */
	private Long userId;
	/**
	 * 联系方式
	 */
	private String contactDetails;

}
