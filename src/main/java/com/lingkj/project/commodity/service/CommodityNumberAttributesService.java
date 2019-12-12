package com.lingkj.project.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.commodity.dto.CommodityNumberAttributesDto;
import com.lingkj.project.commodity.entity.CommodityNumberAttributes;

import java.util.List;
import java.util.Map;

/**
 * @author chenyongsong
 * @date 2019-10-11 16:30:01
 */
public interface CommodityNumberAttributesService extends IService<CommodityNumberAttributes> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询商品数量属性
     *
     * @param commodityId 商品Id
     * @return
     */
    CommodityNumberAttributesDto[] getList(Long commodityId);

    /**
     * 查询不在更新集合的id
     *
     * @param updateAttributesIds 需要更新的ids
     * @param commodityId         商品id
     * @return
     */
    List<Long> selectNotInIds(List<Long> updateAttributesIds, Long commodityId);

    void updateStatusInIds(List<Long> deleteIds);
}

