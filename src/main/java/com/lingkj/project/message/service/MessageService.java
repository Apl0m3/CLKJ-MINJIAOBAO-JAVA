package com.lingkj.project.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.message.dto.MessageRespDto;
import com.lingkj.project.message.entity.Message;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:26
 */
public interface MessageService extends IService<Message> {

    PageUtils queryPage(Map<String, Object> params);

    void updateStatusByIds(List<Long> asList);

    PageUtils queryMessagePage(Page page, Long userId, Integer type);

    void addOrUpdate(Message message,Long userId);

    MessageRespDto queryMessage(Long id, Long userId);
    MessageRespDto  queryNewMessage(Long userId);

    /**
     * 统计用户未读消息
     * @param userId
     * @return
     */
    Integer countUnreadMessages(Long userId);
}

