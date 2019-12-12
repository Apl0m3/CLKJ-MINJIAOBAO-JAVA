package com.lingkj.project.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.project.user.entity.UserAgentRate;
import com.lingkj.project.user.mapper.UserAgentRateMapper;
import com.lingkj.project.user.service.UserAgentRateService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;

/**
 * 
 *
 * @author chenyongsong
 * @date 2019-11-21 10:39:51
 */
@Service
public class UserAgentRateServiceImpl extends ServiceImpl<UserAgentRateMapper, UserAgentRate> implements UserAgentRateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserAgentRate> page = this.page(
                new Query<UserAgentRate>(params).getPage(),
                new QueryWrapper<UserAgentRate>()
        );

        return new PageUtils(page);
    }

    @Override
    public UserAgentRate selectByUserIdAndAgentId(Long userId, Long commodityTypeId) {
        QueryWrapper<UserAgentRate> wrapper=new QueryWrapper<UserAgentRate>();
        wrapper.eq("user_id",userId);
        wrapper.eq("commodity_type_id",commodityTypeId);
        UserAgentRate one = getOne(wrapper);
        return one;
    }

}
