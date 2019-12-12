package com.lingkj.project.transaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.transaction.entity.TransactionCommodityAttributes;
import com.lingkj.project.transaction.mapper.TransactionCommodityAttributesMapper;
import com.lingkj.project.transaction.service.TransactionCommodityAttributesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 订单 商品属性
 *
 * @author chenyongsong
 * @date 2019-10-22 14:17:44
 */
@Service
public class TransactionCommodityAttributesServiceImpl extends ServiceImpl<TransactionCommodityAttributesMapper, TransactionCommodityAttributes> implements TransactionCommodityAttributesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TransactionCommodityAttributes> page = this.page(
                new Query<TransactionCommodityAttributes>(params).getPage(),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<TransactionCommodityAttributes> selectByTrCommdityId(Long trCommdityId) {

        return  this.baseMapper.selectByCommodityId(trCommdityId);
    }

}
