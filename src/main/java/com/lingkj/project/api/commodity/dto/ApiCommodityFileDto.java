package com.lingkj.project.api.commodity.dto;

import lombok.Data;

/**
 * ApiCommodityFileDto
 *
 * @author chen yongsong
 * @className ApiCommodityFileDto
 * @date 2019/9/24 15:07
 */
@Data
public class ApiCommodityFileDto {

    private Long id;
    /**
     * 文件类型（1 图片，2 视频）
     */
    private Integer fileType;

    /**
     * 文件路劲
     */
    private String fileUrl;
}
