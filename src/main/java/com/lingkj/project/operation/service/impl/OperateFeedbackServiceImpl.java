package com.lingkj.project.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.project.message.entity.Message;
import com.lingkj.project.message.service.MessageService;
import com.lingkj.project.operation.dto.OperateFeedBackReqDto;
import com.lingkj.project.operation.entity.OperateFeedback;
import com.lingkj.project.operation.mapper.OperateFeedbackMapper;
import com.lingkj.project.operation.service.OperateFeedbackService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * @author Administrator
 */
@Service
public class OperateFeedbackServiceImpl extends ServiceImpl<OperateFeedbackMapper, OperateFeedback> implements OperateFeedbackService {

    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageUtils messageUtils;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
//        params.put("sidx","create_time");
//        params.put("order","DESC");
        String processingStatus = (String) params.get("processingStatus");
        QueryWrapper<OperateFeedback> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(processingStatus))
            wrapper.eq("processing_status", Integer.valueOf(processingStatus));
        wrapper.orderByAsc("processing_status");
        wrapper.orderByDesc("create_time");

        IPage<OperateFeedback> page = this.page(
                new Query<OperateFeedback>(params).getPage(),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void reply(OperateFeedBackReqDto operateFeedBackReqDto, Long sysId, HttpServletRequest request) {
        //获取反馈信息
        OperateFeedback operateFeedback = this.baseMapper.selectById(operateFeedBackReqDto.getId());
        //修改反馈信息状态
        operateFeedback.setProcessingStatus(1);
        this.updateById(operateFeedback);
        //添加用户消息

        Message message = new Message();
        message.setUserId(operateFeedback.getUserId());
        message.setTitle(messageUtils.getMessage("manage.feedback.reply", request));
        message.setContent(operateFeedBackReqDto.getContent());
        message.setType(2);
        messageService.addOrUpdate(message, sysId);


    }

}
