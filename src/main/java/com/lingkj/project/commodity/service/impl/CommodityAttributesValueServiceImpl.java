package com.lingkj.project.commodity.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.api.commodity.dto.ApiCommodityAttributeValueDto;
import com.lingkj.project.commodity.entity.CommodityAttributesValue;
import com.lingkj.project.commodity.mapper.CommodityAttributesValueMapper;
import com.lingkj.project.commodity.service.CommodityAttributesValueService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author chenyongsong
 * @date 2019-09-16 11:06:03
 */
@Service
public class CommodityAttributesValueServiceImpl extends ServiceImpl<CommodityAttributesValueMapper, CommodityAttributesValue> implements CommodityAttributesValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String commodityAttributesId = (String) params.get("commodityAttributesId");
        QueryWrapper<CommodityAttributesValue> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0);
        if (StringUtils.isNotBlank(commodityAttributesId)) {
            wrapper.eq("commodity_attributes_id", Long.valueOf(commodityAttributesId));
        }
        IPage<CommodityAttributesValue> page = this.page(
                new Query<CommodityAttributesValue>(params).getPage(),
                wrapper
        );

        return new PageUtils(page);
    }

    /**
     * 删除不在更新列表的属性值
     *  @param commodityAttributesId 属性id
     * @param updateIds             需要修改的属性值Id*/
    @Override
    public void updateNotInIds(Long commodityAttributesId, List<Long> updateIds) {
        baseMapper.updateNotInIds(commodityAttributesId, updateIds);
    }

    @Override
    public void updateStatusInCommodityAttributesValueIds(List<Long> deleteCommodityAttributesIds) {
        baseMapper.updateStatusInCommodityAttributesValueIds(deleteCommodityAttributesIds);
    }

    /**
     * 查询属性值数据
     *
     * @param commodityAttributesId 属性id
     * @return
     */
    @Override
    public CommodityAttributesValue[] queryByCommodityAttributesId(Long commodityAttributesId) {
        return baseMapper.queryByCommodityAttributesId(commodityAttributesId);
    }

    @Override
    public List<ApiCommodityAttributeValueDto> queryAttributeValueApi(Long attributeId) {
        return this.baseMapper.queryAttributeValueApi(attributeId);
    }

    @Override
    public JSONObject selectJsonById(Long id) {
        return baseMapper.selectJsonById(id);
    }
}
