package com.lingkj.project.api.user.dto;

import lombok.Data;

/**
 * ApiUserMessageFileDto
 *
 * @author chen yongsong
 * @className ApiUserMessageFileDto
 * @date 2019/10/21 20:30
 */
@Data
public class ApiUserMessageFileDto {
    private Long id;
    private Long messageId;
    private String url;
}
