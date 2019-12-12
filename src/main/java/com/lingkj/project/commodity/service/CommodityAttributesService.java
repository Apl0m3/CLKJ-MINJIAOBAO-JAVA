package com.lingkj.project.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.transaction.dto.ApiTransactionAttributeDto;
import com.lingkj.project.commodity.dto.CommodityAttributesDto;
import com.lingkj.project.api.commodity.dto.ApiCommodityAttributeDto;
import com.lingkj.project.commodity.entity.CommodityAttributes;
import com.lingkj.project.transaction.entity.TransactionCommodity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author chenyongsong
 * @date 2019-09-16 11:06:04
 */
public interface CommodityAttributesService extends IService<CommodityAttributes> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 修改列表中的属性为删除
     *
     * @param deleteCommodityAttributesIds id集合
     */
    void updateStatusInIds(List<Long> deleteCommodityAttributesIds);

    /**
     * 查询应该删除的ids
     *
     * @param commodityId                  商品id
     * @param updateCommodityAttributesIds id集合
     * @return
     */
    List<Long> getNotInIds(Long commodityId, List<Long> updateCommodityAttributesIds);

    /**
     * 根据属性列表
     *
     * @param commodityId 商品id
     * @return
     */
    CommodityAttributesDto[] getList(Long commodityId);

    List<ApiCommodityAttributeDto> queryAttributeApi(Long commodityId);



//    List<TransactionCommodityAttributes> getCommodityAttribute(List<ApiTransactionAttributeDto> attributeDtos, Long commodityId, Long transactionCommodityId);
    /**
     *  保存 购物车商品属性
     * @param attribute
     * @param commodityId
     * @param cartId
     */
    void saveCartAttribute(List<ApiTransactionAttributeDto> attribute, Long commodityId, Long cartId);
    /**
     *  保存 订单商品属性
     * @param attribute
     * @param transactionCommodity
     * @return
     */
    BigDecimal saveCommodityAttribute(List<ApiTransactionAttributeDto> attribute, TransactionCommodity transactionCommodity);
}

