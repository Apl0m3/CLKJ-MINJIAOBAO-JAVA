package com.lingkj.project.commodity.mapper;

import com.alibaba.fastjson.JSONObject;
import com.lingkj.project.api.commodity.dto.ApiCommodityAttributeValueDto;
import com.lingkj.project.commodity.entity.CommodityAttributesValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 *
 * @author chenyongsong
 * @date 2019-09-16 11:06:03
 */
@Mapper
public interface CommodityAttributesValueMapper extends BaseMapper<CommodityAttributesValue> {

    /**
     * 删除不在更新列表的属性值
     *  @param commodityAttributesId 属性id
     * @param updateIds             需要修改的属性值Id*/
    void updateNotInIds(@Param("commodityAttributesId") Long commodityAttributesId, @Param("updateIds") List<Long> updateIds);

    void updateStatusInCommodityAttributesValueIds(List<Long> deleteCommodityAttributesIds);

    CommodityAttributesValue[] queryByCommodityAttributesId(Long commodityAttributesId);

    /**
     * 查询属性值
     * @param attributeId
     * @return
     */
    List<ApiCommodityAttributeValueDto> queryAttributeValueApi(@Param("attributeId") Long attributeId);

    /**
     * 查询 订单商品属性值
     * @param id
     * @return
     */
    JSONObject selectJsonById(Long id);
}
