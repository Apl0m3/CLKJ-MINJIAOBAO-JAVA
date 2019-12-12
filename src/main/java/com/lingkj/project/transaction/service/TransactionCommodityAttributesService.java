package com.lingkj.project.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.transaction.entity.TransactionCommodityAttributes;

import java.util.List;
import java.util.Map;

/**
 * 订单 商品属性
 *
 * @author chenyongsong
 * @date 2019-10-22 14:17:44
 */
public interface TransactionCommodityAttributesService extends IService<TransactionCommodityAttributes> {

    PageUtils queryPage(Map<String, Object> params);

    List<TransactionCommodityAttributes> selectByTrCommdityId(Long trCommdityId);
}

