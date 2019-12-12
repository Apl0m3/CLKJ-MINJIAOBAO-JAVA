package com.lingkj.project.api.user.dto;

import lombok.Data;

@Data
public class AccountBindingDto {
    private String userEmail;
    private String tripartiteId;
    private Integer type;
    private  String  passWord;
    private   String code;
    private String uuid;
}
