package com.lingkj.project.operation.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 常见问题
 *
 * @author chenyongsong
 * @date 2019-09-19 11:02:41
 */
@Data
@TableName("operate_common_problem")
public class CommonProblem implements Serializable{
private static final long serialVersionUID=1L;

/**
 *
 */
        @TableId
    private Long id;
/**
 * 常见问题标题
 */
    private String title;
/**
 * 常见问题描述
 */
    private String content;
/**
 *
 */
    private Date createTime;
/**
 *
 */
    private Long createBy;
/**
 *
 */
    private Date updateTime;
/**
 *
 */
    private Long updateBy;

}
