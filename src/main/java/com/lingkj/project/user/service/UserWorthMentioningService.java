package com.lingkj.project.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.user.dto.AccountBindingDto;
import com.lingkj.project.user.entity.UserToken;
import com.lingkj.project.user.entity.UserWorthMentioning;

import java.util.Map;

/**
 * 用户Token
 *
 * @author chenyongsong
 *
 * @date 2019-07-05 10:57:20
 */
public interface UserWorthMentioningService extends IService<UserWorthMentioning> {
    Map<String,Object> accountBinding(AccountBindingDto accountBindingDto);
    Map<String,Object> loginCheck(String type,String tripartiteId);
}

