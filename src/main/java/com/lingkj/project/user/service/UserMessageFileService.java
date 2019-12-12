package com.lingkj.project.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.user.dto.ApiUserMessageFileDto;
import com.lingkj.project.user.entity.UserMessageFile;

import java.util.List;
import java.util.Map;

/**
 * 站内消息图片
 *
 * @author chenyongsong
 * @date 2019-10-21 20:04:11
 */
public interface UserMessageFileService extends IService<UserMessageFile> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询消息图片
     * @param messageId
     * @return
     */
    List<ApiUserMessageFileDto> selectListByMessageIdApi(Long messageId);
}

