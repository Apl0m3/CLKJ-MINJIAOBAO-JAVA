package com.lingkj.project.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.operation.entity.OperateType;

import java.util.List;
import java.util.Map;

/**
 * 供应商 供应产品 类型
 *
 * @author chenyongsong
 * @date 2019-09-12 11:54:40
 */
public interface OperateTypeService extends IService<OperateType> {

    PageUtils queryPage(Map<String, Object> params);

    List<OperateType> selectBatchIds(List<String> asList);
    List<OperateType> selectType(Integer type);
}

