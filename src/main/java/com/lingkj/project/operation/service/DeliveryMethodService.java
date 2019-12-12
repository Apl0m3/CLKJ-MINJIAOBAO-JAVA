package com.lingkj.project.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.operation.entity.OperateDeliveryMethod;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:26
 */
public interface DeliveryMethodService extends IService<OperateDeliveryMethod> {

    PageUtils queryPage(Map<String, Object> params);

    void saveOrUpdate(OperateDeliveryMethod deliveryMethod, Long sysUserId);

    void updateStatusByIds(List<Long> asList);
}

