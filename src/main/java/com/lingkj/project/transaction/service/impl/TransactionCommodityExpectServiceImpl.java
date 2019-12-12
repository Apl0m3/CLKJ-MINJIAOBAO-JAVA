package com.lingkj.project.transaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.transaction.entity.TransactionCommodityExpect;
import com.lingkj.project.transaction.mapper.TransactionCommodityExpectMapper;
import com.lingkj.project.transaction.service.TransactionCommodityExpectService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 订单商品 预计到货时间
 *
 * @author chenyongsong
 * @date 2019-10-22 14:17:44
 */
@Service
public class TransactionCommodityExpectServiceImpl extends ServiceImpl<TransactionCommodityExpectMapper, TransactionCommodityExpect> implements TransactionCommodityExpectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TransactionCommodityExpect> page = this.page(
                new Query<TransactionCommodityExpect>(params).getPage(),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

}
