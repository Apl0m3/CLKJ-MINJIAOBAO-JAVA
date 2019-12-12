package com.lingkj.project.api.transaction.dto;

import lombok.Data;

import java.util.List;

/**
 * ApiUploadManuscriptDto
 *
 * @author chen yongsong
 * @className ApiUploadManuscriptDto
 * @date 2019/10/17 17:22
 */
@Data
public class ApiUploadManuscriptDto {
    private List<String>  images;
    private Long deliveryId;
    private String manuscriptUrl;
    private  Long transactionCommodityId;
}
