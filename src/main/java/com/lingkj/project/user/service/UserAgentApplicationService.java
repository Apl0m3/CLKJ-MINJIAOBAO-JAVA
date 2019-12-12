package com.lingkj.project.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.user.dto.UserAndAgentApplicationDto;
import com.lingkj.project.user.entity.UserAgentApplication;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用户 代理商申请
 *
 * @author chenyongsong
 * @date 2019-10-08 16:23:51
 */
public interface UserAgentApplicationService extends IService<UserAgentApplication> {

    PageUtils queryPage(Map<String, Object> params);
    UserAgentApplication  getUserAgentApplicationOne(Long userId);
    Integer getUserAgentApplicationCount(Long userId);
    void  saveAgent(UserAndAgentApplicationDto userAndAgentApplicationDto, Long userId, HttpServletRequest request);
}

