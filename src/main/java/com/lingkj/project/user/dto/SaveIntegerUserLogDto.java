package com.lingkj.project.user.dto;

import lombok.Data;

@Data
public class SaveIntegerUserLogDto {
    private Long userId;
    private Integer integral;
    private String remark;
    private Long  integralUserId;
    private String transactionId;
}
