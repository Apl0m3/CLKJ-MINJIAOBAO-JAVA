package com.lingkj.project.operation.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 积分规则
 *
 * @author chenyongsong
 * @date 2019-06-26 16:10:26
 */
@Data
@TableName("operate_rules")
public class OperateRules implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 1-消费规则，2-分享规则
     */
    public static final Integer TYPE_CONSUME =1;
    public static final Integer TYPE_SHARE =2;
    public static final Integer RULE_TYPE_INTEGRAL =1;
    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 1-积分
     */
    private Integer ruleType;
    /**
     * ruleType = 1 时  1-消费规则，2-分享规则
     */
    private Integer type;

    /**
     * 条件
     */
    private BigDecimal factor;
    /**
     * 结果
     */
    private BigDecimal result;
    /**
     * 0 正常 1 删除
     */
    private Integer status;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    @TableField(exist = false)
    private String createSysUserName;
    private Long createSysUserId;
    /**
     *
     */
    private Date updateTime;
    /**
     *
     */
    @TableField(exist = false)
    private String updateSysUserName;
    private Long updateSysUserId;

}
