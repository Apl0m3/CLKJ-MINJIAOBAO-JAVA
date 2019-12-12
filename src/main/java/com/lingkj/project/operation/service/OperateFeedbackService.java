package com.lingkj.project.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.operation.dto.OperateFeedBackReqDto;
import com.lingkj.project.operation.entity.OperateFeedback;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 意见反馈
 *
 * @author chenyongsong
 *
 * @date 2019-07-10 10:36:07
 */
public interface OperateFeedbackService extends IService<OperateFeedback> {

    PageUtils queryPage(Map<String, Object> params);


    void reply(OperateFeedBackReqDto operateFeedBackReqDto, Long sysId, HttpServletRequest request);
}

