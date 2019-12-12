package com.lingkj.project.message.dto;

import lombok.Data;

import java.util.Date;

/**
 * MessageRespDto
 *
 * @author chen yongsong
 * @className MessageRespDto
 * @date 2019/8/14 9:45
 */
@Data
public class MessageRespDto {
    private Long id;
    private String title;
    private Integer type;
    private String content;
    private String transactionId;
    private Integer readStatus;
    private Date createTime;
}
