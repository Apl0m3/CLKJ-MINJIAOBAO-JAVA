package com.lingkj.project.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lingkj.project.user.dto.CommissionDto;
import com.lingkj.project.user.dto.DiscountDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户分销会员申请
 *
 * @author chenyongsong
 * @date 2019-08-16 09:31:05
 */
@Data
@TableName("user_agent_application")
public class UserMemberApplication implements Serializable {
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
    private String businessLicenseUrl;
    /**
     *
     */
    private Integer type;
    /**
     *
     */
    private Date createTime;
    /**
     * 审核失败原因
     */
    private String reason;
    /**
     * 0-已申请，审核中，1-审核成功，2-申请失败
     */
    private Integer status;
    /**
     * 审核时间
     */
    private Date applicationTime;
    /**
     * 审核人
     */
    private Long applicationBy;

    @TableField(exist = false)
    private String applicationName;
    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String mail;

    @TableField(exist = false)
    private String typeName;

    @TableField(exist = false)
    private List<DiscountDto> discountDtoList;
}
