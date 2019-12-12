package com.lingkj.project.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.user.dto.UserAccountLogaAndTransactionIdDto;
import com.lingkj.project.user.entity.UserAccountLog;

import java.util.List;
import java.util.Map;

/**
 * 用户钱包记录
 *
 * @author chenyongsong
 * @date 2019-09-24 11:37:31
 */
public interface UserAccountLogService extends IService<UserAccountLog> {

    PageUtils queryPage(Map<String, Object> params);
    List<UserAccountLogaAndTransactionIdDto> getUserAccountLogList(Long userId, Integer start, Integer end);
    Integer getUserAccountLogCount( Long userId);
}

