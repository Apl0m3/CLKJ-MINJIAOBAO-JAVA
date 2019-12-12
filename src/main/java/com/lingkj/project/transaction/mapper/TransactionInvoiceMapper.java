package com.lingkj.project.transaction.mapper;

import com.lingkj.project.api.transaction.dto.ApiTransactionInvoiceDto;
import com.lingkj.project.transaction.entity.TransactionInvoice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单 用户发票信息
 *
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
@Mapper
public interface TransactionInvoiceMapper extends BaseMapper<TransactionInvoice> {
    /**
     * 查询交易 发票信息
     * @param recordId
     * @return
     */
    ApiTransactionInvoiceDto selectByRecordId(Long recordId);
}
