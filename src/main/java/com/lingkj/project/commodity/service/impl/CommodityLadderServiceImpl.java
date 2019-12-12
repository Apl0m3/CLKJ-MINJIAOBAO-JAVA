package com.lingkj.project.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.AmountUtil;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.api.commodity.dto.ApiCommodityLadderDto;
import com.lingkj.project.api.dto.AmountDto;
import com.lingkj.project.api.transaction.dto.ApiTransactionLadderExpectDto;
import com.lingkj.project.cart.entity.CartLadder;
import com.lingkj.project.cart.service.CartLadderService;
import com.lingkj.project.commodity.entity.CommodityLadder;
import com.lingkj.project.commodity.mapper.CommodityLadderMapper;
import com.lingkj.project.commodity.service.CommodityLadderService;
import com.lingkj.project.transaction.entity.TransactionCommodity;
import com.lingkj.project.transaction.entity.TransactionCommodityLadder;
import com.lingkj.project.transaction.service.TransactionCommodityLadderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 商品数量阶梯
 *
 * @author chenyongsong
 * @date 2019-09-17 15:13:42
 */
@Service
public class CommodityLadderServiceImpl extends ServiceImpl<CommodityLadderMapper, CommodityLadder> implements CommodityLadderService {
    @Autowired
    private CartLadderService cartLadderService;
    @Autowired
    private TransactionCommodityLadderService transactionCommodityLadderService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String commodityId = (String) params.get("commodityId");
        QueryWrapper<CommodityLadder> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(commodityId)) {
            wrapper.eq("commodity_id", Long.valueOf(commodityId));
        }
        wrapper.orderByAsc("num");
        IPage<CommodityLadder> page = this.page(
                new Query<CommodityLadder>(params).getPage(),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void deleteNoId(Long commodityId, List<Long> updateLadders) {
        this.baseMapper.updateByNoId(commodityId, updateLadders);

    }

    @Override
    public CommodityLadder[] getList(Long commodityId) {

        return this.baseMapper.getList(commodityId);
    }

    @Override
    public List<ApiCommodityLadderDto> selectByCommodityIdApi(Long commodityId) {
        return this.baseMapper.selectByCommodityIdApi(commodityId);
    }

    @Override
    public void saveCartLadder(ApiTransactionLadderExpectDto ladderExpect, Long commodityId, Long id) {

        CommodityLadder ladder = this.getById(ladderExpect.getLadder().getId());
        if (!ladder.getCommodityId().equals(commodityId)) {
            throw new RRException("商品与属性不匹配");
        }
        CartLadder cartLadder = new CartLadder();
        cartLadder.setCartId(id);
        cartLadder.setDiscount(ladder.getDiscount());
        cartLadder.setNum(ladder.getNum());
        cartLadder.setLadderId(ladder.getId());
        this.cartLadderService.save(cartLadder);
    }

    @Override
    public void saveCommodityLadder(ApiTransactionLadderExpectDto ladderExpect, TransactionCommodity transactionCommodity) {
        CommodityLadder ladder = this.getById(ladderExpect.getLadder().getId());
        if (!ladder.getCommodityId().equals(transactionCommodity.getCommodityId())) {
            throw new RRException("商品与属性不匹配");
        }
        TransactionCommodityLadder commodityLadder = new TransactionCommodityLadder();
        commodityLadder.setCommodityId(transactionCommodity.getId());
        commodityLadder.setDiscount(ladder.getDiscount());
        commodityLadder.setNum(ladder.getNum());
        this.transactionCommodityLadderService.save(commodityLadder);
    }

    /**
     * 服装数量阶梯判断
     * @param num
     * @param subtotal
     * @param commodityId
     * @param subtotalFactoryPrice
     * @return
     */
    @Override
    public AmountDto countCommodityStairPrice(Integer num, BigDecimal subtotal, Long commodityId, BigDecimal subtotalFactoryPrice) {
        BigDecimal stairPrice = BigDecimal.ZERO;
        BigDecimal stairFactoryPrice = BigDecimal.ZERO;
        List<ApiCommodityLadderDto> commodityLadderDtos = this.selectByCommodityIdApi(commodityId);
        int size = commodityLadderDtos.size();
        //计算阶梯价格
        BigDecimal numBig = BigDecimal.valueOf(num);
        for (int i = 0; i < size; i++) {
            ApiCommodityLadderDto ladder = commodityLadderDtos.get(i);
            ApiCommodityLadderDto ladderNext = null;
            if (i + 1 < size) {
                ladderNext = commodityLadderDtos.get(i + 1);
            }
            if (i == 0 && num < ladder.getNum()) {
                stairPrice = subtotal.multiply(numBig);
                stairFactoryPrice = subtotalFactoryPrice.multiply(numBig);
            } else if (num >= ladder.getNum() && ladderNext != null && ladder.getNum() < ladderNext.getNum()) {
                stairPrice = subtotal.multiply(numBig).multiply(ladder.getDiscount()).divide(AmountUtil.HUNDRED, 2, BigDecimal.ROUND_HALF_DOWN);
                stairFactoryPrice = subtotalFactoryPrice.multiply(numBig).multiply(ladder.getDiscount()).divide(AmountUtil.HUNDRED, 2, BigDecimal.ROUND_HALF_DOWN);

            } else if (num >= ladder.getNum() && ladderNext == null) {
                stairPrice = subtotal.multiply(numBig).multiply(ladder.getDiscount()).divide(AmountUtil.HUNDRED, 2, BigDecimal.ROUND_HALF_DOWN);
                stairFactoryPrice = subtotalFactoryPrice.multiply(numBig).multiply(ladder.getDiscount()).divide(AmountUtil.HUNDRED, 2, BigDecimal.ROUND_HALF_DOWN);
            }

        }
        return new AmountDto(stairPrice,stairFactoryPrice);
    }

}
