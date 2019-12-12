package com.lingkj.project.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

    import java.io.Serializable;
import java.util.Date;

/**
 *
 *
 * @author chenyongsong
 * @date 2019-10-14 17:24:27
 */
@Data
@TableName("user_bank")
public class UserBank implements Serializable{
private static final long serialVersionUID=1L;

/**
 *
 */
        @TableId
    private Long id;
/**
 * 申请用户id
 */
    private Long userId;
/**
 * 银行卡号
 */
    private String bankAccount;
/**
 * 银行名称
 */
    private String bankName;
/**
 * 开户人名称
 */
    private String bankUserName;
/**
 * 申请类型id
 */
    private Long applicationId;

    private  Date createTime;



    /**
     * '用户角色  0 普通  1 设计  2 供应'
     */
    private  Integer type;

}
