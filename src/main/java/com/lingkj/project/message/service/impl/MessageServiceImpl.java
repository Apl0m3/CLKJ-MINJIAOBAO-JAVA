package com.lingkj.project.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.DateUtils;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.common.validator.Assert;
import com.lingkj.project.message.dto.MessageRespDto;
import com.lingkj.project.message.entity.Message;
import com.lingkj.project.message.entity.MessageUserLog;
import com.lingkj.project.message.mapper.MessageMapper;
import com.lingkj.project.message.service.MessageService;
import com.lingkj.project.message.service.MessageUserLogService;
import com.lingkj.project.sys.entity.SysUserEntity;
import com.lingkj.project.sys.service.SysUserService;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.service.AdminUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private MessageUserLogService userMessageLogService;
    @Autowired
    private AdminUserService adminUserService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        params.put("sidx", "create_time");
        params.put("order", "DESC");
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        String type = (String) params.get("type");
        String title = (String) params.get("title");
        Long userId = (Long) params.get("userId");
        if (userId != null) wrapper.eq("user_id", userId);
        if (StringUtils.isNotBlank(title)) wrapper.like("title", title);
        if (StringUtils.isNotBlank(type)) wrapper.eq("type", Integer.valueOf(type));

        IPage<Message> page = this.page(
                new Query<Message>(params).getPage(),
                wrapper.orderByDesc("create_time")
        );
        for (Message message : page.getRecords()) {
            if (message.getType() != null) {
                if (message.getType() == 2) {
                    if (message.getUserId() != null) {
                        User user = adminUserService.getById(message.getUserId());
                        message.setUserName(user.getUserName());
                        message.setCreateSysUserName("system");
                    }

                } else {
                    SysUserEntity entity = sysUserService.getById(message.getCreateSysUserId());
                    message.setCreateSysUserName(entity.getUsername());
                    if (message.getUpdateSysUserId() != null) {
                        entity = sysUserService.getById(message.getUpdateSysUserId());
                        message.setCreateSysUserName(entity.getUsername());
                    }
                }
            }
        }
        return new PageUtils(page);
    }

    @Override
    public void updateStatusByIds(List<Long> asList) {
        baseMapper.updateStatusByIds(asList);
    }

    @Override
    public PageUtils queryMessagePage(com.lingkj.common.bean.entity.Page page, Long userId, Integer type) {
        User user = adminUserService.getById(userId);
        Integer totalCount = baseMapper.queryMessageCount(userId, type, user.getCreateTime());
        List<MessageRespDto> list = baseMapper.queryMessageList(page.getPageSize(), page.getLimit(), userId, type, user.getCreateTime());
        return new PageUtils(list, totalCount, page.getPageSize(), page.getPage());
    }

    @Override
    public void addOrUpdate(Message message, Long userId) {
        Date currentTime = DateUtils.current();
        if (message.getId() == null) {
            message.setCreateSysUserId(userId);
            message.setCreateTime(currentTime);
            message.setStatus(0);
            save(message);
        } else {
            message.setUpdateSysUserId(userId);
            message.setUpdateTime(currentTime);
            updateById(message);
        }
    }

    @Override
    public MessageRespDto queryMessage(Long id, Long userId) {
        MessageRespDto objectMap = this.baseMapper.selectByUserId(id, userId);
        Assert.isNull(objectMap, "此消息不存在", 30001);
        Integer totalCount = userMessageLogService.selectCountById(id, userId);
        if (totalCount <= 0) {
            MessageUserLog messageUserLog = new MessageUserLog();
            messageUserLog.setCreateTime(DateUtils.current());
            messageUserLog.setMessageId(id);
            messageUserLog.setUserId(userId);
            userMessageLogService.save(messageUserLog);
        }
        return objectMap;
    }

    @Override
    public MessageRespDto queryNewMessage(Long userId) {
        User user = adminUserService.getById(userId);
        Page page = new Page(1, 1);
        List<MessageRespDto> list = baseMapper.queryMessageList(page.getPageSize(), page.getLimit(), userId, null, user.getCreateTime());
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Integer countUnreadMessages(Long userId) {
        User user = adminUserService.getById(userId);
        return baseMapper.countUnreadMessages(userId, user.getCreateTime());
    }

}
