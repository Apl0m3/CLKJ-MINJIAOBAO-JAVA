package com.lingkj.project.api.transaction.dto;

import com.lingkj.project.commodity.entity.CommodityExpect;
import com.lingkj.project.commodity.entity.CommodityLadder;
import lombok.Data;

/**
 * ApiTransactionLadderExpectDto
 *
 * @author chen yongsong
 * @className ApiTransactionLadderExpectDto
 * @date 2019/10/9 17:13
 */
@Data
public class ApiTransactionLadderExpectDto {
    private CommodityLadder ladder;
    private CommodityExpect expect;
    private Integer totalQuantity;
}
