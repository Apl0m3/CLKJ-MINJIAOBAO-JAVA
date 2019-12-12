package com.lingkj.project.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.api.commodity.dto.ApiCommodityAttributeDto;
import com.lingkj.project.api.transaction.dto.ApiTransactionAttributeDto;
import com.lingkj.project.cart.entity.CartAttributes;
import com.lingkj.project.cart.service.CartAttributesService;
import com.lingkj.project.commodity.dto.CommodityAttributesDto;
import com.lingkj.project.commodity.entity.CommodityAttributes;
import com.lingkj.project.commodity.entity.CommodityAttributesValue;
import com.lingkj.project.commodity.mapper.CommodityAttributesMapper;
import com.lingkj.project.commodity.service.CommodityAttributesService;
import com.lingkj.project.commodity.service.CommodityAttributesValueService;
import com.lingkj.project.transaction.entity.TransactionCommodity;
import com.lingkj.project.transaction.entity.TransactionCommodityAttributes;
import com.lingkj.project.transaction.service.TransactionCommodityAttributesService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author chenyongsong
 * @date 2019-09-16 11:06:04
 */
@Service
public class CommodityAttributesServiceImpl extends ServiceImpl<CommodityAttributesMapper, CommodityAttributes> implements CommodityAttributesService {
    @Autowired
    private CommodityAttributesValueService commodityAttributesValueService;
    @Autowired
    private CartAttributesService cartAttributesService;
    @Autowired
    private TransactionCommodityAttributesService transactionCommodityAttributesService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String commodityId = (String) params.get("commodityId");
        QueryWrapper<CommodityAttributes> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0);
        if (StringUtils.isNotBlank(commodityId)) {
            wrapper.eq("commodity_id", Long.valueOf(commodityId));
        }
        IPage<CommodityAttributes> page = this.page(
                new Query<CommodityAttributes>(params).getPage(),
                wrapper
        );

        return new PageUtils(page);
    }

    /**
     * 修改列表中的属性为删除
     *
     * @param deleteCommodityAttributesIds id集合
     */
    @Override
    public void updateStatusInIds(List<Long> deleteCommodityAttributesIds) {
        baseMapper.updateStatusNotInIds(deleteCommodityAttributesIds);
    }

    /**
     * 查询应该删除的ids
     *
     * @param commodityId                  商品id
     * @param updateCommodityAttributesIds id集合
     * @return
     */
    @Override
    public List<Long> getNotInIds(Long commodityId, List<Long> updateCommodityAttributesIds) {
        return baseMapper.getNotInIds(commodityId, updateCommodityAttributesIds);
    }

    /**
     * 根据属性列表
     *
     * @param commodityId 商品id
     * @return
     */
    @Override
    public CommodityAttributesDto[] getList(Long commodityId) {
        return baseMapper.getCommodityAttributesList(commodityId);
    }

    @Override
    public List<ApiCommodityAttributeDto> queryAttributeApi(Long commodityId) {

        return this.baseMapper.queryAttributeApi(commodityId);
    }


    @Override
    public void saveCartAttribute(List<ApiTransactionAttributeDto> attribute, Long commodityId, Long cartId) {
        List<CartAttributes> list = new ArrayList<>();
        for (ApiTransactionAttributeDto attributeDto : attribute) {
            CommodityAttributes commodityAttributes = this.getById(attributeDto.getId());
            CartAttributes cartAttributes = new CartAttributes();
            if (commodityAttributes == null) {
                throw new RRException("该商品属性不是该商品的属性");
            }
            if (!commodityAttributes.getCommodityId().equals(commodityId)) {
                throw new RRException("该商品属性不是该商品的属性");
            }
            CommodityAttributesValue attributesValue = commodityAttributesValueService.getById(attributeDto.getAttributeValue().getId());
            cartAttributes.setName(commodityAttributes.getName());
            cartAttributes.setSelectType(commodityAttributes.getSelectType());
            cartAttributes.setType(commodityAttributes.getType());
            cartAttributes.setCategory(commodityAttributes.getCategory());
            cartAttributes.setAttributeId(commodityAttributes.getId());
            cartAttributes.setInputValue(attributeDto.getInputValue());
            cartAttributes.setAmount(commodityAttributes.getAmount());
            cartAttributes.setQuantity(commodityAttributes.getQuantity());
            cartAttributes.setFactoryPrice(commodityAttributes.getFactoryPrice());
            //属性类型 0：默认 1：输入 2：选择
            if (commodityAttributes.getType() != 1) {
                cartAttributes.setValueName(attributesValue.getName());
                cartAttributes.setValueAmount(attributesValue.getAmount());
                if (attributesValue.getSizeCustomizable()==1){
                    cartAttributes.setValueLength(attributeDto.getAttributeValue().getLength());
                    cartAttributes.setValueWidth(attributeDto.getAttributeValue().getWidth());
                }else {
                    cartAttributes.setValueLength(attributesValue.getLength());
                    cartAttributes.setValueWidth(attributesValue.getWidth());
                }
                cartAttributes.setValueSizeCustomizable(attributesValue.getSizeCustomizable());
                cartAttributes.setValueUrl(attributesValue.getUrl());
                cartAttributes.setValueFactoryPrice(attributesValue.getFactoryPrice());
            }
            cartAttributes.setCartId(cartId);
            list.add(cartAttributes);

        }
        if (list.size() > 0) {
            this.cartAttributesService.saveBatch(list);
        }
    }

    @Override
    public BigDecimal saveCommodityAttribute(List<ApiTransactionAttributeDto> attribute, TransactionCommodity transactionCommodity) {
        BigDecimal amount = BigDecimal.ZERO;
        List<TransactionCommodityAttributes> list = new ArrayList<>();
        for (ApiTransactionAttributeDto attributeDto : attribute) {
            TransactionCommodityAttributes attributes = this.baseMapper.selectAttributesById(attributeDto.getId(), transactionCommodity.getCommodityId());
            if (attributes == null) {
                throw new RRException("该商品属性不是该商品的属性");
            }
            CommodityAttributesValue attributesValue = commodityAttributesValueService.getById(attributeDto.getAttributeValue().getId());
            //属性类型 0：默认 1：输入 2：选择
            if (attributes.getType() != 1) {
                attributes.setValueName(attributesValue.getName());
                attributes.setValueAmount(attributesValue.getAmount());
                if (attributesValue.getSizeCustomizable()==1){
                    attributes.setValueLength(attributeDto.getAttributeValue().getLength());
                    attributes.setValueWidth(attributeDto.getAttributeValue().getWidth());
                }else {
                    attributes.setValueLength(attributesValue.getLength());
                    attributes.setValueWidth(attributesValue.getWidth());
                }

                attributes.setValueFactoryPrice(attributesValue.getFactoryPrice());
                attributes.setValueSizeCustomizable(attributesValue.getSizeCustomizable());
                attributes.setValueUrl(attributesValue.getUrl());
                amount = amount.add(attributesValue.getAmount());
            }
            attributes.setInputValue(attributeDto.getInputValue());
            attributes.setCommodityId(transactionCommodity.getId());
            list.add(attributes);

        }
        if (list.size() > 0) {
            this.transactionCommodityAttributesService.saveBatch(list);
        }
        return amount;
    }
}
