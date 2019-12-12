package com.lingkj.project.transaction.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

    import java.io.Serializable;
import java.util.Date;

/**
 * 
 *
 * @author chenyongsong
 * @date 2019-10-21 11:47:26
 */
@Data
@TableName("transaction_service_file")
public class TransactionServiceFile implements Serializable{
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
    private Long serviceId;
/**
 * 服务文件
 */
    private String image;

}
