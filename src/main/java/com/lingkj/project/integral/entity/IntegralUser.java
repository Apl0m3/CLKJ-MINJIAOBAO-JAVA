package com.lingkj.project.integral.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户积分
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:25
 */
@Data
@TableName("user_integral")
public class IntegralUser implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 积分
	 */
	private Integer integral;
	/**
	 *
	 */
	private Date createTime;
	/**
	 * 0 正常 1 禁用
	 */
	private Integer status;
	/**
	 *
	 */
	private Date modifyTime;

}
