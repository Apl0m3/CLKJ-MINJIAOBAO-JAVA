package com.lingkj.project.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.user.dto.UserAccountLogaAndTransactionIdDto;
import com.lingkj.project.user.entity.UserAccountLog;
import com.lingkj.project.user.mapper.UserAccountLogMapper;
import com.lingkj.project.user.service.UserAccountLogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



/**
 * 用户钱包记录
 *
 * @author chenyongsong
 * @date 2019-09-24 11:37:31
 */
@Service
public class UserAccountLogServiceImpl extends ServiceImpl<UserAccountLogMapper, UserAccountLog> implements UserAccountLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Long userId =(Long)params.get("userId");
        QueryWrapper<UserAccountLog> wrapper=new QueryWrapper<>();
        if(userId!=null) wrapper.eq("user_id",userId);
        IPage<UserAccountLog> page = this.page(
                new Query<UserAccountLog>(params).getPage(),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<UserAccountLogaAndTransactionIdDto> getUserAccountLogList(Long userId, Integer start, Integer end) {

        return this.baseMapper.getUserAccountLog(userId, start, end);
    }

    @Override
    public Integer getUserAccountLogCount(Long userId) {
        return this.baseMapper.queryUserAccountLogCount(userId);
    }

}
