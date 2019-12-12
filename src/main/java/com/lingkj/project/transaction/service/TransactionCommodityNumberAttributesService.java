package com.lingkj.project.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.project.api.transaction.dto.ApiTransactionCommodityNumberAttributeDto;
import com.lingkj.project.transaction.entity.TransactionCommodity;
import com.lingkj.project.transaction.entity.TransactionCommodityNumberAttributes;

import java.util.List;

/**
 * 
 *
 * @author chenyongsong
 * @date 2019-10-22 14:55:54
 */
public interface TransactionCommodityNumberAttributesService extends IService<TransactionCommodityNumberAttributes> {

    void saveCommodityNumberAttributes(List<ApiTransactionCommodityNumberAttributeDto> numberAttributesList, TransactionCommodity transactionCommodity);

    /**
     * 通过商品数量查询 服装数量
     * @param id
     * @return
     */
    List<ApiTransactionCommodityNumberAttributeDto> selectByTransactionCommodityId(Long id);
}

