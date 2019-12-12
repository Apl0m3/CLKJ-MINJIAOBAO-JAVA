package com.lingkj.project.api.operation.dto;

import com.lingkj.project.operation.entity.OperateAreas;
import lombok.Data;

import java.util.List;

/**
 * AreasDto
 *
 * @author chen yongsong
 * @className AreasDto
 * @date 2019/9/23 17:39
 */
@Data
public class AreasDto {
    private OperateAreas province;
    private OperateAreas city;
}
