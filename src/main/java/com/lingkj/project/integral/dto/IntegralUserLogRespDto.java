package com.lingkj.project.integral.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * IntegralUserRespDto
 *
 * @author chen yongsong
 * @className IntegralUserRespDto
 * @date 2019/7/5 18:21
 */
@ApiModel("积分记录返回参数")
@Data
public class IntegralUserLogRespDto {
    private Integer changeValue;
    private Date createTime;
    private String remark;
    private Integer type;

}
