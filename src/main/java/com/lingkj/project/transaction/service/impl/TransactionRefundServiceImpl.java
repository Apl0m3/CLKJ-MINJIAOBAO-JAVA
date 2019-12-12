package com.lingkj.project.transaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.transaction.entity.TransactionRefund;
import com.lingkj.project.transaction.mapper.TransactionRefundMapper;
import com.lingkj.project.transaction.service.TransactionRefundService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 *
 *
 * @author chenyongsong
 * @date 2019-08-06 11:43:36
 */
@Service
public class TransactionRefundServiceImpl extends ServiceImpl<TransactionRefundMapper, TransactionRefund> implements TransactionRefundService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TransactionRefund> page = this.page(
                new Query<TransactionRefund>(params).getPage(),
                new QueryWrapper<TransactionRefund>()
        );

        return new PageUtils(page);
    }

    @Override
    public TransactionRefund selectByTransactionForUpdate(String transactionId) {
        return this.baseMapper.selectByTransactionForUpdate(transactionId);
    }

}
