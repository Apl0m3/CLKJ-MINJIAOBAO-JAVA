package com.lingkj.project.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.transaction.entity.TransactionDelivery;

import java.util.Map;

/**
 * 交易平台配送信息
 *
 * @author chenyongsong
 * @date 2019-07-19 11:59:54
 */
public interface TransactionDeliveryService extends IService<TransactionDelivery> {

    PageUtils queryPage(Map<String, Object> params);

    Map<String, Object> selectByTransactionId(String transactionId);
}

