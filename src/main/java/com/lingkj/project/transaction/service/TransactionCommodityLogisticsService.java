package com.lingkj.project.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.transaction.entity.TransactionCommodityLogistics;

import java.util.Map;

/**
 * 订单商品物流
 *
 * @author chenyongsong
 * @date 2019-10-21 18:11:22
 */
public interface TransactionCommodityLogisticsService extends IService<TransactionCommodityLogistics>{

    PageUtils queryPage(Map<String, Object> params);

    TransactionCommodityLogistics getLogisticsOne(Long trCommodityId, Long recordId,Integer type);

}

