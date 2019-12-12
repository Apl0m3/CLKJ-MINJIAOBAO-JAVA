package com.lingkj.project.transaction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.project.api.transaction.dto.ApiTransactionCommodityNumberAttributeDto;
import com.lingkj.project.transaction.entity.TransactionCommodity;
import com.lingkj.project.transaction.entity.TransactionCommodityNumberAttributes;
import com.lingkj.project.transaction.mapper.TransactionCommodityNumberAttributesMapper;
import com.lingkj.project.transaction.service.TransactionCommodityNumberAttributesService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyongsong
 * @date 2019-10-22 14:55:54
 */
@Service
public class TransactionCommodityNumberAttributesServiceImpl extends ServiceImpl<TransactionCommodityNumberAttributesMapper, TransactionCommodityNumberAttributes> implements TransactionCommodityNumberAttributesService {


    @Override
    public void saveCommodityNumberAttributes(List<ApiTransactionCommodityNumberAttributeDto> numberAttributesList, TransactionCommodity transactionCommodity) {
        List<TransactionCommodityNumberAttributes> list = new ArrayList<>();
        for (ApiTransactionCommodityNumberAttributeDto attributeDto : numberAttributesList) {
            if (attributeDto.getNum() != null && attributeDto.getNum() > 0) {
                TransactionCommodityNumberAttributes numberAttributes = new TransactionCommodityNumberAttributes();
                numberAttributes.setName(attributeDto.getName());
                numberAttributes.setNum(attributeDto.getNum());
                numberAttributes.setTransactionCommodityId(transactionCommodity.getId());
                list.add(numberAttributes);
            }
        }
        if (list.size() > 0) {
            this.saveBatch(list);
        }
    }

    @Override
    public List<ApiTransactionCommodityNumberAttributeDto> selectByTransactionCommodityId(Long id) {
        return this.baseMapper.selectByTransactionCommodityId(id);
    }
}
