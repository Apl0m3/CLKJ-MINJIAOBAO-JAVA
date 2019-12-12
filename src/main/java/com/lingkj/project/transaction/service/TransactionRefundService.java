package com.lingkj.project.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.transaction.entity.TransactionRefund;

import java.util.Map;

/**
 *
 *
 * @author chenyongsong
 * @date 2019-08-06 11:43:36
 */
public interface TransactionRefundService extends IService<TransactionRefund> {

    PageUtils queryPage(Map<String, Object> params);

    TransactionRefund selectByTransactionForUpdate(String transactionId);
}

