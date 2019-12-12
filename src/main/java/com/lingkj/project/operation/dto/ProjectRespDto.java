package com.lingkj.project.operation.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ProjectRespDto
 *
 * @author chen yongsong
 * @className ProjectRespDto
 * @date 2019/7/12 10:17
 */
@ApiModel("关于我们")
@Data
public class ProjectRespDto {
    /**
     *
     */
    private Long id;
    /**
     * logo
     */
    @ApiModelProperty("应用logo")
    private String companyLogo;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 应用名称
     */
    private String name;
    /**
     *
     */
    @ApiModelProperty("联系邮箱")
    private String email;
    /**
     *
     */
    @ApiModelProperty("联系地址")
    private String address;
    /**
     *
     */
    @ApiModelProperty("联系电话")
    private String phone;
    /**
     *
     */
    @ApiModelProperty("描述")
    private String remark;
}
