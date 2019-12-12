package com.lingkj.project.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class UserCollectionDto {
    private Long id;
    private String name;
    private String images;
    private Date createTime;
}
