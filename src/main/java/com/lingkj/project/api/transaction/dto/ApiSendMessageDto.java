package com.lingkj.project.api.transaction.dto;

import lombok.Data;

/**
 * ApiSendMessageDto
 *
 * @author chen yongsong
 * @className ApiSendMessageDto
 * @date 2019/10/18 9:24
 */
@Data
public class ApiSendMessageDto {
    private Long transactionCommodityId;
    private String message;
    private String fileUrl;
}
