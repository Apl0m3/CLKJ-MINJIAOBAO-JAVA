package com.lingkj.project.integral.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.integral.entity.IntegralUserLog;
import com.lingkj.project.user.dto.SaveIntegerUserLogDto;

import java.util.Map;

/**
 * 用户积分记录
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:25
 */
public interface UserIntegralLogService extends IService<IntegralUserLog> {

    PageUtils queryPage(Map<String, Object> params, Long userId);

    PageUtils queryIntegralLog(Page page, Long userId);

    public void saveIntegralUserLog(SaveIntegerUserLogDto saveIntegerUserLogDto);

}

