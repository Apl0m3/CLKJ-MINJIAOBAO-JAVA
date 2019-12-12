package com.lingkj.project.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.operation.entity.OperateRate;

import java.util.Map;

/**
 * 费率表
 *
 * @author chenyongsong
 * @date 2019-10-10 09:15:52
 */
public interface OperateRateService extends IService<OperateRate> {

    PageUtils queryPage(Map<String, Object> params);


    /**
     * 根据费率类型查询费率
     * @param type
     * @return
     */
    OperateRate selectByType(Integer type);

}

