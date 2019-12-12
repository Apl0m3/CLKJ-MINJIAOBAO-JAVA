package com.lingkj.project.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.user.entity.UserApplicationFile;
import com.lingkj.project.user.mapper.UserApplicationFileMapper;
import com.lingkj.project.user.service.UserApplicationFileService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 设计师 申请 上传 案例url
 *
 * @author chenyongsong
 * @date 2019-09-12 11:54:40
 */
@Service
public class UserApplicationFileServiceImpl extends ServiceImpl<UserApplicationFileMapper, UserApplicationFile> implements UserApplicationFileService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserApplicationFile> page = this.page(
                new Query<UserApplicationFile>(params).getPage(),
                new QueryWrapper<UserApplicationFile>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<UserApplicationFile> getUserApplicationFileList(Long userId, Integer start, Integer end) {
        return this.baseMapper.getUserApplicationFile(userId, start, end);
    }

    @Override
    public Integer getUserApplicationFileCount(Long userId) {
        return this.baseMapper.queryUserApplicationFileCount(userId);
    }

    @Override
    public void deleteFile(Long applicationId, Integer type) {
        this.baseMapper.deleteUserFile(applicationId, type);
    }

    @Override
    public List<UserApplicationFile> getUserAndUserApplicationFileList(Long applicationId,Integer type) {
        QueryWrapper<UserApplicationFile> wrapper = new QueryWrapper<>();
        wrapper.eq("application_id", applicationId);
        wrapper.eq("type",type);
        return this.list(wrapper);
    }

    @Override
    public UserApplicationFile getUserApplicationFileOne(Long applicationId, Integer type) {
        QueryWrapper<UserApplicationFile> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("application_id", applicationId);
        if (type != null) {
            wrapper1.eq("type", 2);
        }
        return this.getOne(wrapper1);
    }

    @Override
    public void uploadWork(List<String> images, Long userId, Integer type) {
        List<UserApplicationFile> list = new ArrayList<>();
        UserApplicationFile userApplicationFile;
        for (String image : images) {
            userApplicationFile = new UserApplicationFile();
            userApplicationFile.setUrl(image);
            userApplicationFile.setType(type);
            userApplicationFile.setUserId(userId);
            userApplicationFile.setCreateTime(new Date());
            list.add(userApplicationFile);
        }
        if (list.size() > 0) {
            this.saveBatch(list);
        }
    }

}
