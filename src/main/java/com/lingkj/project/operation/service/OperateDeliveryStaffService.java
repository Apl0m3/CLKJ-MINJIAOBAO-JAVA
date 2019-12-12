package com.lingkj.project.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.operation.entity.OperateDeliveryStaff;

import java.util.List;
import java.util.Map;

/**
 * 配送员信息
 *
 * @author chenyongsong
 * @date 2019-07-19 11:59:54
 */
public interface OperateDeliveryStaffService extends IService<OperateDeliveryStaff> {

    PageUtils queryPage(Map<String, Object> params);

    void updateStatusBatchIds(List<Long> asList);
}

