package com.lingkj.project.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.commodity.dto.ApiCommodityLadderDto;
import com.lingkj.project.api.dto.AmountDto;
import com.lingkj.project.api.transaction.dto.ApiTransactionLadderExpectDto;
import com.lingkj.project.commodity.entity.CommodityLadder;
import com.lingkj.project.transaction.entity.TransactionCommodity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 商品数量阶梯
 *
 * @author chenyongsong
 * @date 2019-09-17 15:13:42
 */
public interface CommodityLadderService extends IService<CommodityLadder> {

    PageUtils queryPage(Map<String, Object> params);

    void deleteNoId(Long commodityId, List<Long> updateLadders);

    CommodityLadder[] getList(Long id);

    List<ApiCommodityLadderDto> selectByCommodityIdApi(Long commodityId);


    /**
     * 添加 购物车 货到付款
     *
     * @param ladderExpect
     * @param commodityId
     * @param id
     */
    void saveCartLadder(ApiTransactionLadderExpectDto ladderExpect, Long commodityId, Long id);

    /**
     * 添加 订单 货到付款
     *
     * @param ladderExpect
     * @param transactionCommodity
     */
    void saveCommodityLadder(ApiTransactionLadderExpectDto ladderExpect, TransactionCommodity transactionCommodity);

    /**
     * 计算阶梯价
     * @param num
     * @param subtotalFactoryPrice
     * @return
     */
    AmountDto countCommodityStairPrice(Integer num, BigDecimal subtotal, Long commodityId, BigDecimal subtotalFactoryPrice);
}

