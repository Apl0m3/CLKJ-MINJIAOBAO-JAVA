package com.lingkj.project.user.entity;

import lombok.Data;

import java.util.Date;

@Data
public class InvitationUserIntegralLog {
    private Long id;
    private Long userId;
    private Integer integral;
    private Long invitedUserId;
    private Date createTime;
    private String transactionId;
}
