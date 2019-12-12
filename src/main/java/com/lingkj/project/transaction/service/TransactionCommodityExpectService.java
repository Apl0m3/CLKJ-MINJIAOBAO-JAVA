package com.lingkj.project.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.transaction.entity.TransactionCommodityExpect;

import java.util.Map;

/**
 * 订单商品 预计到货时间
 *
 * @author chenyongsong
 * @date 2019-10-22 14:17:44
 */
public interface TransactionCommodityExpectService extends IService<TransactionCommodityExpect> {

    PageUtils queryPage(Map<String, Object> params);
}

