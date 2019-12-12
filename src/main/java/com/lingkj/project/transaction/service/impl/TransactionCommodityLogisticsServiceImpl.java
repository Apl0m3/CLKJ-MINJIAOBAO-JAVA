package com.lingkj.project.transaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.transaction.entity.TransactionCommodityLogistics;
import com.lingkj.project.transaction.mapper.TransactionCommodityLogisticsMapper;
import com.lingkj.project.transaction.service.TransactionCommodityLogisticsService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 订单商品物流
 *
 * @author chenyongsong
 * @date 2019-10-21 18:11:22
 */
@Service
public class TransactionCommodityLogisticsServiceImpl extends ServiceImpl<TransactionCommodityLogisticsMapper, TransactionCommodityLogistics> implements TransactionCommodityLogisticsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TransactionCommodityLogistics> page = this.page(
                new Query<TransactionCommodityLogistics>(params).getPage(),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public TransactionCommodityLogistics getLogisticsOne( Long trCommodityId, Long recordId, Integer type) {
         QueryWrapper<TransactionCommodityLogistics> queryWrapper=new QueryWrapper<>();
         queryWrapper.eq("record_id",recordId);
         queryWrapper.eq("transaction_commodity_id",trCommodityId);
         queryWrapper.eq("type",type);
        TransactionCommodityLogistics one = this.getOne(queryWrapper);
        return one;
    }

}
