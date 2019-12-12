package com.lingkj.project.commodity.mapper;

import com.alibaba.fastjson.JSONObject;
import com.lingkj.project.commodity.dto.CommodityAttributesDto;
import com.lingkj.project.api.commodity.dto.ApiCommodityAttributeDto;
import com.lingkj.project.commodity.entity.CommodityAttributes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.transaction.entity.TransactionCommodityAttributes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenyongsong
 * @date 2019-09-16 11:06:04
 */
@Mapper
public interface CommodityAttributesMapper extends BaseMapper<CommodityAttributes> {

    void updateStatusNotInIds(@Param("updateCommodityAttributesIds") List<Long> updateCommodityAttributesIds);

    List<Long> getNotInIds(@Param("commodityId") Long commodityId, @Param("updateCommodityAttributesIds") List<Long> updateCommodityAttributesIds);

    CommodityAttributesDto[] getCommodityAttributesList(@Param("commodityId") Long commodityId);

    /**
     * 商品详情查询商品属性
     *
     * @param commodityId
     * @return
     */
    List<ApiCommodityAttributeDto> queryAttributeApi(Long commodityId);

    /**
     * 订单商品查询 属性信息
     *
     * @param id
     * @return
     */
    JSONObject selectJsonById(Long id);

    /**
     * 查询交易数据
     * @param id
     * @param commodityId
     * @return
     */
    TransactionCommodityAttributes selectAttributesById(@Param("id") Long id, @Param("commodityId") Long commodityId);

}
