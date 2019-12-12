package com.lingkj.project.operation.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 费率表
 *
 * @author chenyongsong
 * @date 2019-10-10 09:15:52
 */
@Data
@TableName("operate_rate")
public class OperateRate implements Serializable {
    private static final long serialVersionUID = 1L;
      public static final Integer IVA=1;
      public static final Integer DISCOUNT=2;
    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 1-IVA，2-代理商折扣
     */
    private Integer type;
    /**
     *
     */
    private BigDecimal rate;
    /**
     *
     */
    private Long createBy;
    /**
     *
     */
    private Date createAt;
    /**
     *
     */
    private Long updateBy;
    /**
     *
     */
    private Date updateAt;

}
