package com.lingkj.project.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

    import java.io.Serializable;
import java.util.Date;

/**
 *
 *
 * @author chenyongsong
 * @date 2019-08-03 09:17:56
 */
@Data
@TableName("logistics_sms_num")
public class LogisticsSmsNum implements Serializable{
private static final long serialVersionUID=1L;


@TableId
    private Integer id;

    private Integer type;

    private Integer num;
    @TableField(exist = false)
    private Integer remainingNumber;

    private Date createTime;

}
