package com.lingkj.project.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.api.transaction.dto.ApiSendMessageDto;
import com.lingkj.project.api.user.dto.ApiUserDto;
import com.lingkj.project.api.user.dto.ApiUserMessageFileDto;
import com.lingkj.project.api.user.dto.ApiUserMessageRespDto;
import com.lingkj.project.transaction.entity.TransactionCommodity;
import com.lingkj.project.transaction.entity.TransactionCommodityDelivery;
import com.lingkj.project.transaction.entity.TransactionRecord;
import com.lingkj.project.transaction.service.TransactionCommodityDeliveryService;
import com.lingkj.project.transaction.service.TransactionCommodityService;
import com.lingkj.project.transaction.service.TransactionRecordService;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.entity.UserMessage;
import com.lingkj.project.user.entity.UserMessageFile;
import com.lingkj.project.user.mapper.UserMessageMapper;
import com.lingkj.project.user.service.AdminUserService;
import com.lingkj.project.user.service.UserMessageFileService;
import com.lingkj.project.user.service.UserMessageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author chenyongsong
 * @date 2019-09-11 14:22:51
 */
@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage> implements UserMessageService {
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    @Lazy
    private TransactionCommodityService transactionCommodityService;
    @Autowired
    @Lazy
    private TransactionRecordService transactionRecordService;
    @Autowired
    @Lazy
    private UserMessageFileService userMessageFileService;
    @Autowired
    @Lazy
    private TransactionCommodityDeliveryService transactionCommodityDeliveryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<UserMessage> wrapper = new QueryWrapper<>();
        String title = (String) params.get("title");
        Integer readStatus = Integer.valueOf(params.get("readStatus").toString());
        Long userId = Long.valueOf(params.get("userId").toString());
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        if (StringUtils.isNotBlank(title)) {
            wrapper.like("title", title);
        }
        if (readStatus != null) {
            wrapper.eq("read_status", readStatus);
        }
        wrapper.orderByDesc("create_time");
        IPage<UserMessage> page = this.page(
                new Query<UserMessage>(params).getPage(),
                wrapper
        );
        for (UserMessage userMessage : page.getRecords()) {
            User accpetUser = adminUserService.selectInfoById(userMessage.getUserId());
            userMessage.setAcceptUserName(accpetUser.getUserName());
            User senderUser = adminUserService.selectInfoById(userMessage.getSenderId());
            userMessage.setSenderUserName(senderUser.getUserName());
        }

        return new PageUtils(page);
    }

    @Override
    public Integer countNewApi(Long userId, Long transactionRecordId) {
        return this.baseMapper.countNewApi(userId, transactionRecordId);
    }

    @Override
    public UserMessage queryNewMessage(Long userId) {
        return this.baseMapper.newestMessage(userId);
    }

    @Override
    public void sendMessage(ApiSendMessageDto sendMessageDto, Long userId) {
        TransactionCommodity commodity = transactionCommodityService.getById(sendMessageDto.getTransactionCommodityId());
        if (commodity != null) {
            TransactionRecord transactionRecord = transactionRecordService.getById(commodity.getRecordId());
            UserMessage userMessage = new UserMessage();
            userMessage.setReadStatus(0);
            userMessage.setSenderId(userId);
            userMessage.setContent(sendMessageDto.getMessage());
            userMessage.setTransactionCommodityId(commodity.getId());
            userMessage.setRecordId(commodity.getRecordId());
            //判断订单是否为本人订单发起人 是则接受人为设计师
            if (transactionRecord.getUserId().equals(userId)) {
                TransactionCommodityDelivery commodityDelivery = this.transactionCommodityDeliveryService.selectById(transactionRecord.getId(), commodity.getId(), TransactionCommodityDelivery.TYPE_DESIGNER);
                userMessage.setUserId(commodityDelivery.getUserId());
            } else {
                userMessage.setUserId(transactionRecord.getUserId());
            }

            userMessage.setCreateTime(new Date());
            this.save(userMessage);
            if (StringUtils.isNotBlank(sendMessageDto.getFileUrl())) {
                UserMessageFile userMessageFile = new UserMessageFile();
                userMessageFile.setCreateTime(new Date());
                userMessageFile.setUserId(userId);
                userMessageFile.setMessageId(userMessage.getId());
                userMessageFile.setUrl(sendMessageDto.getFileUrl());
                userMessageFileService.save(userMessageFile);
            }

        }
    }

    @Override
    public PageUtils queryUserMessageApi(Page page, Long userId, Long recordId, Long transactionCommodityId) {
        Integer total = this.baseMapper.selectCountById(userId, recordId, transactionCommodityId);
        List<ApiUserMessageRespDto> list = this.baseMapper.selectListById(userId, recordId, transactionCommodityId, page.getLimit(), page.getPageSize());
        for (ApiUserMessageRespDto messageRespDto : list) {
            ApiUserDto user = adminUserService.selectApiUserDto(messageRespDto.getUserId());
            ApiUserDto sendUser = adminUserService.selectApiUserDto(messageRespDto.getSenderId());
            List<ApiUserMessageFileDto> fileList = userMessageFileService.selectListByMessageIdApi(messageRespDto.getId());
            messageRespDto.setList(fileList);
            messageRespDto.setUser(user);
            messageRespDto.setSenderUser(sendUser);
            messageRespDto.setSendType(userId.equals(messageRespDto.getUserId()) ? 1 : 2);
        }
        Collections.sort(list);
        return new PageUtils(list, total, page.getPageSize(), page.getPage());
    }

    @Override
    public void updateStatusByRecordIdAll(Long userId, Long recordId, Long transactionCommodityId) {
        this.baseMapper.updateStatusByRecordId(userId,recordId,transactionCommodityId);
    }

}
