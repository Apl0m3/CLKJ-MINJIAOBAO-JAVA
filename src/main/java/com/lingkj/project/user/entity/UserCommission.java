package com.lingkj.project.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 设计师或者供应商佣金
 *
 * @author chenyongsong
 * @date 2019-10-25 14:16:42
 */
@Data
@TableName("user_commission")
public class UserCommission implements Serializable {
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
    private Long commodityTypeId;
    /**
     * 佣金
     */
    private BigDecimal commission;
    /**
     *
     */
    private Long createBy;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Long updateBy;
    /**
     *
     */
    private Date updateTime;

}
