package com.lingkj.project.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenyongsong
 * @date 2019-07-04 09:50:39
 */
@Data
@TableName("commodity_comment_file")
public class CommodityCommentFile implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 文件类型（1 图片，2 视频）
     */
    private Integer fileType;
    /**
     * 评论id
     */
    private Long commodityCommentId;
    /**
     * 文件路劲
     */
    private String fileUrl;
    /**
     *
     */
    private Date createTime;

}
