package com.lingkj.project.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.transaction.dto.ApiTransactionReceivingAddressDto;
import com.lingkj.project.transaction.entity.TransactionReceivingAddress;

import java.util.Map;

/**
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
public interface TransactionReceivingAddressService extends IService<TransactionReceivingAddress> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询订单收货地址
     * @param recordId
     * @return
     */
    ApiTransactionReceivingAddressDto selectByRecordId(Long recordId);
}

