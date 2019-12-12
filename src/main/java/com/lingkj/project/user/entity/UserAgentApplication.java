package com.lingkj.project.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

    import java.io.Serializable;
import java.util.Date;

/**
 * 用户 代理商申请
 *
 * @author chenyongsong
 * @date 2019-10-08 16:23:51
 */
@Data
@TableName("user_agent_application")
public class UserAgentApplication implements Serializable{
private static final long serialVersionUID=1L;

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
 * 营业执照 图片路劲
 */
    private String businessLicenseUrl;
/**
 * 申请时间
 */
    private Date createTime;
/**
 * 0 提交审核 1 审核成功  2 审核失败
 */
    private Integer status;
/**
 * 失败原因
 */
    private String reason;
/**
 * 审核时间
 */
    private Date applicationTime;
/**
 * 审核人员
 */
    private Long applicationBy;
/**
 * 行业类型
 */
    private Integer type;

}
