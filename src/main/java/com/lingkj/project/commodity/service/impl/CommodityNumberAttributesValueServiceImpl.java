package com.lingkj.project.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.commodity.entity.CommodityNumberAttributesValue;
import com.lingkj.project.commodity.mapper.CommodityNumberAttributesValueMapper;
import com.lingkj.project.commodity.service.CommodityNumberAttributesValueService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author chenyongsong
 * @date 2019-10-11 16:30:01
 */
@Service
public class CommodityNumberAttributesValueServiceImpl extends ServiceImpl<CommodityNumberAttributesValueMapper, CommodityNumberAttributesValue> implements CommodityNumberAttributesValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CommodityNumberAttributesValue> page = this.page(
                new Query<CommodityNumberAttributesValue>(params).getPage(),
                new QueryWrapper<CommodityNumberAttributesValue>()
        );

        return new PageUtils(page);
    }

    @Override
    public CommodityNumberAttributesValue[] queryByCommodityNumberAttributesValueId(Long commodityNumberAttributesId) {
        return baseMapper.queryByCommodityNumberAttributesValueId(commodityNumberAttributesId);
    }

    @Override
    public void updateStatusInIds(List<Long> deleteIds) {
        baseMapper.updateStatusInIds(deleteIds);
    }

    @Override
    public void updateStatusNotInIds(List<Long> updateValueIds, Long commodityNumberAttributesId) {
        baseMapper.updateStatusNotInIds(updateValueIds, commodityNumberAttributesId);
    }
}
