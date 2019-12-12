package com.lingkj.project.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:25
 */
@Data
@TableName("Commodity_user_collection")
public class CommodityUserCollection implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 商品id
     */
    private Long commodityId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     *
     */
    private Date createTime;


}
