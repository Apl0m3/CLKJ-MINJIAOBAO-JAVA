package com.lingkj.project.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.commodity.dto.CommodityNumberAttributesDto;
import com.lingkj.project.commodity.entity.CommodityNumberAttributes;
import com.lingkj.project.commodity.entity.CommodityNumberAttributesValue;
import com.lingkj.project.commodity.mapper.CommodityNumberAttributesMapper;
import com.lingkj.project.commodity.service.CommodityNumberAttributesService;
import com.lingkj.project.commodity.service.CommodityNumberAttributesValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author chenyongsong
 * @date 2019-10-11 16:30:01
 */
@Service
public class CommodityNumberAttributesServiceImpl extends ServiceImpl<CommodityNumberAttributesMapper, CommodityNumberAttributes> implements CommodityNumberAttributesService {

    @Autowired
    CommodityNumberAttributesValueService commodityNumberAttributesValueService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CommodityNumberAttributes> page = this.page(
                new Query<CommodityNumberAttributes>(params).getPage(),
                new QueryWrapper<CommodityNumberAttributes>()
        );

        return new PageUtils(page);
    }

    /**
     * 查询商品数量属性
     *
     * @param commodityId 商品Id
     * @return
     */
    @Override
    public CommodityNumberAttributesDto[] getList(Long commodityId) {
        CommodityNumberAttributesDto[] numberAttributeList = baseMapper.getList(commodityId);
        if (numberAttributeList != null && numberAttributeList.length > 0) {
            for (CommodityNumberAttributesDto commodityNumberAttributesDto : numberAttributeList) {
                CommodityNumberAttributesValue[] commodityNumberAttributesValues = commodityNumberAttributesValueService.queryByCommodityNumberAttributesValueId(commodityNumberAttributesDto.getId());
                commodityNumberAttributesDto.setNumberAttributeValueList(commodityNumberAttributesValues);
            }
        }
        return numberAttributeList;
    }

    /**
     * 查询不在更新集合的id
     *
     * @param updateAttributesIds 需要更新的ids
     * @param commodityId         商品id
     * @return
     */
    @Override
    public List<Long> selectNotInIds(List<Long> updateAttributesIds, Long commodityId) {
        return baseMapper.selectNotInIds(updateAttributesIds, commodityId);
    }

    @Override
    public void updateStatusInIds(List<Long> deleteIds) {
        baseMapper.updateStatusInIds(deleteIds);
    }
}
