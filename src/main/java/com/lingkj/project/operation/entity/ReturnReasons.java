package com.lingkj.project.operation.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

    import java.io.Serializable;
import java.util.Date;

/**
 * 退款原因
 *
 * @author chenyongsong
 * @date 2019-09-19 16:11:50
 */
@Data
@TableName("`operate_return_reasons`")
public class  ReturnReasons implements Serializable {
private static final long serialVersionUID=1L;

/**
 *
 */
        @TableId
    private Long id;
/**
 * 退款原因
 */
    private String name;
/**
 * 备注
 */
    private String remark;
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
