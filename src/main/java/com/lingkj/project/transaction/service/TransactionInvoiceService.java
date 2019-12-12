package com.lingkj.project.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.transaction.dto.ApiTransactionInvoiceDto;
import com.lingkj.project.transaction.entity.TransactionInvoice;

import java.util.Map;

/**
 * 订单 用户发票信息
 *
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
public interface TransactionInvoiceService extends IService<TransactionInvoice> {

    PageUtils queryPage(Map<String, Object> params);

    ApiTransactionInvoiceDto selectByRecordId(Long transactionId);
}

