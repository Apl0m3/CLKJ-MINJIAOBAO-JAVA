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
 * @date 2019-09-20 15:40:52
 */
@Data
@TableName("user_collection_commodity")
public class UserCollectionCommodity implements Serializable{
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
 *
 */
    private Long commodityId;
/**
 *
 */
    private Date createTime;

}
