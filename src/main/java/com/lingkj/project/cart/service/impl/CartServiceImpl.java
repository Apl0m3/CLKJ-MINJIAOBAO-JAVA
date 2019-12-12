package com.lingkj.project.cart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.AmountUtil;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.common.validator.Assert;
import com.lingkj.project.api.cart.dto.*;
import com.lingkj.project.api.commodity.dto.*;
import com.lingkj.project.api.dto.AmountDto;
import com.lingkj.project.cart.entity.Cart;
import com.lingkj.project.cart.service.*;
import com.lingkj.project.commodity.dto.CommodityDto;
import com.lingkj.project.commodity.entity.Commodity;
import com.lingkj.project.commodity.mapper.CartMapper;
import com.lingkj.project.commodity.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
    @Autowired
    private CommodityLadderService commodityLadderService;
    @Autowired
    private CommodityExpectedService commodityExpectedService;
    @Autowired
    private CommodityAttributesService commodityAttributesService;
    @Autowired
    private CommodityTypeService commodityTypeService;
    @Autowired
    @Lazy
    private CartAttributesService cartAttributesService;
    @Autowired
    @Lazy
    private CartLadderService cartLadderService;
    @Autowired
    @Lazy
    private CartNumberAttributesService cartNumberAttributesService;
    @Autowired
    @Lazy
    private CartExpectService cartExpectService;
    @Autowired
    @Lazy
    private CommodityService commodityService;
    @Autowired
    private MessageUtils messageUtils;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Cart> page = this.page(
                new Query<Cart>(params).getPage(),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageApi(Page page, Long userId) {

        Integer totalCount = this.baseMapper.queryCommodityCartCount(userId);
        List<ApiCartDto> list = this.baseMapper.queryPageApi(page.getLimit(), page.getPageSize(), userId);
        for (ApiCartDto userCartDto : list) {
            getCartDto(userCartDto);
        }
        return new PageUtils(list, totalCount, page.getPageSize(), page.getPage());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCart(ApiCommodityCartAddDto commodityCartAddDto, Long userId, HttpServletRequest request) {
        Commodity commodity = commodityService.getById(commodityCartAddDto.getCommodityId());
        if (commodity == null || commodity.getStatus() != 0) {
            throw new RRException(messageUtils.getMessage("api.commodity.commodity.isEmpty", request));
        }
        Cart cart = new Cart();
        // 数量填写方式 1：输入，2：选择，3：服装
        if (commodity.getNumberSelectType() == 3) {
            if (commodityCartAddDto.getNumberAttributesList() != null && commodityCartAddDto.getNumberAttributesList().size() > 0) {
                Integer totalQuantity = 0;
                for (ApiCartNumberAttributeDto numberAttributeDto : commodityCartAddDto.getNumberAttributesList()) {
                    if (numberAttributeDto.getNum() != null) {
                        totalQuantity += numberAttributeDto.getNum();
                    }
                }
                cart.setTotalQuantity(totalQuantity);
            }
        } else if (commodity.getNumberSelectType() == 1) {
            cart.setTotalQuantity(commodityCartAddDto.getLadderExpect().getTotalQuantity());
        }

        cart.setNumberSelectType(commodity.getNumberSelectType());
        cart.setCommodityId(commodityCartAddDto.getCommodityId());
        cart.setDesignRequirements(commodityCartAddDto.getDesignRequirements());
        cart.setNeedDesignerStatus(commodityCartAddDto.getDesignStatus());
        cart.setManuscriptUrl(commodityCartAddDto.getManuscriptUrl());
        cart.setUserId(userId);

        cart.setCreateTime(new Date());
        this.baseMapper.insert(cart);

        commodityAttributesService.saveCartAttribute(commodityCartAddDto.getAttribute(), commodityCartAddDto.getCommodityId(), cart.getId());
        //数量填写方式 1：输入，2：选择，3：服装
        if (commodityCartAddDto.getLadderExpect().getLadder() != null && cart.getNumberSelectType() ==2) {
            commodityLadderService.saveCartLadder(commodityCartAddDto.getLadderExpect(), commodityCartAddDto.getCommodityId(), cart.getId());
        }
        commodityExpectedService.saveCartExpect(commodityCartAddDto.getLadderExpect(), commodityCartAddDto.getCommodityId(), cart.getId());
        if (commodityCartAddDto.getNumberAttributesList() != null && commodityCartAddDto.getNumberAttributesList().size() > 0) {
            cartNumberAttributesService.saveCartNumberAttributes(commodityCartAddDto.getNumberAttributesList(), commodityCartAddDto.getCommodityId(), cart.getId());
        }
        //购物车金额
        AmountDto amountDto = getAmount(cart,request);
        Cart cartUpdate = new Cart();
        cartUpdate.setId(cart.getId());
        cartUpdate.setAmount(amountDto.getAmount());
        cartUpdate.setFactoryPrice(amountDto.getFactoryPrice());
        this.updateById(cartUpdate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id,Long userId) {
        //删除cart表
        this.baseMapper.delete(new QueryWrapper<Cart>().eq("id",id).eq("user_id",userId));
        //删除cart_attributes表
        this.baseMapper.deleteTableCartAttributesByCartId(id);
        //删除cart_expect表
        this.baseMapper.deleteTableCartExpectByCartId(id);
        //删除cart_ladder表
        this.baseMapper.deleteTableCartLadderByCartId(id);
        //删除cart_number_attributes表
        this.baseMapper.deleteTableCartNumberAttributesByCartId(id);

    }

    /**
     * 计算购物车商品金额
     *
     * @param cart
     * @return
     */
    private AmountDto getAmount(Cart cart,HttpServletRequest request) {
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal factoryPrice = BigDecimal.ZERO;

        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal subtotalFactoryPrice = BigDecimal.ZERO;
        BigDecimal amountOne = BigDecimal.ZERO;
        BigDecimal amountOneFactoryPrice = BigDecimal.ZERO;
        AmountDto amountDto = new AmountDto();
        //计算单价
        List<ApiCartAttributesDto> attribute = cartAttributesService.selectListByCartId(cart.getId());
        if (attribute == null || attribute.size() == 0) {
            return amountDto;
        }
        for (ApiCartAttributesDto attributesDto : attribute) {
            if (attributesDto != null) {
                if (attributesDto.getCategory() == 3) {
                    amountOne = amountOne.add(attributesDto.getValueAmount());
                    amountOneFactoryPrice = amountOneFactoryPrice.add(attributesDto.getValueFactoryPrice());
                } else if (attributesDto.getCategory() == 4) {
                    if (attributesDto.getType() == 1) {
                        int length = attributesDto.getInputValue().length();
                        BigDecimal num = BigDecimal.valueOf(length).divide(attributesDto.getQuantity(), 2, BigDecimal.ROUND_HALF_DOWN);
                        if (num.multiply(AmountUtil.HUNDRED).compareTo(BigDecimal.ZERO) > 0) {
                            Assert.isNull( attributesDto.getAmount(), messageUtils.getMessage("api.commodity.attributes.amount.null", request));
                            Assert.isNull( attributesDto.getFactoryPrice(), messageUtils.getMessage("api.commodity.attributes.factoryPrice.null", request));
                            subtotal = subtotal.add(num.multiply(attributesDto.getAmount()));
                            subtotalFactoryPrice = subtotalFactoryPrice.add(num.multiply(attributesDto.getFactoryPrice()));
                        }
                    }
                } else if (attributesDto.getCategory() == 5) {
                    if (attributesDto.getType() == 1) {
                        if (StringUtils.isNotBlank(attributesDto.getInputValue())) {
                            Integer length = Integer.valueOf(attributesDto.getInputValue());
                            BigDecimal num = BigDecimal.valueOf(length).divide(attributesDto.getQuantity(), 2, BigDecimal.ROUND_HALF_DOWN);
                            if (num.multiply(AmountUtil.HUNDRED).compareTo(BigDecimal.ZERO) > 0) {
                                Assert.isNull( attributesDto.getAmount(),  messageUtils.getMessage("api.commodity.attributes.amount.null", request));
                                Assert.isNull( attributesDto.getFactoryPrice(), messageUtils.getMessage("api.commodity.attributes.factoryPrice.null", request));
                                subtotal = subtotal.add(num.multiply(attributesDto.getAmount()));
                                subtotalFactoryPrice = subtotalFactoryPrice.add(num.multiply(attributesDto.getFactoryPrice()));
                            }
                        }
                    }
                } else {
                    //属性类型 0：默认 1：输入 2：选择
                    if (attributesDto.getType() != 1) {
                        Assert.isNull( attributesDto.getValueAmount(),  messageUtils.getMessage("api.commodity.attributes.amount.null", request));
                        Assert.isNull( attributesDto.getValueFactoryPrice(), messageUtils.getMessage("api.commodity.attributes.factoryPrice.null", request));
                        subtotal = subtotal.add(attributesDto.getValueAmount());
                        subtotalFactoryPrice = subtotalFactoryPrice.add(attributesDto.getValueFactoryPrice());
                    }
                }
            }
        }

        //数量填写方式 1：输入，2：选择，3：服装
        if (cart.getNumberSelectType() == 2) {
            ApiCartLadderDto ladderNum = cartLadderService.selectByCartId(cart.getId());
            if (ladderNum != null) {
                amount = subtotal.multiply(BigDecimal.valueOf(ladderNum.getNum())).multiply(ladderNum.getDiscount()).divide(AmountUtil.HUNDRED, 2, BigDecimal.ROUND_HALF_DOWN);
                factoryPrice = subtotalFactoryPrice.multiply(BigDecimal.valueOf(ladderNum.getNum())).multiply(ladderNum.getDiscount()).divide(AmountUtil.HUNDRED, 2, BigDecimal.ROUND_HALF_DOWN);
            }
        } else {
            Integer num = cart.getTotalQuantity();
            AmountDto stairPrice = commodityLadderService.countCommodityStairPrice(num, subtotal, cart.getCommodityId(), subtotalFactoryPrice);
            amount = amount.add(stairPrice.getAmount());
            factoryPrice = factoryPrice.add(stairPrice.getFactoryPrice());
        }

        ApiCartExpectDto expect = cartExpectService.selectByCartId(cart.getId());
        //计算预计到达时间 额外费用
        if (expect != null) {
            amount = amount.add(expect.getAmount());
        }
        //计算设计师费用
        if (cart.getNeedDesignerStatus() == 2) {
            Commodity commodityDto = commodityService.getById(cart.getCommodityId());
            ApiCommodityTypeDto apiCommodityTypeDto = commodityTypeService.selectTypeDtoById(commodityDto.getCommodityTypeId());
            if (apiCommodityTypeDto != null) {
                amount = amount.add(apiCommodityTypeDto.getCommission());
            }
        }
        //添加 包装费用
        amount = amount.add(amountOne);
        factoryPrice = factoryPrice.add(amountOneFactoryPrice);
        return new AmountDto(amount, factoryPrice);
    }

    private ApiCartDto getCartDto(ApiCartDto userCartDto) {
        //计算单价
        List<ApiCartAttributesDto> attribute = cartAttributesService.selectListByCartId(userCartDto.getId());
        if (attribute == null || attribute.size() == 0) {
            return userCartDto;
        }
        ApiCartLadderDto ladderNum = cartLadderService.selectByCartId(userCartDto.getId());
        ApiCartExpectDto expect = cartExpectService.selectByCartId(userCartDto.getId());
        //计算设计师费用
        if (userCartDto.getNeedDesignerStatus() == 2) {
            Commodity commodity = commodityService.getById(userCartDto.getCommodityId());
            ApiCommodityTypeDto apiCommodityTypeDto = commodityTypeService.selectTypeDtoById(commodity.getCommodityTypeId());
            if (apiCommodityTypeDto != null) {
                userCartDto.setDesignerAmount(apiCommodityTypeDto.getCommission());
            }
        }
        if (userCartDto.getNumberSelectType()==3){
            List<ApiCartNumberAttributeDto> apiCartNumberAttributeDtos = cartNumberAttributesService.selectNumberAttributeByCartId(userCartDto.getId());
            userCartDto.setNumberAttributes(apiCartNumberAttributeDtos);
        }
        userCartDto.setAttributes(attribute);
        userCartDto.setLadderNum(ladderNum);
        userCartDto.setExpect(expect);
        return userCartDto;
    }

    @Override
    public AmountDto cartAmount(Long cartId, Long userId,HttpServletRequest request) {
        Cart cart = this.getById(cartId);
        if (!cart.getUserId().equals(userId)) {
            throw new RRException(messageUtils.getMessage("api.user.cart.not.belong", request));
        }
        return getAmount(cart,request);
    }

}
