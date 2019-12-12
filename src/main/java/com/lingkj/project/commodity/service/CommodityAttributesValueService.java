package com.lingkj.project.commodity.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.commodity.dto.ApiCommodityAttributeValueDto;
import com.lingkj.project.commodity.entity.CommodityAttributesValue;

import java.util.List;
import java.util.Map;

/**
 * @author chenyongsong
 * @date 2019-09-16 11:06:03
 */
public interface CommodityAttributesValueService extends IService<CommodityAttributesValue> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 删除不在更新列表的属性值
     *  @param commodityAttributesId 属性id
     * @param updateIds             需要修改的属性值Id*/
    void updateNotInIds(Long commodityAttributesId, List<Long> updateIds);

    void updateStatusInCommodityAttributesValueIds(List<Long> deleteCommodityAttributesIds);

    /**
     * 查询属性值数据
     * @param commodityAttributesId 属性id
     * @return
     */
    CommodityAttributesValue[] queryByCommodityAttributesId(Long commodityAttributesId);

    /**
     * 根据属性和commodityId
     * @param attributeId
     * @return
     */
    List<ApiCommodityAttributeValueDto> queryAttributeValueApi(Long attributeId);

    JSONObject selectJsonById(Long id);
}

