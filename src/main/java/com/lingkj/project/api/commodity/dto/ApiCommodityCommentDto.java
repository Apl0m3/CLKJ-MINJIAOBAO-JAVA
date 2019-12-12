package com.lingkj.project.api.commodity.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * ApiCommodityCommentDto
 *
 * @author chen yongsong
 * @className ApiCommodityCommentDto
 * @date 2019/9/24 17:05
 */
@Data
public class ApiCommodityCommentDto {
    private Long id;
    private String name;
    private String avatar;
    private Date createTime;
    private String comment;
    private List<String> files;

}
