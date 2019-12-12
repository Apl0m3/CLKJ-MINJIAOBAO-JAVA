package com.lingkj.project.hot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

    import java.io.Serializable;
import java.util.Date;

/**
 * 
 *
 * @author chenyongsong
 * @date 2019-11-11 09:52:12
 */
@Data
@TableName("hot_words")
public class HotWords implements Serializable{
private static final long serialVersionUID=1L;

/**
 * 
 */
        @TableId
    private Long id;
/**
 * 热词名称
 */
    private String name;
/**
 * 热词搜索数量
 */
    private Integer num;
/**
 * 创建与修改时间
 */
    private Date modifyTime;

}
