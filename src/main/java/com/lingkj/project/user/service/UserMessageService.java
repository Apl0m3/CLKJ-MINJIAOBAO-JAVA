package com.lingkj.project.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.transaction.dto.ApiSendMessageDto;
import com.lingkj.project.user.entity.UserMessage;

import java.util.Map;

/**
 *
 *
 * @author chenyongsong
 * @date 2019-09-11 14:22:51
 */
public interface UserMessageService extends IService<UserMessage> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询订单是否存在信息消息
     * @param userId
     * @param transactionRecordId
     * @return
     */
    Integer countNewApi(Long userId, Long transactionRecordId);

    UserMessage queryNewMessage(Long userId);

    /**
     * 发送站内消息
     * @param sendMessageDto
     * @param userId
     */
    void sendMessage(ApiSendMessageDto sendMessageDto, Long userId);

    PageUtils queryUserMessageApi(Page page, Long userId, Long recordId, Long transactionCommodityId);

    void  updateStatusByRecordIdAll(Long userId,Long recordId, Long transactionCommodityId);
}

