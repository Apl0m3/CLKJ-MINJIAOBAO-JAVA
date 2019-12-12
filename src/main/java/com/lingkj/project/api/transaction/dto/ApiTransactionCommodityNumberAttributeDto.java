package com.lingkj.project.api.transaction.dto;

import lombok.Data;

/**
 * ApitransactioncommodityNumberAttirbuteDto
 *
 * @author chen yongsong
 * @className ApitransactioncommodityNumberAttirbuteDto
 * @date 2019/10/24 16:37
 */
@Data
public class ApiTransactionCommodityNumberAttributeDto {

    /**
     * 属性名称
     */
    private String name;
    /**
     * 值
     */
    private Integer num = 0;
}
