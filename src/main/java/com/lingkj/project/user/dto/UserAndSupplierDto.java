package com.lingkj.project.user.dto;

import com.lingkj.project.user.entity.UserApplicationFile;
import com.lingkj.project.user.entity.UserBank;
import com.lingkj.project.user.entity.UserSupplierApplication;
import lombok.Data;

@Data
public class UserAndSupplierDto {
    private UserApplicationFile userApplicationFile;
    private UserRespDto userRespDto;
    private UserSupplierApplication userSupplierApplication;
    private UserBank userBank;
}
