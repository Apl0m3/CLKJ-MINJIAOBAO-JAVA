package com.lingkj.project.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.user.entity.UserApplicationFile;

import java.util.List;
import java.util.Map;

/**
 * 设计师 申请 上传 案例url
 *
 * @author chenyongsong
 * @date 2019-09-12 11:54:40
 */
public interface UserApplicationFileService extends IService<UserApplicationFile> {

    PageUtils queryPage(Map<String, Object> params);
    List<UserApplicationFile> getUserApplicationFileList( Long userId, Integer start,  Integer end);
    Integer getUserApplicationFileCount(Long userId);
    void deleteFile( Long applicationId, Integer type);
    List<UserApplicationFile> getUserAndUserApplicationFileList(Long applicationId,Integer type);
    UserApplicationFile getUserApplicationFileOne(Long applicationId,Integer type);

    /**
     * 上传左边
     * @param images
     * @param userId
     * @param type
     */
    void uploadWork(List<String> images, Long userId, Integer type);
}

