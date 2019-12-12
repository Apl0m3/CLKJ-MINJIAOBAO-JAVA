package com.lingkj.project.api.commodity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * ApiCommodityAttributeDto
 *
 * @author chen yongsong
 * @className ApiCommodityAttributeDto
 * @date 2019/9/25 9:14
 */
@ApiModel("属性表返回")
@Data
public class ApiCommodityAttributeDto {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("属性名称")
    private String name;

    @ApiModelProperty("数量")
    private BigDecimal quantity;

    @ApiModelProperty("金额")
    private BigDecimal amount;

    @ApiModelProperty("描述")
    private String remark;

    @ApiModelProperty("属性类型 0：默认 1：输入 2：选择")
    private Integer type;

    @ApiModelProperty("选择类型 0：文字描述 1：图片 2：图文1 3：图文2")
    private Integer selectType;

    @ApiModelProperty("分类 1：材料 2：尺寸 3：包装")
    private Integer category;


    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("输入类型的值")
    private Integer inputValue;

    @ApiModelProperty("属性值列表")
    private List<ApiCommodityAttributeValueDto> list;
}
