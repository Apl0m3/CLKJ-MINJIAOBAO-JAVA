package com.lingkj.project.api.user.controller;


import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.message.dto.MessageRespDto;
import com.lingkj.project.message.entity.MessageUserLog;
import com.lingkj.project.message.service.MessageService;
import com.lingkj.project.message.service.MessageUserLogService;
import com.lingkj.project.transaction.entity.TransactionRecord;
import com.lingkj.project.transaction.service.TransactionRecordService;
import com.lingkj.project.user.dto.UserRespDto;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.entity.UserMessage;
import com.lingkj.project.user.service.AdminUserService;
import com.lingkj.project.user.service.UserMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javafx.scene.chart.ValueAxis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Api(value = "用户系统信息接口")
@RestController
@RequestMapping("/api/user/message")
public class ApiUserMessageController {
    /**
     * 系统通知
     */
    @Autowired
    private MessageService messageService;
    /**
     * 用户站内信
     */
    @Autowired
    private UserMessageService userMessageService;
    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private MessageUserLogService messageUserLogService;
    @Autowired
    private TransactionRecordService transactionRecordService;

    /**
     * 用户的系统信息
     *
     * @param page
     * @param userId
     * @return
     */
    @PostMapping("/userMessageList")
    @Login
    public R getUserMessageList(@RequestBody Page page, @RequestAttribute("userId") Long userId) {
        PageUtils pageUtils = messageService.queryMessagePage(page, userId, null);
        return R.ok().put("page", pageUtils);
    }

    /**
     * 查找用户收到的最新的未读消息（站内和系统）
     *
     * @param userId
     * @return
     */
    @GetMapping("/newMessage")
    @Login
    public R newMessage(@RequestAttribute("userId") Long userId) {
        UserMessage userMessage = userMessageService.queryNewMessage(userId);
        MessageRespDto dto=messageService.queryNewMessage(userId);
        UserRespDto userRespDto = null;
        if (userMessage != null) {
            userRespDto = adminUserService.queryPersonInfo(userMessage.getSenderId());
            if(userMessage.getRecordId()!=null){
                TransactionRecord byId = transactionRecordService.getById(userMessage.getRecordId());
                if(byId!=null){
                    User use = adminUserService.getById(userId);
                    if(byId.getUserId().equals(userId)&&use.getUserRoleId().equals(User.member_role_designer)){
                        userMessage.setType(1);
                    }else {
                        userMessage.setType(0);
                    }
                }
            }
        }
        if (userRespDto != null && userRespDto.getAvatar() != null) {
            return R.ok().put("userMessage", userMessage).put("sendUserAvatar", userRespDto.getAvatar()).put("sysMessage",dto);
        } else {
            return R.ok().put("userMessage", userMessage).put("sysMessage",dto);
        }

    }

    /**
     * 设置系统消息已读，并记录已读日志
     *
     * @param messageId
     * @return
     */
    @GetMapping("/setMessageUserLog")
    @Login
    public R setMessageUserLog(@RequestAttribute("userId") Long userId, @RequestParam("messageId") Long messageId) {
        MessageUserLog messageUserLogOne = messageUserLogService.getMessageUserLogOne(userId, messageId);
        if (messageUserLogOne != null) {
            return R.ok();
        }
        MessageUserLog messageUserLog = new MessageUserLog();
        messageUserLog.setCreateTime(new Date());
        messageUserLog.setMessageId(messageId);
        messageUserLog.setUserId(userId);
        messageUserLog.setStatus(0);
        messageUserLogService.save(messageUserLog);
        return R.ok();
    }

    @Login
    @ApiOperation(value = "查询站内信消息")
    @GetMapping("/queryUserMessage")
    public R queryUserMessageApi(Page page, Long recordId, Long transactionCommodityId, @RequestAttribute("userId") Long userId) {
        PageUtils pageUtils = userMessageService.queryUserMessageApi(page, userId, recordId, transactionCommodityId);
        return R.ok().put("page", pageUtils);
    }
}
