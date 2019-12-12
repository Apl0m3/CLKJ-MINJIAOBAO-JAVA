package com.lingkj.project.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.user.entity.UserAgentRate;

import java.util.Map;

/**
 * 
 *
 * @author chenyongsong
 * @date 2019-11-21 10:39:51
 */
public interface UserAgentRateService extends IService<UserAgentRate> {

    PageUtils queryPage(Map<String, Object> params);

    UserAgentRate selectByUserIdAndAgentId(Long userId, Long commodityTypeId);
}

