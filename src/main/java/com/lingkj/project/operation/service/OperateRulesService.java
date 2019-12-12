package com.lingkj.project.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.operation.entity.OperateRules;

import java.util.List;
import java.util.Map;

/**
 * 积分规则
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:26
 */
public interface OperateRulesService extends IService<OperateRules> {

    PageUtils queryPage(Map<String, Object> params);

    void updateStatusByIds(List<Long> asList);

    /**
     * 规则类型
     *
     * @param ruleType
     * @param type
     * @return
     */
    OperateRules selectByType(Integer ruleType, Integer type);

    OperateRules selectOneByType(Integer type, Integer ruleType);
}

