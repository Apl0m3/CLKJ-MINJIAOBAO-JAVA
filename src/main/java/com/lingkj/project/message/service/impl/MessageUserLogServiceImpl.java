package com.lingkj.project.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.message.entity.MessageUserLog;
import com.lingkj.project.message.mapper.MessageUserLogMapper;
import com.lingkj.project.message.service.MessageUserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * @author Administrator
 */
@Service
public class MessageUserLogServiceImpl extends ServiceImpl<MessageUserLogMapper, MessageUserLog> implements MessageUserLogService {
    @Autowired
    private MessageUserLogMapper messageUserLogMapper;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        params.put("sidx","create_time");
        params.put("order","DESC");
        IPage<MessageUserLog> page = this.page(
                new Query<MessageUserLog>(params).getPage(),
                new QueryWrapper<MessageUserLog>()
        );

        return new PageUtils(page);
    }

    @Override
    public Integer selectCountById(Long messageId, Long userId) {
        return messageUserLogMapper.selectCountById(messageId, userId);
    }

    @Override
    public MessageUserLog getMessageUserLogOne(Long userId, Long messageId) {
        QueryWrapper<MessageUserLog> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("message_id",messageId);
        wrapper.eq("status",0);
        return  this.getOne(wrapper);
    }

}
