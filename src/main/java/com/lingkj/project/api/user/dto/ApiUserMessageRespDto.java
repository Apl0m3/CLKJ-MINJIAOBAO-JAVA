package com.lingkj.project.api.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * ApiUserMessageRespDto
 *
 * @author chen yongsong
 * @className ApiUserMessageRespDto
 * @date 2019/10/21 20:28
 */
@Data
@ApiModel(value = "消息返回实体")
public class ApiUserMessageRespDto implements Comparable<ApiUserMessageRespDto> {
    private Long id;
    private Long userId;
    private ApiUserDto user;
    private Long senderId;
    private ApiUserDto senderUser;
    private String content;
    private Date createTime;
    private List<ApiUserMessageFileDto> list;
    @ApiModelProperty(value = "发送方 1 自己  2 对方")
    private Integer sendType;

    @Override
    public int compareTo(ApiUserMessageRespDto messageRespDto) {
        if (messageRespDto.getCreateTime().getTime() < this.createTime.getTime()) {
            return 1;
        } else if (messageRespDto.getCreateTime().getTime() > this.createTime.getTime()) {
            return -1;
        } else {
            return 0;
        }
    }
}
