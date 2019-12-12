package com.lingkj.project.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.commodity.entity.CommodityNumberAttributesValue;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author chenyongsong
 * @date 2019-10-11 16:30:01
 */
public interface CommodityNumberAttributesValueService extends IService<CommodityNumberAttributesValue> {

    PageUtils queryPage(Map<String, Object> params);

    CommodityNumberAttributesValue[] queryByCommodityNumberAttributesValueId(Long commodityNumberAttributesId);

    void updateStatusInIds(List<Long> deleteIds);

    void updateStatusNotInIds(List<Long> updateValueIds, Long commodityNumberAttributesId);
}

