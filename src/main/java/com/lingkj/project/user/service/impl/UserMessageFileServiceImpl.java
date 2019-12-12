package com.lingkj.project.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.api.user.dto.ApiUserMessageFileDto;
import com.lingkj.project.user.entity.UserMessageFile;
import com.lingkj.project.user.mapper.UserMessageFileMapper;
import com.lingkj.project.user.service.UserMessageFileService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 站内消息图片
 *
 * @author chenyongsong
 * @date 2019-10-21 20:04:11
 */
@Service
public class UserMessageFileServiceImpl extends ServiceImpl<UserMessageFileMapper, UserMessageFile> implements UserMessageFileService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserMessageFile> page = this.page(
                new Query<UserMessageFile>(params).getPage(),
                new QueryWrapper<UserMessageFile>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<ApiUserMessageFileDto> selectListByMessageIdApi(Long messageId) {
        return this.baseMapper.selectListByMessageIdApi(messageId);
    }

}
