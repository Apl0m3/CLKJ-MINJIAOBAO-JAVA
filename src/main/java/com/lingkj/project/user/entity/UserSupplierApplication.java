package com.lingkj.project.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lingkj.project.user.dto.CommissionDto;
import lombok.Data;

    import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户供应商 申请  表
 *
 * @author chenyongsong
 * @date 2019-09-12 09:17:38
 */
@Data
@TableName("user_supplier_application")
public class UserSupplierApplication implements Serializable{
private static final long serialVersionUID=1L;


    @TableId
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 联系方式
     * @author XXX <XXX@163.com>
     * @date 2019/11/4 11:04
     * @param
     * @return
     */
    @TableField(exist = false)
    private String userName;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 公司网站
     */
    private String companyWebsite;
    /**
     * 联系人
     */
    private String phone;
    /**
     * 申请时间
     */
    private Date createTime;
    /**
     * 0 提交审核 1 审核成功  2 审核失败
     */
    private Integer status;
    /**
     * 失败原因
     */
    private String reason;
    /**
     * 审核时间
     */
    private Date applicationTime;
    /**
     * 审核人员
     */
    private Long applicationBy;
    /**
     * 相关供应产品的ids
     */
    private String typeIds;
    /**
     * 审核人员名称
     */
    @TableField(exist = false)
    private String applicationByName;
    /**
     * 供应产品选择
     */
    @TableField(exist = false)
    private String supplyProducts;
    /**
     * 相关报价表文件url
     */
    @TableField(exist = false)
    private List<String> applicationFiles;

    /**
     * 佣金信息
     */
    @TableField(exist = false)
    private List<CommissionDto> commissionDto;

    private  String name;

}
