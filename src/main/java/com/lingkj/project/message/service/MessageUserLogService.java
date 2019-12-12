package com.lingkj.project.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.message.entity.MessageUserLog;

import java.util.Map;

/**
 *
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:25
 */
public interface MessageUserLogService extends IService<MessageUserLog> {

    PageUtils queryPage(Map<String, Object> params);

    Integer selectCountById(Long messageId, Long userId);

    MessageUserLog  getMessageUserLogOne(Long userId,Long messageId);
}

