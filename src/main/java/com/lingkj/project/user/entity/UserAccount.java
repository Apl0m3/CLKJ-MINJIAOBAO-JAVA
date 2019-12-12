package com.lingkj.project.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户金额
 *
 * @author chenyongsong
 * @date 2019-09-24 11:37:31
 */
@Data
@TableName("user_account")
public class UserAccount implements Serializable {
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
     * 用户金额（未结算）
     */
    private BigDecimal amount;
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
    /**
     * 总金额(包含未结算)
     */
    private BigDecimal totalAmount;
    /**
     * 已结算金额
     */
    private BigDecimal settlementAmount;

}
