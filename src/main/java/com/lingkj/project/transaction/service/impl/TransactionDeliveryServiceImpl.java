package com.lingkj.project.transaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.transaction.entity.TransactionDelivery;
import com.lingkj.project.transaction.mapper.TransactionDeliveryMapper;
import com.lingkj.project.transaction.service.TransactionDeliveryService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 交易平台配送信息
 *
 * @author chenyongsong
 * @date 2019-07-19 11:59:54
 */
@Service
public class TransactionDeliveryServiceImpl extends ServiceImpl<TransactionDeliveryMapper, TransactionDelivery> implements TransactionDeliveryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TransactionDelivery> page = this.page(
                new Query<TransactionDelivery>(params).getPage(),
                new QueryWrapper<TransactionDelivery>()
        );

        return new PageUtils(page);
    }

    @Override
    public Map<String, Object> selectByTransactionId(String transactionId) {
        return baseMapper.selectByTransactionId(transactionId);
    }

}
