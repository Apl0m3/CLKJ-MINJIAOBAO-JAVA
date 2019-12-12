package com.lingkj.project.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

    import java.io.Serializable;
import java.util.Date;

/**
 * 分享网站记录
 *
 * @author chenyongsong
 * @date 2019-09-23 16:03:51
 */
@Data
@TableName("user_share_log")
public class UserShareLog implements Serializable{
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
 * 分享网站url
 */
    private String url;
/**
 *
 */
    private Date createTime;

}
