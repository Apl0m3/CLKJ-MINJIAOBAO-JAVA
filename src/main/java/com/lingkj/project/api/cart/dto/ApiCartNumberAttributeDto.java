package com.lingkj.project.api.cart.dto;

import lombok.Data;

/**
 * ApiCartNumberAttributeDto
 *
 * @author chen yongsong
 * @className ApiCartNumberAttributeDto
 * @date 2019/10/22 18:12
 */
@Data
public class ApiCartNumberAttributeDto {
    private Long id;
    /**
     * cart id
     */
    private Long cartId;
    /**
     * 属性名称
     */
    private String name;
    /**
     * 	值
     */
    private Integer num;
}
