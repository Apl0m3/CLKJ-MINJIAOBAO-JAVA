package com.lingkj.project.transaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.api.transaction.dto.ApiTransactionCommodityLadderDto;
import com.lingkj.project.transaction.entity.TransactionCommodityLadder;
import com.lingkj.project.transaction.mapper.TransactionCommodityLadderMapper;
import com.lingkj.project.transaction.service.TransactionCommodityLadderService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 订单商品数量阶梯价
 *
 * @author chenyongsong
 * @date 2019-10-22 14:17:43
 */
@Service
public class TransactionCommodityLadderServiceImpl extends ServiceImpl<TransactionCommodityLadderMapper, TransactionCommodityLadder> implements TransactionCommodityLadderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TransactionCommodityLadder> page = this.page(
                new Query<TransactionCommodityLadder>(params).getPage(),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public ApiTransactionCommodityLadderDto selectByCommodityId(Long id) {
        return baseMapper.selectByCommodityId(id);
    }

}
