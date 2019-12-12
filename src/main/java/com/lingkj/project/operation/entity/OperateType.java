package com.lingkj.project.operation.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

    import java.io.Serializable;
import java.util.Date;

/**
 * 供应商 供应产品 类型
 *
 * @author chenyongsong
 * @date 2019-09-12 11:54:40
 */
@Data
@TableName("operate_type")
public class OperateType implements Serializable{
private static final long serialVersionUID=1L;

/**
 *
 */
        @TableId
    private Long id;
/**
 * 1 代理商行业类型 2-供应商 供应产品选择 3 设计师 擅长的设计软件选择
 */
    private Integer type;
/**
 * 类型名称
 */
    private String name;
/**
 *
 */
    private Date createTime;
/**
 *
 */
    private String createBy;
/**
 *
 */
    private Date updateTime;
/**
 *
 */
    private Date updateBy;

}
