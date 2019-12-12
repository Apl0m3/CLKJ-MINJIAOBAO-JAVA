package com.lingkj.project.transaction.dto;

import lombok.Data;

/**
 * ReviewManuscriptReqDto
 *
 * @author chen yongsong
 * @className ReviewManuscriptReqDto
 * @date 2019/10/18 16:45
 */
@Data
public class ReviewManuscriptReqDto {
    private Long transactionCommodityId;
    private Integer status;
    private String reason;
}
