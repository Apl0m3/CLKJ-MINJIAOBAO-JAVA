package com.lingkj.project.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 设计师 申请 上传 案例url
 *
 * @author chenyongsong
 * @date 2019-09-12 11:54:40
 */
@Data
@TableName("user_application_file")
public class UserApplicationFile implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 申请id
     */
    private Long applicationId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     *
     */
    private String url;
    /**
     *
     */
    private Date createTime;
    /**
     * 申请文件类型（1 设计师 2 供应商 3 代理商）
     */
    private Integer type;

    //作品名称
//    private String name;

}
