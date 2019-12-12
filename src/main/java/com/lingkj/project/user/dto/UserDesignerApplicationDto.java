package com.lingkj.project.user.dto;


import com.lingkj.project.user.entity.UserApplicationFile;
import com.lingkj.project.user.entity.UserBank;
import com.lingkj.project.user.entity.UserDesignerApplication;
import lombok.Data;

import java.util.List;

@Data
public class UserDesignerApplicationDto {
    private UserDesignerApplication userDesignerApplication;
    private List<UserApplicationFile> userApplicationFiles;
    private UserRespDto userRespDto;
    private UserBank userBank;

}
