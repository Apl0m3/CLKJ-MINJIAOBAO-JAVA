package com.lingkj.project.operation.dto;

import lombok.Data;

@Data
public class OperateTypeAndUserDto {
    private Long id;
    private String name;

    // 0是没有这项技能，1是有
    private Integer status;
}
