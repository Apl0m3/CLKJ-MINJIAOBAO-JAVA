package com.lingkj.project.operation.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

    import java.io.Serializable;
import java.util.Date;

/**
 * 合作伙伴
 *
 * @author chenyongsong
 * @date 2019-09-20 14:44:16
 */
@Data
@TableName("operate_about")
public class OperatePartner implements Serializable{
private static final long serialVersionUID=1L;

/**
 *
 */
        @TableId
    private Long id;
/**
 * 标题
 */
    private String title;
/**
 * 详情
 */
    private String content;
/**
 * 类型
 */
    private String type;
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
