package com.lingkj.project.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.authentication.paramsvalidation.SendCodeForm;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.message.entity.MessageEmailCodeLog;

import java.util.Map;

/**
 * 短信记录表
 *
 * @author chenyongsong
 *
 * @date 2019-06-27 10:02:37
 */
public interface MessageSmsLogService extends IService<MessageEmailCodeLog> {
    /*****************manage***********/
    /**
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /*****************api***********/
    void sendCode(SendCodeForm form);


}

