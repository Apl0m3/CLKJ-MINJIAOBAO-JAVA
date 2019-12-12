package com.lingkj.project.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.user.entity.UserToken;
import com.lingkj.project.user.mapper.UserTokenMapper;
import com.lingkj.project.user.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * @author Administrator
 */
@Service
public class UserTokenServiceImpl extends ServiceImpl<UserTokenMapper, UserToken> implements UserTokenService {
    @Autowired
    private UserTokenMapper userTokenMapper;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserToken> page = this.page(
                new Query<UserToken>(params).getPage(),
                new QueryWrapper<UserToken>()
        );

        return new PageUtils(page);
    }

    @Override
    public UserToken queryByToken(String token) {
        return userTokenMapper.queryByToken(token);
    }

}
