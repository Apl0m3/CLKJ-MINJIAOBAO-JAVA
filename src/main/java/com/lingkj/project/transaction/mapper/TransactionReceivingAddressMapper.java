package com.lingkj.project.transaction.mapper;

import com.lingkj.project.api.transaction.dto.ApiTransactionReceivingAddressDto;
import com.lingkj.project.transaction.entity.TransactionReceivingAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
@Mapper
public interface TransactionReceivingAddressMapper extends BaseMapper<TransactionReceivingAddress> {
    /**
     * 查询订单收货地址
     *
     * @param recordId
     * @return
     */
    ApiTransactionReceivingAddressDto selectByRecordId(Long recordId);
}
