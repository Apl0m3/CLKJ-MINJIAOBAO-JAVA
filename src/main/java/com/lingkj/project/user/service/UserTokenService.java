package com.lingkj.project.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.user.entity.UserToken;

import java.util.Map;

/**
 * 用户Token
 *
 * @author chenyongsong
 *
 * @date 2019-07-05 10:57:20
 */
public interface UserTokenService extends IService<UserToken> {

    PageUtils queryPage(Map<String, Object> params);


    UserToken queryByToken(String token);
}

