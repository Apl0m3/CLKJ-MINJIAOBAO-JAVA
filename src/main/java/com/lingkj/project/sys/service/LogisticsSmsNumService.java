package com.lingkj.project.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.sys.Dto.UpdateLogisticsSmsNumRespDto;
import com.lingkj.project.sys.entity.LogisticsSmsNum;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 *
 * @author chenyongsong
 * @date 2019-08-03 09:17:56
 */
public interface LogisticsSmsNumService extends IService<LogisticsSmsNum> {

    PageUtils queryPage(Map<String, Object> params);
    void updateLogisticsSmsNum(UpdateLogisticsSmsNumRespDto updateLogisticsSmsNumRespDto, HttpServletRequest request);
}

