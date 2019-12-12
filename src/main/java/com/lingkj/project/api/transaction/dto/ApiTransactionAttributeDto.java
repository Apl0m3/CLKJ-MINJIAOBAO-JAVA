package com.lingkj.project.api.transaction.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ApiTransactionAttributeDto
 *
 * @author chen yongsong
 * @className ApiTransactionAttributeDto
 * @date 2019/10/9 16:51
 */
@Data
public class ApiTransactionAttributeDto {
    /**
     *
     */
    private Long id;
    /**
     * 属性名称
     */
    private String name;

    /**
     * 描述
     */
    private String remark;
    /**
     * 父类 attribute id
     */
    private Long parentId;
    /**
     * 分类 1：材料 2：尺寸 3：包装
     */
    private Integer category;
    /**
     * 属性类型 0：默认 1：输入 2：选择
     */
    private Integer type;

    /**
     * 选择类型 0：文字描述 1：图片 2：图文1 3：图文2
     */
    private Integer selectType;
    /**
     * 按数量计算金额
     */
    private Integer quantity;

    @ApiModelProperty("输入类型的值")
    private String inputValue;
    /**
     * 排序
     */
    private Integer sort;

    private ApiTransactionAttributeValueDto attributeValue;
}
