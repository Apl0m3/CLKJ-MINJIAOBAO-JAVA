package com.lingkj.project.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.api.commodity.dto.ApiCommodityExpectDto;
import com.lingkj.project.api.transaction.dto.ApiTransactionLadderExpectDto;
import com.lingkj.project.cart.entity.CartExpect;
import com.lingkj.project.cart.service.CartExpectService;
import com.lingkj.project.commodity.entity.CommodityExpect;
import com.lingkj.project.commodity.mapper.CommodityExpectMapper;
import com.lingkj.project.commodity.service.CommodityExpectedService;
import com.lingkj.project.transaction.entity.TransactionCommodity;
import com.lingkj.project.transaction.entity.TransactionCommodityExpect;
import com.lingkj.project.transaction.service.TransactionCommodityExpectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 预计到货时间
 *
 * @author chenyongsong
 * @date 2019-09-26 11:41:34
 */
@Service
public class CommodityExpectedServiceImpl extends ServiceImpl<CommodityExpectMapper, CommodityExpect> implements CommodityExpectedService {
    @Autowired
    private CartExpectService cartExpectService;
    @Autowired
    private TransactionCommodityExpectService transactionCommodityExpectService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CommodityExpect> page = this.page(
                new Query<CommodityExpect>(params).getPage(),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public void deleteNoId(Long commodityId, List<Long> updateExpectIds) {
        this.baseMapper.updateByNoId(commodityId, updateExpectIds);
    }

    @Override
    public CommodityExpect[] getList(Long id) {

        return this.baseMapper.getList(id);
    }

    @Override
    public List<ApiCommodityExpectDto> selectByCommodityIdApi(Long commodityId) {
        return baseMapper.selectByCommodityIdApi(commodityId);
    }

    @Override
    public void saveCartExpect(ApiTransactionLadderExpectDto ladderExpect, Long commodityId, Long cartId) {
        CommodityExpect commodityExpect = this.getById(ladderExpect.getExpect().getId());
        if (!commodityExpect.getCommodityId().equals(commodityId)) {
            throw new RRException("商品不符合");
        }
        CartExpect cartExpect = new CartExpect();
        cartExpect.setCartId(cartId);
        cartExpect.setExpectId(commodityExpect.getId());
        cartExpect.setAmount(commodityExpect.getAmount());
        cartExpect.setDay(commodityExpect.getDay());
        cartExpect.setFactoryPrice(commodityExpect.getFactoryPrice());
        cartExpect.setMaxNum(commodityExpect.getMaxNum());
        cartExpectService.save(cartExpect);

    }

    @Override
    public void saveCommodityExpect(ApiTransactionLadderExpectDto ladderExpect, TransactionCommodity transactionCommodity) {
        CommodityExpect commodityExpect = this.getById(ladderExpect.getExpect().getId());
        if (!commodityExpect.getCommodityId().equals(transactionCommodity.getCommodityId())) {
            throw new RRException("商品不符合");
        }
        TransactionCommodityExpect transactionCommodityExpect = new TransactionCommodityExpect();
        transactionCommodityExpect.setCommodityId(transactionCommodity.getId());
        transactionCommodityExpect.setAmount(commodityExpect.getAmount());
        transactionCommodityExpect.setDay(commodityExpect.getDay());
        transactionCommodityExpect.setMaxNum(commodityExpect.getMaxNum());
        transactionCommodityExpect.setFactoryPrice(commodityExpect.getFactoryPrice());
        transactionCommodityExpectService.save(transactionCommodityExpect);
    }

}
