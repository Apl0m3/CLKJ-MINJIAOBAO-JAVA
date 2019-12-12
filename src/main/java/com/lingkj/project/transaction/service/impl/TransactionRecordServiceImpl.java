package com.lingkj.project.transaction.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.enums.TransactionStatusEnum;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.*;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.common.utils.payment.visa.VisaUtils;
import com.lingkj.common.validator.Assert;
import com.lingkj.project.api.cart.dto.ApiCartAttributesDto;
import com.lingkj.project.api.cart.dto.ApiCartExpectDto;
import com.lingkj.project.api.cart.dto.ApiCartLadderDto;
import com.lingkj.project.api.cart.dto.ApiCartNumberAttributeDto;
import com.lingkj.project.api.commodity.dto.ApiCommodityTypeDto;
import com.lingkj.project.api.dto.AmountDto;
import com.lingkj.project.api.transaction.dto.*;
import com.lingkj.project.cart.entity.Cart;
import com.lingkj.project.cart.service.*;
import com.lingkj.project.commodity.entity.*;
import com.lingkj.project.commodity.service.*;
import com.lingkj.project.integral.entity.IntegralUser;
import com.lingkj.project.integral.entity.IntegralUserLog;
import com.lingkj.project.integral.service.UserIntegralLogService;
import com.lingkj.project.message.entity.Message;
import com.lingkj.project.message.service.MessageService;
import com.lingkj.project.operation.entity.*;
import com.lingkj.project.integral.service.UserIntegralService;
import com.lingkj.project.operation.service.*;
import com.lingkj.project.transaction.dto.*;
import com.lingkj.project.transaction.entity.*;
import com.lingkj.project.transaction.mapper.TransactionRecordMapper;
import com.lingkj.project.transaction.service.*;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.entity.UserAccountLog;
import com.lingkj.project.user.entity.UserAgentRate;
import com.lingkj.project.user.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;


/**
 * @author Administrator
 */
