package com.lingkj.project.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

        import java.math.BigDecimal;
    import java.io.Serializable;
import java.util.Date;

/**
 * 
 *
 * @author chenyongsong
 * @date 2019-11-21 10:39:51
 */
@Data
@TableName("user_agent_rate")
public class UserAgentRate implements Serializable{
private static final long serialVersionUID=1L;

/**
 * 
 */
        @TableId
    private Long id;
/**
 * 代理商id
 */
    private Long userId;
/**
 * 商品分类id
 */
    private Long commodityTypeId;
/**
 * 折扣率
 */
    private BigDecimal rate;
/**
 * 审核人Id
 */
    private Long createBy;
/**
 * 
 */
    private Date createTime;
/**
 * 修改人id
 */
    private Long updateBy;
/**
 * 
 */
    private Date updateTime;

}
