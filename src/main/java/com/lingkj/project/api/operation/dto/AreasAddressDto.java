package com.lingkj.project.api.operation.dto;

import com.lingkj.project.operation.entity.OperateAreas;
import lombok.Data;

import java.util.List;

@Data
public class AreasAddressDto {
    private String provinceName;
    private Long provinceId;
    private List<OperateAreas> city;
}