@Slf4j
@Service
public class TransactionRecordServiceImpl extends ServiceImpl<TransactionRecordMapper, TransactionRecord>
        implements TransactionRecordService {
    @Autowired
    private PaymentMethodService paymentMethodService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private OperateAreasService operateAreasService;
    @Autowired
    private OperateRulesService operateRulesService;
    @Autowired
    private UserIntegralLogService userIntegralLogService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private TransactionReceivingAddressService transactionReceivingAddressService;
    @Autowired
    private TransactionInvoiceService transactionInvoiceService;
    @Autowired
    private TransactionCommodityService transactionCommodityService;
    @Autowired
    private TransactionCommodityDeliveryService transactionCommodityDeliveryService;
    @Autowired
    private UserMessageService userMessageService;
    @Autowired
    private MessageUtils messageUtils;
    @Autowired
    private UserCouponMapService couponMapService;
    @Autowired
    private CommodityAttributesValueService commodityAttributesValueService;
    @Autowired
    private CommodityAttributesService commodityAttributesService;
    @Autowired
    private CommodityLadderService commodityLadderService;
    @Autowired
    private CommodityExpectedService commodityExpectedService;
    @Autowired
    private CommodityTypeService commodityTypeService;
    @Autowired
    private OperateRateService operateRateService;
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private UserIntegralService userIntegralService;
    @Autowired
    private CartService cartService;
    @Autowired
    @Lazy
    private CartAttributesService cartAttributesService;
    @Autowired
    @Lazy
    private CartLadderService cartLadderService;
    @Autowired
    @Lazy
    private CartExpectService cartExpectService;
    @Autowired
    @Lazy
    private CartNumberAttributesService cartNumberAttributesService;
    @Autowired
    @Lazy
    private TransactionCommodityNumberAttributesService transactionCommodityNumberAttributesService;
    @Autowired
    @Lazy
    private TransactionCommodityLadderService transactionCommodityLadderService;
    @Autowired
    @Lazy
    private TransactionCommodityExpectService transactionCommodityExpectService;
    @Autowired
    @Lazy
    private TransactionCommodityAttributesService transactionCommodityAttributesService;
    @Autowired
    @Lazy
    private UserAccountService userAccountService;
    @Autowired
    private TransactionServiceApplicationService transactionServiceApplicationService;
    @Autowired
    private UserAgentRateService userAgentRateService;
    @Autowired
    private UserAgentApplicationService userAgentApplicationService;
    @Autowired
    private  TransactionCommodityLogisticsService transactionCommodityLogisticsService;

    @Override
    public PageUtils queryPage(TransactionReqDto transactionReqDto, Page page) {
        Integer totalCount = this.baseMapper.selectCountByCondition(transactionReqDto);
        List<TransactionRecordRespDto> list = this.baseMapper.selectPageByCondition(transactionReqDto, page.getPageSize(), page.getLimit());
       for (TransactionRecordRespDto recordRespDto : list) {
           if(recordRespDto.getTrCommodityId()!=null){
               TransactionServiceApplication byTrCommodityIdService = transactionServiceApplicationService.getByTrCommodityId(recordRespDto.getTrCommodityId());
               if(byTrCommodityIdService!=null){
                   recordRespDto.setTransactionServiceType(byTrCommodityIdService.getType());
               }
           }

       }
        return new PageUtils(list, totalCount, page.getPageSize(), page.getPage());
    }

    @Override
    public TransactionDetailRespDto getInfo(Long id) {
        TransactionDetailRespDto transactionRecordRespDto = this.baseMapper.selectRecordDtoById(id);
        if (transactionRecordRespDto != null) {
            List<TransactionCommodityRespDto> list = this.transactionCommodityService.queryCommodityByRecordId(transactionRecordRespDto.getId());
            for (TransactionCommodityRespDto commodityRespDto : list) {
                TransactionCommodityLogistics deliveryLogistics = transactionCommodityLogisticsService.getLogisticsOne(commodityRespDto.getId(), id, TransactionCommodityLogistics.type_common);
                if(deliveryLogistics!=null){
                    transactionRecordRespDto.setDeliveryLogistics(deliveryLogistics.getDeliveryNum());
                }
                TransactionCommodityLogistics userReturnLogistics = transactionCommodityLogisticsService.getLogisticsOne(commodityRespDto.getId(), id, TransactionCommodityLogistics.type_return);
                if(userReturnLogistics!=null){
                    transactionRecordRespDto.setUserReturnLogistics(userReturnLogistics.getDeliveryNum());
                }
                TransactionCommodityLogistics exchangeLogistics = transactionCommodityLogisticsService.getLogisticsOne(commodityRespDto.getId(), id, TransactionCommodityLogistics.type_exchange);
                if(exchangeLogistics!=null){
                    transactionRecordRespDto.setExchangeLogistics(exchangeLogistics.getDeliveryNum());
                }
                TransactionServiceApplication byTrCommodityIdService = transactionServiceApplicationService.getByTrCommodityId(commodityRespDto.getId());
                 if(byTrCommodityIdService!=null){
                     commodityRespDto.setTransactionServiceType(byTrCommodityIdService.getType());
                 }
                QueryWrapper<TransactionCommodityAttributes> attributesWrapper = new QueryWrapper<>();
                attributesWrapper.eq("commodity_id", commodityRespDto.getId());
                List<TransactionCommodityAttributes> attributesList = this.transactionCommodityAttributesService.list(attributesWrapper);
                commodityRespDto.setAttribute(attributesList);
                if (transactionRecordRespDto.getType() == 1) {
                    if (commodityRespDto.getNumberSelectType() == 3) {
                        Wrapper numberAttributesWrapper = new QueryWrapper<TransactionCommodityNumberAttributes>().eq("transaction_commodity_id", commodityRespDto.getId());
                        List<TransactionCommodityNumberAttributes> numberAttributes = this.transactionCommodityNumberAttributesService.list(numberAttributesWrapper);
                        commodityRespDto.setNumberAttributes(numberAttributes);
                    } else {
                        Wrapper ladderWrapper = new QueryWrapper<TransactionCommodityLadder>().eq("commodity_id", commodityRespDto.getId());
                        TransactionCommodityLadder ladder = this.transactionCommodityLadderService.getOne(ladderWrapper);
                        commodityRespDto.setLadderNum(ladder);
                    }
                }
                Wrapper expectWrapper = new QueryWrapper<TransactionCommodityExpect>().eq("commodity_id", commodityRespDto.getId());
                TransactionCommodityExpect expect = this.transactionCommodityExpectService.getOne(expectWrapper);
                commodityRespDto.setExpect(expect);
            }
            transactionRecordRespDto.setList(list);
        }
        //查询收货地址
        ApiTransactionReceivingAddressDto addressDto = getApiTransactionReceivingAddressDto(transactionRecordRespDto.getId());
        //查询发票信息
        ApiTransactionInvoiceDto invoiceDto = getInvoiceDto(transactionRecordRespDto.getId());
        transactionRecordRespDto.setAddress(addressDto);
        transactionRecordRespDto.setInvoice(invoiceDto);
        return transactionRecordRespDto;
    }

    /**
     * @param userId
     * @param page
     * @param type    0 全部订单  1 待付款订单 2 待定稿订单  3 待收货 4 已收货   5 售后订单
     * @param request
     * @return
     */
    @Override
    public PageUtils queryPageApi(Long userId, com.lingkj.common.bean.entity.Page page, Integer type, HttpServletRequest request) {
        Integer totalCount = this.baseMapper.countApi(userId, type);
        List<ApiTransactionRecordDto> listDtos = this.baseMapper.queryPageApi(userId, page.getPageSize(), page.getLimit(), type);
        for (ApiTransactionRecordDto apiTransactionRecordDto : listDtos) {
            //查询收货地址
            ApiTransactionReceivingAddressDto addressDto = getApiTransactionReceivingAddressDto(apiTransactionRecordDto.getId());
            Integer messageStatus = userMessageService.countNewApi(userId, apiTransactionRecordDto.getId());
            apiTransactionRecordDto.setMessageStatus(messageStatus);
            transactionCommodity(request, apiTransactionRecordDto);
            apiTransactionRecordDto.setReceivingAddressDto(addressDto);

        }
        return new PageUtils(listDtos, totalCount, page.getPageSize(), page.getPage());
    }

    private ApiTransactionInvoiceDto getInvoiceDto(Long id2) {
        //查询发票信息
        ApiTransactionInvoiceDto invoiceDto = transactionInvoiceService.selectByRecordId(id2);
        if (invoiceDto != null) {
            OperateAreas province = this.operateAreasService.getById(invoiceDto.getProvince());
            OperateAreas city = this.operateAreasService.getById(invoiceDto.getCity());
            if (province != null) {
                invoiceDto.setProvinceStr(province.getName());
            }
            if (city != null) {
                invoiceDto.setCityStr(city.getName());
            }
        }
        return invoiceDto;
    }

    private ApiTransactionReceivingAddressDto getApiTransactionReceivingAddressDto(Long id2) {
        ApiTransactionReceivingAddressDto addressDto = transactionReceivingAddressService.selectByRecordId(id2);
        if (addressDto != null) {
            OperateAreas province = this.operateAreasService.getById(addressDto.getProvince());
            if (province != null) {
                addressDto.setProvinceStr(province.getName());
            }
            OperateAreas city = this.operateAreasService.getById(addressDto.getCity());
            if (city != null) {
                addressDto.setCityStr(city.getName());
            }
        }
        return addressDto;
    }

    @Override
    @Transactional
    public Map<String, Object> order(ApiTransactionRecordReqDto recordReqDto, Long userId, HttpServletRequest request) {
        OperateRate rate = operateRateService.selectByType(OperateRate.IVA);
        User user = adminUserService.getById(userId);

        TransactionRecord record = new TransactionRecord();
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal amount = BigDecimal.ZERO;
        //订单号
        String transactionId = TransIdGenerate.generateTransId(TransIdGenerate.orderPrefix);
        //获取订单总金额
        getTransactionAmount(recordReqDto, userId,request);
        totalAmount = recordReqDto.getTotalAmount();
        amount = recordReqDto.getTotalAmount();
        //获取订单 使用优惠劵 优惠金额
        if (recordReqDto.getCouponMapId() != null) {
            BigDecimal couponAmount = couponMapService.getDiscountAmount(userId, recordReqDto.getCouponMapId(), totalAmount);
            amount = totalAmount.subtract(couponAmount);
            record.setCouponAmount(couponAmount);
            record.setCouponMapId(recordReqDto.getCouponMapId());

        }
        //计算费率 金额
        if (rate != null) {
            BigDecimal rateAmount = amount.multiply(rate.getRate()).divide(AmountUtil.HUNDRED, 2, BigDecimal.ROUND_HALF_DOWN);
            amount = amount.add(rateAmount);
            record.setRate(rate.getRate());
            record.setRateAmount(rateAmount);
        }
        // 代理商优惠
        if (user.getUserRoleId().equals(User.member_role_agent)) {
            Long commodityId = recordReqDto.getCommodityList().get(0).getCommodityId();
            Commodity commodity = commodityService.getById(commodityId);
            UserAgentRate agentRate=null;
            if(commodity!=null){
                agentRate = userAgentRateService.selectByUserIdAndAgentId(userId,  commodity.getCommodityTypeId());
            }else {
                throw new RRException(messageUtils.getMessage("api.commodity.commodity.isEmpty", request),400);
            }
//            OperateRate agentRate = operateRateService.selectByType(OperateRate.DISCOUNT);
            if (agentRate != null&&agentRate.getRate()!=null&&agentRate.getRate()!=BigDecimal.valueOf(0)) {
                BigDecimal subtract = AmountUtil.HUNDRED.subtract(agentRate.getRate());
                BigDecimal discountAmount = amount.multiply(subtract).divide(AmountUtil.HUNDRED, 2, BigDecimal.ROUND_HALF_DOWN);
                record.setDiscount(agentRate.getRate());
                record.setDiscountAmount(discountAmount);
                amount=amount.subtract(discountAmount);
            }
        }
        //保存订单
        record.setStatus(TransactionStatusEnum.pending.getStatus());
        record.setTransactionId(transactionId);
        record.setTotalAmount(totalAmount);
        record.setAmount(amount);

        record.setCreateTime(new Date());
        record.setType(1);
        record.setRemarks(recordReqDto.getRemark());
        record.setUserId(userId);
        this.save(record);

        //保存订单商品信息 并 移除购物车
        saveTransactionCommodity(recordReqDto, record,request);

        //保存订单收货地址信息
        ApiTransactionReceivingAddressDto recordReqDtoAddress = recordReqDto.getAddress();
        TransactionReceivingAddress address = new TransactionReceivingAddress();
        BeanUtils.copyProperties(recordReqDtoAddress, address);
        address.setCreateTime(new Date());
        address.setRecordId(record.getId());
        transactionReceivingAddressService.save(address);
        //保存订单发票信息
        TransactionInvoice invoice = new TransactionInvoice();
        ApiTransactionInvoiceDto recordReqDtoInvoice = recordReqDto.getInvoice();
        if (recordReqDto.getInvoice() == null) {
            throw new RRException(messageUtils.getMessage("api.transaction.commodity.order.invoice.null", request));
        }
        BeanUtils.copyProperties(recordReqDtoInvoice, invoice);
        invoice.setCreateTime(new Date());
        invoice.setRecordId(record.getId());
        transactionInvoiceService.save(invoice);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("transactionId", transactionId);
        resultMap.put("amount", amount);
        return resultMap;
    }

    private void saveTransactionCommodity(ApiTransactionRecordReqDto recordReqDto, TransactionRecord record,HttpServletRequest request) {
        List<Long> cartIds = new ArrayList<>();
        List<ApiTransactionCommodityReqDto> commodityList = recordReqDto.getCommodityList();
        for (ApiTransactionCommodityReqDto commodityReqDto : commodityList) {
            if (commodityReqDto.getCartId() != null) {
                cartIds.add(commodityReqDto.getCartId());
                saveCommodityByCartId(record, commodityReqDto,request);
            } else {
                saveCommodity(record, commodityReqDto);
            }
        }
        //删除购物车
        if (cartIds.size() > 0) {
            cartService.removeByIds(cartIds);
        }
    }

    private void saveCommodityByCartId(TransactionRecord record, ApiTransactionCommodityReqDto commodityReqDto,HttpServletRequest request) {
        Long cartId = commodityReqDto.getCartId();
        Cart cart = cartService.getById(cartId);
        if (cart == null) {
            throw new RRException(messageUtils.getMessage("api.user.cart.null", request));
        }
        TransactionCommodity transactionCommodity = getCommodity(record, cart.getCommodityId());
        transactionCommodity.setManuscriptUrl(cart.getManuscriptUrl());
        transactionCommodity.setCommodityNum(cart.getTotalQuantity());
        transactionCommodity.setNeedDesignerStatus(cart.getNeedDesignerStatus());
        transactionCommodity.setNumberSelectType(cart.getNumberSelectType());
        transactionCommodity.setFactoryPrice(cart.getFactoryPrice());
        transactionCommodity.setAmount(cart.getAmount());
        transactionCommodity.setType(1);
        transactionCommodity.setStatus(TransactionStatusEnum.pending.getStatus());
        // 获取预计到货时间
        if (commodityReqDto.getLadderExpect() != null) {
            ApiCartExpectDto apiCartExpectDto = cartExpectService.selectByCartId(cartId);
            if (apiCartExpectDto != null) {
                transactionCommodity.setExpectTime(DateUtils.addDateDays(new Date(), apiCartExpectDto.getDay()));
            }
        }
        this.transactionCommodityService.save(transactionCommodity);
        saveCommodityAttributeByCartId(cartId, transactionCommodity);
        saveCommodityExpectByCartId(cartId, transactionCommodity);
        if (transactionCommodity.getNumberSelectType() == 3) {
            List<ApiCartNumberAttributeDto> list = cartNumberAttributesService.selectNumberAttributeByCartId(cartId);
            List<TransactionCommodityNumberAttributes> attributesList = new ArrayList<>();
            TransactionCommodityNumberAttributes numberAttributes=new TransactionCommodityNumberAttributes();
            for (ApiCartNumberAttributeDto attributeDto : list) {
                numberAttributes=new TransactionCommodityNumberAttributes();
                numberAttributes.setName(attributeDto.getName());
                numberAttributes.setNum(attributeDto.getNum());
                numberAttributes.setTransactionCommodityId(transactionCommodity.getId());
                attributesList.add(numberAttributes);
            }

            this.transactionCommodityNumberAttributesService.saveBatch(attributesList);
        } else {
            saveCommodityLadderByCartId(cartId, transactionCommodity);
        }

    }

    /**
     * 商品添加 购物车 阶梯价
     *
     * @param cartId
     * @param transactionCommodity
     */
    private void saveCommodityLadderByCartId(Long cartId, TransactionCommodity transactionCommodity) {
        ApiCartLadderDto ladderDto = cartLadderService.selectByCartId(cartId);
        TransactionCommodityLadder commodityLadder = new TransactionCommodityLadder();
        commodityLadder.setDiscount(ladderDto.getDiscount());
        commodityLadder.setNum(ladderDto.getNum());
        commodityLadder.setCommodityId(transactionCommodity.getId());
        this.transactionCommodityLadderService.save(commodityLadder);
    }

    /**
     * 商品添加 购物车 预计到货时间
     *
     * @param cartId
     * @param transactionCommodity
     */
    private void saveCommodityExpectByCartId(Long cartId, TransactionCommodity transactionCommodity) {
        ApiCartExpectDto expectDto = cartExpectService.selectByCartId(cartId);
        TransactionCommodityExpect commodityExpect = new TransactionCommodityExpect();
        commodityExpect.setAmount(expectDto.getAmount());
        commodityExpect.setDay(expectDto.getDay());
        commodityExpect.setMaxNum(expectDto.getMaxNum());
        commodityExpect.setFactoryPrice(expectDto.getFactoryPrice());
        commodityExpect.setCommodityId(transactionCommodity.getId());
        this.transactionCommodityExpectService.save(commodityExpect);
    }

    /**
     * 商品添加 购物车 商品属性
     *
     * @param cartId
     * @param transactionCommodity
     */
    private void saveCommodityAttributeByCartId(Long cartId, TransactionCommodity transactionCommodity) {
        List<ApiCartAttributesDto> attributesDtos = cartAttributesService.selectListByCartId(cartId);
        List<TransactionCommodityAttributes> attributesList = new ArrayList<>();
        TransactionCommodityAttributes commodityAttributes;
        for (ApiCartAttributesDto cartAttributesDto : attributesDtos) {
            commodityAttributes = new TransactionCommodityAttributes();
            commodityAttributes.setCommodityId(transactionCommodity.getId());
            commodityAttributes.setCategory(cartAttributesDto.getCategory());
            commodityAttributes.setName(cartAttributesDto.getName());
            commodityAttributes.setQuantity(cartAttributesDto.getQuantity());
            commodityAttributes.setSelectType(cartAttributesDto.getSelectType());
            commodityAttributes.setAmount(cartAttributesDto.getAmount());
            commodityAttributes.setFactoryPrice(cartAttributesDto.getFactoryPrice());
            commodityAttributes.setInputValue(cartAttributesDto.getInputValue());
            commodityAttributes.setType(cartAttributesDto.getType());

            commodityAttributes.setValueName(cartAttributesDto.getValueName());
            commodityAttributes.setValueAmount(cartAttributesDto.getValueAmount());
            commodityAttributes.setValueFactoryPrice(cartAttributesDto.getValueFactoryPrice());
            commodityAttributes.setValueSizeCustomizable(cartAttributesDto.getValueSizeCustomizable());
            commodityAttributes.setValueLength(cartAttributesDto.getValueLength());
            commodityAttributes.setValueWidth(cartAttributesDto.getValueWidth());
            commodityAttributes.setValueUrl(cartAttributesDto.getValueUrl());
            attributesList.add(commodityAttributes);
        }
        this.transactionCommodityAttributesService.saveBatch(attributesList);

    }

    /**
     * 添加订单商品
     *
     * @param record
     * @param commodityReqDto
     */
    private void saveCommodity(TransactionRecord record, ApiTransactionCommodityReqDto commodityReqDto) {

        TransactionCommodity transactionCommodity = getCommodity(record, commodityReqDto.getCommodityId());
        if (transactionCommodity.getNumberSelectType() == 3) {
            Integer commodityNum = getCommodityNum(commodityReqDto);
            transactionCommodity.setCommodityNum(commodityNum);
        } else if (transactionCommodity.getNumberSelectType() == 1) {
            transactionCommodity.setCommodityNum(commodityReqDto.getTotalQuantity());
        }
        transactionCommodity.setCommodityId(commodityReqDto.getCommodityId());
        transactionCommodity.setManuscriptUrl(commodityReqDto.getManuscriptUrl());
        transactionCommodity.setNeedDesignerStatus(commodityReqDto.getDesignStatus());
        transactionCommodity.setRemark(record.getRemarks());
        transactionCommodity.setAmount(commodityReqDto.getAmount());
        transactionCommodity.setFactoryPrice(commodityReqDto.getFactoryPrice());
        transactionCommodity.setType(1);
        transactionCommodity.setStatus(TransactionStatusEnum.pending.getStatus());
        // 获取预计到货时间
        if (commodityReqDto.getLadderExpect() != null) {
            CommodityExpect expect = commodityReqDto.getLadderExpect().getExpect();
            if (expect != null) {
                transactionCommodity.setExpectTime(DateUtils.addDateDays(new Date(), expect.getDay()));
            }
        }
        this.transactionCommodityService.save(transactionCommodity);
        commodityAttributesService.saveCommodityAttribute(commodityReqDto.getAttribute(), transactionCommodity);
        if (transactionCommodity.getNumberSelectType() == 3) {
            transactionCommodityNumberAttributesService.saveCommodityNumberAttributes(commodityReqDto.getNumberAttributesList(), transactionCommodity);
        } else if (transactionCommodity.getNumberSelectType() == 2) {
            commodityLadderService.saveCommodityLadder(commodityReqDto.getLadderExpect(), transactionCommodity);
        }
        commodityExpectedService.saveCommodityExpect(commodityReqDto.getLadderExpect(), transactionCommodity);
    }

    /**
     * 获取订单商品对象
     *
     * @param record
     * @param commodityId
     * @return
     */
    private TransactionCommodity getCommodity(TransactionRecord record, Long commodityId) {
        Commodity commodity = commodityService.getById(commodityId);
        TransactionCommodity transactionCommodity = new TransactionCommodity();
        transactionCommodity.setCommodityId(commodity.getId());
        transactionCommodity.setName(commodity.getName());
        transactionCommodity.setImage(commodity.getImage());
        transactionCommodity.setTransactionId(record.getTransactionId());
        transactionCommodity.setRecordId(record.getId());
        transactionCommodity.setCommodityTypeId(commodity.getCommodityTypeId());
        transactionCommodity.setCreateTime(new Date());
        transactionCommodity.setNumberSelectType(commodity.getNumberSelectType());
        return transactionCommodity;
    }

    /**
     * 确认支付
     *
     * @param confirmPaymentDto
     * @param userId
     * @param request
     */

    @Transactional
    @Override
    public void confirmPayment(ApiConfirmPaymentDto confirmPaymentDto, Long userId, HttpServletRequest request) {
        TransactionRecord record = this.baseMapper.selectByTransactionIdForUpdate(confirmPaymentDto.getTransactionId());
        if (record == null) {
            throw new RRException(messageUtils.getMessage("api.transaction.isEmpty", request));
        }
        if (record.getStatus().equals(TransactionStatusEnum.pending.getStatus())) {
            record.setPaymentMethodId(confirmPaymentDto.getPaymentMethodId());
            record.setStatus(TransactionStatusEnum.paid.getStatus());
            record.setPayTime(new Date());
            this.updateById(record);
        } else {
            throw new RRException(messageUtils.getMessage("api.transaction.paid", request));
        }
    }

    @Transactional
    @Override
    public void updateStatus(String transactionId, Long sysUserId, Integer type, HttpServletRequest request) {
        TransactionRecord transactionRecord = this.baseMapper.selectByTransactionIdForUpdate(transactionId);
        TransactionRecord transactionRecordUpdate = new TransactionRecord();
        transactionRecordUpdate.setId(transactionRecord.getId());
        if (transactionRecord.getStatus().equals(TransactionStatusEnum.paid.getStatus())) {
            //1 平台未收到订单金额 2 平台已收到金额
            if (type == 1) {
                transactionRecordUpdate.setStatus(TransactionStatusEnum.pending.getStatus());
                Message message = new Message();
                message.setUserId(transactionRecord.getUserId());
                message.setCreateSysUserId(sysUserId);
                message.setContent( messageUtils.getMessage("manage.order", request)+":" + transactionRecord.getTransactionId() +  messageUtils.getMessage("manage.order.failure.to.pay", request));
                message.setCreateTime(new Date());
                message.setTitle( messageUtils.getMessage("manage.order.message", request));
                message.setType(Message.type_user);
                message.setTransactionId(transactionRecord.getTransactionId());
                messageService.save(message);
            } else if (type == 2) {
                transactionRecordUpdate.setStatus(TransactionStatusEnum.confirmedPaid.getStatus());
                //平台确认收款 账户添加金额
                BigDecimal amount = transactionRecord.getAmount();
                userAccountService.updateUserAccount(User.platform_user_id, UserAccountLog.type_in, amount, "订单[" + transactionRecord.getTransactionId() + "]支付金额", transactionRecord.getId(), null);
                //添加积分
                OperateRules rules = operateRulesService.selectByType(OperateRules.RULE_TYPE_INTEGRAL, OperateRules.TYPE_CONSUME);
                if (rules != null) {
                    if (amount.multiply(AmountUtil.HUNDRED).compareTo(rules.getFactor().multiply(AmountUtil.HUNDRED)) > 0) {
                        BigDecimal divide = amount.divide(rules.getFactor(), 2, BigDecimal.ROUND_HALF_DOWN);
                        int integral = divide.multiply(rules.getResult()).intValue();
                        userIntegralService.updateUserIntegral(transactionRecord.getUserId(), UserAccountLog.type_in, integral, "购买商品赠送", transactionRecord.getId(), null, transactionRecord.getTransactionId());
                    }

                }
            }
        }
        if (transactionRecordUpdate.getStatus() != null) {
            this.baseMapper.updateById(transactionRecordUpdate);
            if (transactionRecordUpdate.getStatus().equals(TransactionStatusEnum.confirmedPaid.getStatus())) {
                //修改订单下商品状态
                this.transactionCommodityService.updateStatusByRecordId(transactionRecordUpdate.getId(), transactionRecordUpdate.getStatus());
            }
        }
    }


    /**
     * 计算订单商品
     *
     * @param recordReqDto
     * @return
     */
    private ApiTransactionRecordReqDto getTransactionAmount(ApiTransactionRecordReqDto recordReqDto, Long userId,HttpServletRequest request) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (ApiTransactionCommodityReqDto commodityReqDto : recordReqDto.getCommodityList()) {
            BigDecimal amount = BigDecimal.ZERO;
            BigDecimal factoryPrice = BigDecimal.ZERO;
            if (commodityReqDto.getCartId() == null) {
                AmountDto amountDto = commodityOrderAmount(commodityReqDto,request);
                amount = amountDto.getAmount();
                factoryPrice = amountDto.getFactoryPrice();
                commodityReqDto.setAmount(amount);
                commodityReqDto.setFactoryPrice(factoryPrice);
            } else {
                AmountDto amountDto = cartService.cartAmount(commodityReqDto.getCartId(), userId,request);
                amount = amountDto.getAmount();
                factoryPrice = amountDto.getFactoryPrice();
                commodityReqDto.setFactoryPrice(factoryPrice);
            }
            //计算总金额
            totalAmount = totalAmount.add(amount);
        }
        recordReqDto.setTotalAmount(totalAmount);
        return recordReqDto;
    }

    private AmountDto commodityOrderAmount(ApiTransactionCommodityReqDto commodityReqDto,HttpServletRequest request) {
        Commodity commodity = commodityService.getById(commodityReqDto.getCommodityId());
        if (commodity == null) {
            throw new RRException(messageUtils.getMessage("api.commodity.commodity.isEmpty", request));
        }
        BigDecimal amountOne = BigDecimal.ZERO;
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal amountOneFactory = BigDecimal.ZERO;
        BigDecimal amountFactory = BigDecimal.ZERO;
        BigDecimal subtotalFactory = BigDecimal.ZERO;
        //计算单价
        for (ApiTransactionAttributeDto attributeDto : commodityReqDto.getAttribute()) {
            CommodityAttributes attributes = this.commodityAttributesService.getById(attributeDto.getId());
            if (!attributes.getCommodityId().equals(commodityReqDto.getCommodityId())) {
                throw new RRException(messageUtils.getMessage("api.commodity.attribute.mismatch", request));
            }
            switch (attributes.getCategory()) {
                case 4:
                    if (attributes.getType() == 1) {
                        int length =attributeDto.getInputValue().length();
                        BigDecimal num = BigDecimal.valueOf(length).divide(attributes.getQuantity(), 2, BigDecimal.ROUND_HALF_DOWN);
                        if (length > 0) {
                            Assert.isNull( attributes.getAmount(), messageUtils.getMessage("api.commodity.attributes.amount.null", request));
                            Assert.isNull( attributes.getFactoryPrice(), messageUtils.getMessage("api.commodity.attributes.factoryPrice.null", request));
                            subtotal = subtotal.add(num.multiply(attributes.getAmount()));
                            subtotalFactory = subtotalFactory.add(num.multiply(attributes.getFactoryPrice()));
                        }
                    }
                    break;
                case 5:
                    if (attributes.getType() == 1) {
                        if (StringUtils.isNotBlank(attributeDto.getInputValue())) {
                            int length = Integer.parseInt(attributeDto.getInputValue());
                            BigDecimal num = BigDecimal.valueOf(length).divide(attributes.getQuantity(), 2, BigDecimal.ROUND_HALF_DOWN);
                            if (length > 0) {
                                Assert.isNull( attributes.getAmount(), messageUtils.getMessage("api.commodity.attributes.amount.null", request));
                                Assert.isNull( attributes.getFactoryPrice(), messageUtils.getMessage("api.commodity.attributes.factoryPrice.null", request));
                                subtotal = subtotal.add(num.multiply(attributes.getAmount()));
                                subtotalFactory = subtotalFactory.add(num.multiply(attributes.getFactoryPrice()));
                            }
                        }
                    }
                    break;
                default:
                    CommodityAttributesValue attributesValue = commodityAttributesValueService.getById(attributeDto.getAttributeValue().getId());
                    if (attributesValue != null) {
                        //排出 包装金额
                        if (attributes.getCategory() == 3) {
                            Assert.isNull( attributesValue.getAmount(), messageUtils.getMessage("api.commodity.attributes.amount.null", request));
                            Assert.isNull( attributesValue.getFactoryPrice(),  messageUtils.getMessage("api.commodity.attributes.factoryPrice.null", request));
                            amountOne = attributesValue.getAmount();
                            amountOneFactory = attributesValue.getFactoryPrice();
                        } else {
                            if (attributes.getType() != 1) {
                                Assert.isNull( attributesValue.getAmount(), messageUtils.getMessage("api.commodity.attributes.amount.null", request));
                                Assert.isNull( attributesValue.getFactoryPrice(),   messageUtils.getMessage("api.commodity.attributes.factoryPrice.null", request));
                                subtotal = subtotal.add(attributesValue.getAmount());
                                subtotalFactory = subtotalFactory.add(attributesValue.getFactoryPrice());
                            }
                        }
                    }
                    break;
            }
        }
//        数量填写方式 1：输入，2：选择，3：服装
        if (commodity.getNumberSelectType() == 3) {
            //计算服装数量
            Integer commodityNum = getCommodityNum(commodityReqDto);
            AmountDto stairPrice = commodityLadderService.countCommodityStairPrice(commodityNum, subtotal, commodity.getId(), subtotalFactory);
            amount = amount.add(stairPrice.getAmount());
            amountFactory = amountFactory.add(stairPrice.getFactoryPrice());
        } else if (commodity.getNumberSelectType() == 1) {
            AmountDto stairPrice = commodityLadderService.countCommodityStairPrice(commodityReqDto.getTotalQuantity(), subtotal, commodity.getId(), subtotalFactory);
            amount = amount.add(stairPrice.getAmount());
            amountFactory = amountFactory.add(stairPrice.getFactoryPrice());
        } else {
            //计算商品总价
            CommodityLadder commodityLadder = commodityLadderService.getById(commodityReqDto.getLadderExpect().getLadder().getId());
            if (commodityLadder != null) {
                amount = subtotal.multiply(BigDecimal.valueOf(commodityLadder.getNum())).multiply(commodityLadder.getDiscount()).divide(AmountUtil.HUNDRED, 2, BigDecimal.ROUND_HALF_DOWN);
                amountFactory = subtotalFactory.multiply(BigDecimal.valueOf(commodityLadder.getNum())).multiply(commodityLadder.getDiscount()).divide(AmountUtil.HUNDRED, 2, BigDecimal.ROUND_HALF_DOWN);
            }
        }
        //计算预计到达时间 额外费用
        CommodityExpect commodityExpect = commodityExpectedService.getById(commodityReqDto.getLadderExpect().getExpect().getId());
        if (commodityExpect != null) {
            amount = amount.add(commodityExpect.getAmount());
            amountFactory = amountFactory.add(commodityExpect.getFactoryPrice());
        }
        //计算设计师费用
        if (commodityReqDto.getDesignStatus() == 2) {
            ApiCommodityTypeDto apiCommodityTypeDto = commodityTypeService.selectTypeDtoById(commodity.getCommodityTypeId());
            if (apiCommodityTypeDto != null) {
                amount = amount.add(apiCommodityTypeDto.getCommission());
            }
        }
        amountFactory = amountOneFactory.add(amountFactory);
        //包装费用 加入商品总金额
        amount = amountOne.add(amount);

        return new AmountDto(amount, amountFactory);
    }

    private Integer getCommodityNum(ApiTransactionCommodityReqDto commodityReqDto) {
        Integer num = 0;
        if (commodityReqDto.getNumberAttributesList() != null && commodityReqDto.getNumberAttributesList().size() > 0) {
            for (ApiTransactionCommodityNumberAttributeDto numberAttributeDto : commodityReqDto.getNumberAttributesList()) {
                num += numberAttributeDto.getNum();
            }
        }
        return num;
    }

    private ApiTransactionRecordDto transactionCommodity(HttpServletRequest request, ApiTransactionRecordDto apiTransactionRecordDto) {
        //查询订单商品信息
        List<ApiTransactionCommodityRespDto> apiTransactionCommodityRespDto = transactionCommodityService.selectByTransactionId(apiTransactionRecordDto.getTransactionId());
        for (ApiTransactionCommodityRespDto commodityRespDto : apiTransactionCommodityRespDto) {
            if (apiTransactionRecordDto.getType() == 1) {
                //查询设计师
                if (commodityRespDto.getStatus().equals(TransactionStatusEnum.deliveryDesigner.getStatus()) && commodityRespDto.getSubstate().equals(TransactionStatusEnum.design_agree.getStatus()) && commodityRespDto.getNeedDesignerStatus().equals(TransactionCommodity.need_designer_design)) {
                    TransactionCommodityDelivery commodityDelivery = transactionCommodityDeliveryService.selectById(apiTransactionRecordDto.getId(), commodityRespDto.getId(), TransactionCommodityDelivery.TYPE_DESIGNER);
                    if (commodityDelivery != null) {
                        User user = adminUserService.getById(commodityDelivery.getUserId());
                        commodityRespDto.setDesigner(user.getName());
                    }
                }
                if (commodityRespDto.getStatus().equals(TransactionStatusEnum.applyForAfterSale.getStatus()) && commodityRespDto.getSubstate().equals(TransactionStatusEnum.after_sales_application_failed.getStatus())) {
                    TransactionServiceApplication byTrCommodityId = transactionServiceApplicationService.getByTrCommodityId(commodityRespDto.getId());
                    commodityRespDto.setReason(byTrCommodityId.getReason());
                }
                //查询数量阶梯
                if (apiTransactionRecordDto.getType() == 1 && commodityRespDto.getNumberSelectType() == 3) {
                    List<ApiTransactionCommodityNumberAttributeDto> list = transactionCommodityNumberAttributesService.selectByTransactionCommodityId(commodityRespDto.getId());
                    commodityRespDto.setNumberAttributeList(list);
                } else {
                    ApiTransactionCommodityLadderDto apiTransactionCommodityLadderDto = transactionCommodityLadderService.selectByCommodityId(commodityRespDto.getId());
                    commodityRespDto.setLadderNum(apiTransactionCommodityLadderDto);
                }
            }

            //查询供应商
            if (commodityRespDto.getStatus().equals(TransactionStatusEnum.designatedSupplier.getStatus()) && commodityRespDto.getSubstate().equals(TransactionStatusEnum.supplier_agree.getStatus())) {
                TransactionCommodityDelivery commodityDelivery = transactionCommodityDeliveryService.selectById(apiTransactionRecordDto.getId(), commodityRespDto.getId(), TransactionCommodityDelivery.TYPE_SUPPLIER);
                if (commodityDelivery != null) {
                    User user = adminUserService.getById(commodityDelivery.getUserId());
                    commodityRespDto.setSupplier(user.getName());
                }
            }


        }

        apiTransactionRecordDto.setCommodityDto(apiTransactionCommodityRespDto);
        return apiTransactionRecordDto;
    }


    /**
     * 积分商品下单
     *
     * @param integralTransactionRecordDto
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public R integralTransaction(ApiIntegralTransactionRecordDto integralTransactionRecordDto, Long userId,HttpServletRequest request) {
        //订单号
        String transactionId = TransIdGenerate.generateTransId(TransIdGenerate.orderPrefix);
        //查询具体商品  加锁
        Commodity commodity = commodityService.getCommodity1(integralTransactionRecordDto.getCommodityId());
        Integer num = commodity.getNum();
        Integer amount = commodity.getAmount();
        if (num - integralTransactionRecordDto.getNum() < 0) {
            return R.error(messageUtils.getMessage("api.commodity.num.insufficient", request));
        }
        //查询用户积分 加锁
        IntegralUser integralUser = userIntegralService.selectByUserIdForUpdate(userId);
        if (integralUser.getStatus() == 1) {
            return R.error(messageUtils.getMessage("api.user.integral.disable", request));
        }
        //兑换商品总积分
        BigDecimal totalIntegral = BigDecimal.valueOf(amount * integralTransactionRecordDto.getNum());
        //用户当前积分
        Integer userIntegral = integralUser.getIntegral();
        if (userIntegral - totalIntegral.intValue() < 0) {
            return R.error(messageUtils.getMessage("api.user.integral.insufficient", request));
        }
        commodityService.updateCommodityNum(integralTransactionRecordDto.getNum(), new Date(), commodity.getId());
        IntegralUserLog integralUserLog = new IntegralUserLog();
        integralUserLog.setUserId(userId);
        integralUserLog.setCreateTime(new Date());
        integralUserLog.setType(2);
        integralUserLog.setRemark(messageUtils.getMessage("api.exchange.integral.commodity", request) + commodity.getName());
        integralUserLog.setTransactionId(transactionId);
        integralUserLog.setPreviousValue(userIntegral);
        //用户扣除兑换积分后的现有积分
        integralUserLog.setCurrentValue(userIntegral - totalIntegral.intValue());
        integralUserLog.setChangeValue(totalIntegral.intValue());
        userIntegralLogService.save(integralUserLog);
        //扣除积分数 转化成负数
        userIntegralService.updateIntegral(integralUser.getId(), -totalIntegral.intValue(), new Date());

        User user = adminUserService.getById(userId);
        //支付订单生成
        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.setTransactionId(transactionId);
        transactionRecord.setUserId(userId);
        transactionRecord.setStatus(TransactionStatusEnum.confirmedPaid.getStatus());
        transactionRecord.setType(2);
        transactionRecord.setPayTime(new Date());
        transactionRecord.setCreateTime(new Date());
        transactionRecord.setAmount(totalIntegral);
        transactionRecord.setTotalAmount(totalIntegral);
        transactionRecord.setRemarks(integralTransactionRecordDto.getRemark());
        this.save(transactionRecord);
        //订单收货地址
        TransactionReceivingAddress address = integralTransactionRecordDto.getAddress();
        address.setCreateTime(new Date());
        address.setEmail(user.getUserName());
        address.setRecordId(transactionRecord.getId());
        transactionReceivingAddressService.save(address);
        //订单商品
        TransactionCommodity transactionCommodity = new TransactionCommodity();
        transactionCommodity.setCreateTime(new Date());
        transactionCommodity.setName(commodity.getName());
        transactionCommodity.setImage(commodity.getImage());
        transactionCommodity.setRemark(transactionRecord.getRemarks());
        transactionCommodity.setRecordId(transactionRecord.getId());
        transactionCommodity.setTransactionId(transactionId);
        transactionCommodity.setCommodityId(integralTransactionRecordDto.getCommodityId());
        transactionCommodity.setAmount(BigDecimal.valueOf(amount));
        transactionCommodity.setStatus(TransactionStatusEnum.designatedSupplier.getStatus());
        transactionCommodity.setSubstate(TransactionStatusEnum.supplier_agree.getStatus());
        transactionCommodity.setCommodityNum(integralTransactionRecordDto.getNum());
        transactionCommodity.setRemark(integralTransactionRecordDto.getRemark());
        transactionCommodity.setType(2);
        transactionCommodityService.save(transactionCommodity);
        //积分商品默认分配到平台供应商
        TransactionCommodityDelivery commodityDelivery = new TransactionCommodityDelivery();
        commodityDelivery.setRecordId(transactionRecord.getId());
        commodityDelivery.setTransactionCommodityId(transactionCommodity.getId());
        commodityDelivery.setUserId(User.platform_user_id);
        commodityDelivery.setCreateTime(new Date());
        commodityDelivery.setType(TransactionCommodityDelivery.TYPE_SUPPLIER);
        commodityDelivery.setStatus(TransactionCommodityDelivery.STATUS_ACCEPTED);
        this.transactionCommodityDeliveryService.save(commodityDelivery);
        return R.ok();
    }

    @Override
    public PageUtils queryDeliveryList(Page page, TransactionDeliveryListReqDto reqDto) {
        Integer totalCount = this.transactionCommodityService.countDelivery(reqDto);
        List<TransactionDeliveryRespDto> list = this.transactionCommodityService.queryDeliver(reqDto, page.getPageSize(), page.getLimit());

        return new PageUtils(list, totalCount, page.getPageSize(), page.getPage());
    }

    /**
     * 分配订单商品给 供应商/设计师
     *
     * @param reqDto
     * @param sysUserId
     * @param request
     */
    @Transactional
    @Override
    public void delivery(TransactionDeliveryReqDto reqDto, Long sysUserId, HttpServletRequest request) {
        TransactionCommodity transactionCommodity = transactionCommodityService.selectByIdForUpdate(reqDto.getTransactionCommodityId());
        //判断用户信息
        User user = adminUserService.getById(reqDto.getUserId());
        //分配类型 1 设计师  2 供应商
        if (reqDto.getType().equals(TransactionCommodityDelivery.TYPE_DESIGNER)) {
            if (!user.getUserRoleId().equals(User.member_role_designer)) {
                throw new RRException(messageUtils.getMessage("manage.transaction.deliver.roleDesign", request));
            }
            transactionCommodity.setStatus(TransactionStatusEnum.deliveryDesigner.getStatus());
            transactionCommodity.setSubstate(TransactionStatusEnum.design_delivered.getStatus());
        } else if (reqDto.getType().equals(TransactionCommodityDelivery.TYPE_SUPPLIER)) {
            if (!user.getUserRoleId().equals(User.member_role_supplier)) {
                throw new RRException(messageUtils.getMessage("manage.transaction.deliver.roleSupplier", request));
            }
            transactionCommodity.setStatus(TransactionStatusEnum.designatedSupplier.getStatus());
            transactionCommodity.setSubstate(TransactionStatusEnum.supplier_delivered.getStatus());
        }
        //修改订单状态
        this.transactionCommodityService.updateById(transactionCommodity);
        //实体封装
        TransactionCommodityDelivery commodityDelivery = new TransactionCommodityDelivery();
        commodityDelivery.setType(reqDto.getType());
        commodityDelivery.setUserId(reqDto.getUserId());
        commodityDelivery.setStatus(TransactionCommodityDelivery.STATUS_WAIT);
        commodityDelivery.setCreateTime(new Date());
        commodityDelivery.setTransactionCommodityId(transactionCommodity.getId());
        commodityDelivery.setRecordId(transactionCommodity.getRecordId());
        this.transactionCommodityDeliveryService.save(commodityDelivery);

        //添加消息
        Message message = new Message();
        message.setContent(messageUtils.getMessage("manage.user.new.order", request));
        message.setTitle(messageUtils.getMessage("manage.order.delivery", request));
        message.setUserId(reqDto.getUserId());
        message.setStatus(0);
        message.setCreateTime(new Date());
        message.setCreateSysUserId(sysUserId);
        this.messageService.save(message);

    }

    @Override
    public Integer getUserCount(Long userId) {
        QueryWrapper<TransactionRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return this.count(wrapper);
    }

    @Override
    public Map<String, Object> queryManuscriptInfo(Long transactionCommodityId, HttpServletRequest request) {
        TransactionCommodity transactionCommodity = transactionCommodityService.getById(transactionCommodityId);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("manuscriptUrl", transactionCommodity.getManuscriptUrl());

        return returnMap;
    }

    @Transactional
    @Override
    public R cancellationOfOrder(Long id) {
        TransactionRecord transactionRecord = this.baseMapper.selectByIdForUpdate(id);
        transactionRecord.setStatus(1);
        this.updateById(transactionRecord);
        return R.ok();
    }

    @Transactional
    @Override
    public void reviewManuscript(ReviewManuscriptReqDto reviewManuscriptReqDto, Long sysUserId, HttpServletRequest request) {
        TransactionCommodity transactionCommodity = this.transactionCommodityService.selectByIdForUpdate(reviewManuscriptReqDto.getTransactionCommodityId());
        if (transactionCommodity == null) {
            throw new RRException(messageUtils.getMessage("api.transaction.commodity.isEmpty", request));
        }
        TransactionRecord transactionRecord = this.getById(transactionCommodity.getRecordId());
        TransactionCommodity transactionCommodityUpdate = new TransactionCommodity();
        transactionCommodityUpdate.setId(transactionCommodity.getId());
        // 1 认证成功  2 认证失败

        if (reviewManuscriptReqDto.getStatus() == 1) {
            transactionCommodityUpdate.setStatus(TransactionStatusEnum.reviewManuscript.getStatus());
            transactionCommodityUpdate.setSubstate(TransactionStatusEnum.manuscript_review_success.getStatus());
        } else if (reviewManuscriptReqDto.getStatus() == 2) {
            transactionCommodityUpdate.setStatus(TransactionStatusEnum.reviewManuscript.getStatus());
            transactionCommodityUpdate.setSubstate(TransactionStatusEnum.manuscript_review_failure.getStatus());
            //添加通知消息
            Message message = new Message();
            message.setTransactionId(transactionCommodity.getTransactionId());
            message.setUserId(transactionRecord.getUserId());
            message.setCreateTime(new Date());
            message.setCreateSysUserId(sysUserId);
            message.setTitle(messageUtils.getMessage("manage.order.manuscript.examine", request));
            message.setContent(messageUtils.getMessage("manage.order", request) + transactionCommodity.getTransactionId() + messageUtils.getMessage("manage.order.manuscript.examine.error", request) + reviewManuscriptReqDto.getReason());
            this.messageService.save(message);
        }
        if (transactionCommodityUpdate.getStatus() != null) {
            this.transactionCommodityService.updateById(transactionCommodityUpdate);
        }
    }

    @Override
    public ApiTransactionRecordDto transactionInfoApi(Long userId, Long transactionRecordId, HttpServletRequest request) {
        ApiTransactionRecordDto transactionRecordDto = this.baseMapper.transactionInfoApi(userId, transactionRecordId);
        if (transactionRecordDto.getPaymentMethodId() != null) {
            OperatePaymentMethod byId = paymentMethodService.queryInfo(transactionRecordDto.getPaymentMethodId());
            if (byId != null) {
                transactionRecordDto.setMethodPay(byId.getName());
            }
        }

        ApiTransactionReceivingAddressDto addressDto = getApiTransactionReceivingAddressDto(transactionRecordDto.getId());
        ApiTransactionInvoiceDto invoiceDto = getInvoiceDto(transactionRecordDto.getId());
        transactionCommodity(request, transactionRecordDto);
        transactionRecordDto.setReceivingAddressDto(addressDto);
        transactionRecordDto.setInvoiceDto(invoiceDto);
        Integer integer = userMessageService.countNewApi(userId, transactionRecordId);
        if(integer>0){
            for (ApiTransactionCommodityRespDto commodityRespDto :transactionRecordDto.getCommodityDto() ) {
                userMessageService.updateStatusByRecordIdAll(userId,transactionRecordDto.getId(),commodityRespDto.getId());
            }
        }
        return transactionRecordDto;
    }

    @Override
    public Map<String, Integer> queryRecordCountList(Long userId, HttpServletRequest request) {
        Map<String, Integer> map = new HashMap<>();
// type 0 全部订单  1 待付款订单 2 待定稿订单  3 待收货 4 已收货   5 售后订单
        Integer total0 = this.baseMapper.countApi(userId, 0);
        Integer total1 = this.baseMapper.countApi(userId, 1);
        Integer total2 = this.baseMapper.countApi(userId, 2);
        Integer total3 = this.baseMapper.countApi(userId, 3);
        Integer total4 = this.baseMapper.countApi(userId, 4);
        Integer total5 = this.baseMapper.countApi(userId, 5);
        map.put("totalAll", total0);
        map.put("totalOne", total1);
        map.put("totalTwo", total2);
        map.put("totalThr", total3);
        map.put("totalThi", total4);
        map.put("totalFive", total5);
        return map;
    }


    /**
     * 支付回调
     */
    @Override
    @Transactional
    public int getCallback(String orderNo, String amount) {
        TransactionRecord transactionRecord = this.baseMapper.selectOne(new QueryWrapper<TransactionRecord>().eq("tripartite_transaction_id", orderNo));
        if (!transactionRecord.getStatus().equals(TransactionStatusEnum.pending.getStatus())) {
            return 0;
        }
        if (transactionRecord.getAmount().multiply(AmountUtil.HUNDRED).compareTo(new BigDecimal(amount)) != 0) {
            return 0;
        }
        transactionRecord.setPaymentMethodId(OperatePaymentMethod.paymentType_visa.longValue());
        transactionRecord.setStatus(TransactionStatusEnum.confirmedPaid.getStatus());
        transactionRecord.setPayTime(new Date());
        this.baseMapper.updateById(transactionRecord);
        if(transactionRecord.getStatus().equals(TransactionStatusEnum.confirmedPaid.getStatus())){
            transactionCommodityService.updateStatusByRecordId(transactionRecord.getId(),transactionRecord.getStatus());
        }
        return 1;
    }

    @Override
    @Transactional
    public JSONObject getVisaConfig(String orderNo) {
        System.out.println(orderNo);
        TransactionRecord transactionRecord=this.baseMapper.selectOne(new QueryWrapper<TransactionRecord>().eq("transaction_id",orderNo));
        String tripartiteTransactionId= TransIdGenerate.generateTransIdPay(TransIdGenerate.payOrderPrefix);
        transactionRecord.setTripartiteTransactionId(tripartiteTransactionId);
        this.baseMapper.updateById(transactionRecord);
        String amount = transactionRecord.getAmount().multiply(AmountUtil.HUNDRED).intValue()+"";
        return VisaUtils.getParamsAndSign(amount, tripartiteTransactionId);
    }

    @Override
    public BigDecimal monthlySales() {
        return this.baseMapper.monthlySales();
    }
}
