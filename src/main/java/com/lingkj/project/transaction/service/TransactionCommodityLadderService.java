package com.lingkj.project.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.transaction.dto.ApiTransactionCommodityLadderDto;
import com.lingkj.project.transaction.entity.TransactionCommodityLadder;

import java.util.Map;

/**
 * 订单商品数量阶梯价
 *
 * @author chenyongsong
 * @date 2019-10-22 14:17:43
 */
public interface TransactionCommodityLadderService extends IService<TransactionCommodityLadder> {

    PageUtils queryPage(Map<String, Object> params);

    ApiTransactionCommodityLadderDto selectByCommodityId(Long id);
}

