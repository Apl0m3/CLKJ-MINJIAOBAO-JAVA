package com.lingkj.project.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.transaction.entity.TransactionRefund;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 *
 * @author chenyongsong
 * @date 2019-08-06 11:43:36
 */
@Mapper
public interface TransactionRefundMapper extends BaseMapper<TransactionRefund> {

    TransactionRefund selectByTransactionForUpdate(String transactionId);
}
