package com.lingkj.project.user.dto;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class UpdateUserIntegralReqDto {

    private Long id;

    private Integer integral;

    private Integer status;

}
