package com.lingkj.project.user.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SupplierCommissionDto {

    private long id;
    private String companyName;
    private String companyWebsite;
    private String phone;
    private Date createTime;
    private int status;
    private String reason;
    private Date applicationTime;
    private int applicationBy;
    private int userId;
    private String typeIds;

}
