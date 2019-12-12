package com.lingkj.project.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.commodity.dto.ApiCommodityExpectDto;
import com.lingkj.project.api.transaction.dto.ApiTransactionLadderExpectDto;
import com.lingkj.project.cart.entity.CartExpect;
import com.lingkj.project.commodity.entity.CommodityExpect;
import com.lingkj.project.transaction.entity.TransactionCommodity;
import com.lingkj.project.transaction.entity.TransactionCommodityExpect;

import java.util.List;
import java.util.Map;

/**
 * 预计到货时间
 *
 * @author chenyongsong
 * @date 2019-09-26 11:41:34
 */
public interface CommodityExpectedService extends IService<CommodityExpect> {

    PageUtils queryPage(Map<String, Object> params);

    void deleteNoId(Long commodityId, List<Long> updateExpectIds);

    CommodityExpect[] getList(Long id);

    List<ApiCommodityExpectDto> selectByCommodityIdApi(Long commodityId);

    /**
     * 添加 购物车 预计到货
     *
     * @param ladderExpect
     * @param commodityId
     * @param cartId
     * @return
     */

    void saveCartExpect(ApiTransactionLadderExpectDto ladderExpect, Long commodityId, Long cartId);

    /**
     * 添加 订单 预计到货
     *
     * @param ladderExpect
     * @param transactionCommodity
     * @return
     */
    void saveCommodityExpect(ApiTransactionLadderExpectDto ladderExpect, TransactionCommodity transactionCommodity);
}

