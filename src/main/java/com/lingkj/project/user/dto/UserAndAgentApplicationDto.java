package com.lingkj.project.user.dto;

import com.lingkj.project.user.entity.UserAgentApplication;
import lombok.Data;

@Data
public class UserAndAgentApplicationDto {
    private UserRespDto userRespDto;
    private UserAgentApplication userAgentApplication;

}
