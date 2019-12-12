package com.lingkj.project.transaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.enums.TransactionStatusEnum;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.common.utils.R;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.project.api.transaction.dto.ApiShipDto;
import com.lingkj.project.api.transaction.dto.ApiTransactionCommodityLadderDto;
import com.lingkj.project.api.transaction.dto.ApiTransactionCommodityRespDto;
import com.lingkj.project.transaction.dto.TransactionCommodityRespDto;
import com.lingkj.project.transaction.dto.TransactionDeliveryListReqDto;
import com.lingkj.project.transaction.dto.TransactionDeliveryRespDto;
import com.lingkj.project.transaction.entity.*;
import com.lingkj.project.transaction.mapper.TransactionCommodityMapper;
import com.lingkj.project.transaction.service.*;
import com.lingkj.project.user.dto.UserRespDto;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.entity.UserAccountLog;
import com.lingkj.project.user.entity.UserCommission;
import com.lingkj.project.user.entity.UserSupplierApplication;
import com.lingkj.project.user.service.AdminUserService;
import com.lingkj.project.user.service.UserAccountService;
import com.lingkj.project.user.service.UserCommissionService;
import com.lingkj.project.user.service.UserSupplierApplicationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lingkj.common.enums.TransactionStatusEnum.after_sales_supplier_ship;
import static com.lingkj.project.transaction.entity.TransactionCommodityLogistics.type_exchange;
import static com.lingkj.project.transaction.entity.TransactionCommodityLogistics.type_return;

/**
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
@Service
public class TransactionCommodityServiceImpl extends ServiceImpl<TransactionCommodityMapper, TransactionCommodity> implements TransactionCommodityService {

    @Autowired
    private TransactionCommodityStatusLogService transactionCommodityStatusLogService;
    @Autowired
    @Lazy
    private TransactionCommodityDeliveryService transactionCommodityDeliveryService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private MessageUtils messageUtils;
    @Autowired
    @Lazy
    private TransactionRecordService transactionRecordService;
    @Autowired
    @Lazy
    private TransactionCommodityLogisticsService commodityLogisticsService;
    @Autowired
    @Lazy
    private UserCommissionService commissionService;
    @Autowired
    @Lazy
    private UserAccountService userAccountService;
    @Autowired
    @Lazy
    private UserSupplierApplicationService userSupplierApplicationService;
    @Autowired
    private TransactionCommodityLadderService transactionCommodityLadderService;
    @Autowired
    private TransactionCommodityAttributesService transactionCommodityAttributesService;
    @Autowired
    @Lazy
    private  TransactionServiceApplicationService transactionServiceApplicationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TransactionCommodity> page = this.page(
                new Query<TransactionCommodity>(params).getPage(),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<ApiTransactionCommodityRespDto> selectByTransactionId(String transactionId) {
        return this.baseMapper.selectByRecordId(transactionId);
    }

    @Override
    public List<TransactionCommodityRespDto> queryCommodityByRecordId(Long id) {
        List<TransactionCommodityRespDto> commodityRespDtos = this.baseMapper.queryCommodityByRecordId(id);
        for (TransactionCommodityRespDto commodityRespDto : commodityRespDtos) {

        }
        return commodityRespDtos;
    }

    @Override
    public void updateStatusByRecordId(Long recordId, Integer status) {
        this.baseMapper.updateStatusByRecordId(recordId, status);
    }

    @Override
    public Integer countDelivery(TransactionDeliveryListReqDto reqDto) {
        if (reqDto.getType()==1){
            return this.baseMapper.countDeliverDesigner(reqDto);
        }else {
            return this.baseMapper.countDeliverSupplier(reqDto);
        }
    }

    @Override
    public List<TransactionDeliveryRespDto> queryDeliver(TransactionDeliveryListReqDto reqDto, Integer pageSize, Integer limit) {
        if (reqDto.getType()==1){
            return this.baseMapper.queryDeliverDesigner(reqDto, pageSize, limit);
        }else {
            return this.baseMapper.queryDeliverSupplier(reqDto, pageSize, limit);
        }

    }

    @Override
    public TransactionCommodity selectByIdForUpdate(Long id) {
        return this.baseMapper.selectByIdForUpdate(id);
    }

    /**
     * @param id
     * @param userId
     * @param type
     * @param status
     * @param substate
     * @return
     */
    @Transactional
    @Override
    public R updateStatusOrSubstate(Long id, Long userId, Integer type, Integer status, Integer substate,HttpServletRequest request) {
        TransactionCommodity transactionCommodity = this.selectByIdForUpdate(id);
        TransactionCommodity transactionCommodityUpdate=new TransactionCommodity();
        transactionCommodityUpdate.setId(transactionCommodity.getId());
        TransactionRecord transactionRecord = this.transactionRecordService.getById(transactionCommodity.getRecordId());
        if (transactionRecord.getType().equals(TransactionRecord.type_ordinary)){
            //设计师 设计稿件上传后，用户确认稿件 并 支付佣金给设计师
            if (transactionCommodity.getNeedDesignerStatus().equals(TransactionCommodity.need_designer_design) && status.equals(TransactionStatusEnum.deliveryDesigner.getStatus()) && substate.equals(TransactionStatusEnum.design_determined.getStatus())) {

                if (transactionCommodity.getStatus().equals(TransactionStatusEnum.deliveryDesigner.getStatus())&&transactionCommodity.getSubstate().equals(TransactionStatusEnum.design_determined.getStatus())) {
                    throw new RRException(messageUtils.getMessage("api.manuscript.confirm", request));
                }

                TransactionCommodityDelivery commodityDelivery = this.transactionCommodityDeliveryService.selectByRecordIdForUpdate(transactionCommodity.getRecordId(), transactionCommodity.getId(), TransactionCommodityDelivery.TYPE_DESIGNER);
                if (commodityDelivery == null) {
                    throw new RRException(messageUtils.getMessage("api.designer.null", request));
                }
                //查询 需要支付的佣金
                UserCommission userCommission = commissionService.selectByTransactionCommodityId(transactionCommodity.getId(), commodityDelivery.getUserId());
                if (userCommission == null) {
                    throw new RRException(messageUtils.getMessage("api.designer.type.commission.null", request));
                }
                //修改结算状态
                TransactionCommodityDelivery commodityDeliveryUpdate = new TransactionCommodityDelivery();
                commodityDeliveryUpdate.setId(commodityDelivery.getId());
                commodityDeliveryUpdate.setSettlementStatus(TransactionCommodityDelivery.SETTLEMENT_STATUS_YES);
                this.transactionCommodityDeliveryService.updateById(commodityDeliveryUpdate);
                // 支付 设计师 佣金
                userAccountService.updateUserAccount(userCommission.getUserId(), UserAccountLog.type_in, userCommission.getCommission(), "设计产品获得", transactionCommodity.getRecordId(), transactionCommodity.getId());
                //平台 扣除 设计师支付佣金
                userAccountService.updateUserAccount(User.platform_user_id, UserAccountLog.type_out, userCommission.getCommission(), "支付产品设计佣金", transactionCommodity.getRecordId(), transactionCommodity.getId());

            }
            //供应商 用户确认收货后  支付佣金给供应商
            if (status != null && status.equals(TransactionStatusEnum.confirmReceipt.getStatus())&&substate==null) {
                if (transactionCommodity.getStatus().equals(TransactionStatusEnum.confirmReceipt.getStatus())) {
                    throw new RRException(messageUtils.getMessage("api.received.goods", request));
                }
                TransactionCommodityDelivery commodityDelivery = this.transactionCommodityDeliveryService.selectByRecordIdForUpdate(transactionCommodity.getRecordId(), transactionCommodity.getId(), TransactionCommodityDelivery.TYPE_SUPPLIER);
                if (commodityDelivery == null) {
                    throw new RRException(messageUtils.getMessage("api.supplier.null", request));
                }
                //修改结算状态
                TransactionCommodityDelivery commodityDeliveryUpdate = new TransactionCommodityDelivery();
                commodityDeliveryUpdate.setId(commodityDelivery.getId());
                commodityDeliveryUpdate.setSettlementStatus(TransactionCommodityDelivery.SETTLEMENT_STATUS_YES);
                this.transactionCommodityDeliveryService.updateById(commodityDeliveryUpdate);
                // 支付 供应商 佣金
                userAccountService.updateUserAccount(commodityDelivery.getUserId(), UserAccountLog.type_in, transactionCommodity.getFactoryPrice(), "供应商生成商品获得佣金", transactionCommodity.getRecordId(), transactionCommodity.getId());
                //平台 扣除 供应商支付佣金
                userAccountService.updateUserAccount(User.platform_user_id, UserAccountLog.type_out, transactionCommodity.getFactoryPrice(), "支付供应商佣金", transactionCommodity.getRecordId(), transactionCommodity.getId());
            }
        }
        if (status != null) {
            transactionCommodityUpdate.setStatus(status);
        }
        if (substate != null) {
            transactionCommodityUpdate.setSubstate(substate);
        }

        this.updateById(transactionCommodityUpdate);
        transactionCommodityStatusLogService.saveStatusLog(userId, transactionCommodity.getId(), transactionCommodity.getRecordId(), type, status, substate);
        return R.ok();
    }


    @Override
    public ApiTransactionCommodityRespDto selectByIdOne(Long id, Long transactionRecordId, HttpServletRequest request) {
        ApiTransactionCommodityRespDto apiTransactionCommodityRespDto = this.baseMapper.queryCommodityById(id);
        TransactionRecord byId = transactionRecordService.getById(transactionRecordId);
        ApiTransactionCommodityLadderDto ladderDto = transactionCommodityLadderService.selectByCommodityId(id);
        apiTransactionCommodityRespDto.setLadderNum(ladderDto);
        List<TransactionCommodityAttributes> list = transactionCommodityAttributesService.selectByTrCommdityId(id);
        if (list != null || list.size() > 0) {
            apiTransactionCommodityRespDto.setAttributes(list);
        }
//        if (byId.getAmount() != null) {
//            apiTransactionCommodityRespDto.setAmount(byId.getAmount());
//        }
        if (apiTransactionCommodityRespDto.getStatus().equals(TransactionStatusEnum.deliveryDesigner.getStatus()) && apiTransactionCommodityRespDto.getSubstate().equals(TransactionStatusEnum.design_agree.getStatus()) && apiTransactionCommodityRespDto.getNeedDesignerStatus().equals(TransactionCommodity.need_designer_design)) {
            TransactionCommodityDelivery commodityDelivery =
                    transactionCommodityDeliveryService.selectById(transactionRecordId, apiTransactionCommodityRespDto.getId(), TransactionCommodityDelivery.TYPE_DESIGNER);
            if (commodityDelivery == null) {
                throw new RRException(messageUtils.getMessage("api.transaction.commodity.isEmpty", request));
            }
            User user = adminUserService.getById(commodityDelivery.getUserId());
            apiTransactionCommodityRespDto.setDesigner(user.getName());
        }
        //查询供应商
        if (apiTransactionCommodityRespDto.getStatus().equals(TransactionStatusEnum.designatedSupplier.getStatus()) && apiTransactionCommodityRespDto.getSubstate().equals(TransactionStatusEnum.supplier_agree.getStatus())) {
            TransactionCommodityDelivery commodityDelivery = transactionCommodityDeliveryService.selectById(transactionRecordId, apiTransactionCommodityRespDto.getId(), TransactionCommodityDelivery.TYPE_SUPPLIER);
            if (commodityDelivery == null) {
                throw new RRException(messageUtils.getMessage("api.transaction.commodity.isEmpty", request));
            }
            User user = adminUserService.getById(commodityDelivery.getUserId());
            apiTransactionCommodityRespDto.setSupplier(user.getName());
        }
        return apiTransactionCommodityRespDto;
    }

    @Transactional
    @Override
    public void userAfterSalesConfirmation(Long trCommodityId, Long userId) {
        User byId = adminUserService.getById(userId);
        TransactionCommodity transactionCommodity = this.selectByIdForUpdate(trCommodityId);
        if (byId.getUserRoleId().equals(User.member_role_supplier)) {
            TransactionServiceApplication byTrCommodityIdService = transactionServiceApplicationService.getByTrCommodityId(transactionCommodity.getId());
            if(byTrCommodityIdService.getType().equals(TransactionServiceApplication.TYPE_REWORK)){
                transactionCommodity.setSubstate(TransactionStatusEnum.after_sales_supplier_confirm_receipt.getStatus());
            }else {
                transactionCommodity.setSubstate(TransactionStatusEnum.after_sales_user_confirm_receipt.getStatus());
            }
            this.updateById(transactionCommodity);
            transactionCommodityStatusLogService.saveStatusLog(userId, trCommodityId, transactionCommodity.getRecordId(), 3, transactionCommodity.getStatus(), transactionCommodity.getSubstate());
        } else {
            transactionCommodity.setSubstate(TransactionStatusEnum.after_sales_user_confirm_receipt.getStatus());
            transactionCommodityStatusLogService.saveStatusLog(userId, trCommodityId, transactionCommodity.getRecordId(), 0, transactionCommodity.getStatus(), transactionCommodity.getSubstate());
            this.updateById(transactionCommodity);
        }
    }

    @Override
    @Transactional
    public void ship(ApiShipDto apiShipDto, Long userId, HttpServletRequest request) {
        TransactionCommodity transactionCommodity = getCommodity(apiShipDto, request);
        if (transactionCommodity.getStatus().equals(TransactionStatusEnum.designatedSupplier.getStatus())) {
            //
            if (transactionCommodity.getSubstate().equals(TransactionStatusEnum.supplier_agree.getStatus())) {
                TransactionCommodity transactionCommodityUpdate = saveTrCommodityLogistics(apiShipDto, userId, transactionCommodity.getRecordId(), transactionCommodity.getId(), TransactionCommodityLogistics.type_common);
                transactionCommodityUpdate.setStatus(TransactionStatusEnum.ship.getStatus());
                transactionCommodityUpdate.setSubstate(0);
                this.updateById(transactionCommodityUpdate);
                transactionCommodityStatusLogService.saveStatusLog(userId, transactionCommodity.getId(), transactionCommodity.getRecordId(), 3, transactionCommodityUpdate.getStatus(), transactionCommodityUpdate.getSubstate());
            }
        }

    }


    @Transactional
    @Override
    public void afterSale(ApiShipDto apiShipDto, Long userId, HttpServletRequest request) {
        User byId = adminUserService.getById(userId);
        TransactionCommodity transactionCommodity = getCommodity(apiShipDto, request);
        if (byId.getUserRoleId().equals(User.member_role_supplier)) {
            if (transactionCommodity.getSubstate().equals(TransactionStatusEnum.after_sales_supplier_confirm_receipt.getStatus())) {
                TransactionCommodity transactionCommodityUpdate = saveTrCommodityLogistics(apiShipDto, userId, transactionCommodity.getRecordId(), transactionCommodity.getId(), type_exchange);
                transactionCommodityUpdate.setSubstate(after_sales_supplier_ship.getStatus());
                this.updateById(transactionCommodityUpdate);
                transactionCommodityStatusLogService.saveStatusLog(userId, transactionCommodity.getId(), transactionCommodity.getRecordId(), 3, transactionCommodity.getStatus(), transactionCommodityUpdate.getSubstate());
            }
        } else {
            TransactionCommodity transactionCommodityUpdate = saveTrCommodityLogistics(apiShipDto, userId, transactionCommodity.getRecordId(), transactionCommodity.getId(), type_return);
            transactionCommodityUpdate.setSubstate(TransactionStatusEnum.after_sales_user_shipment.getStatus());
            this.updateById(transactionCommodityUpdate);
            transactionCommodityStatusLogService.saveStatusLog(userId, transactionCommodity.getId(), transactionCommodity.getRecordId(), 0, transactionCommodity.getStatus(), transactionCommodityUpdate.getSubstate());
        }

    }

    private TransactionCommodity getCommodity(ApiShipDto apiShipDto, HttpServletRequest request) {
        TransactionRecord record = transactionRecordService.getById(apiShipDto.getRecordId());
        if (record == null) {
            throw new RRException(messageUtils.getMessage("api.transaction.record.isEmpty", request));
        }
        TransactionCommodity transactionCommodity = this.baseMapper.selectByIdForUpdate(apiShipDto.getTransactionCommodityId());
        if (transactionCommodity == null) {
            throw new RRException(messageUtils.getMessage("api.transaction.commodity.isEmpty", request));
        }
        return transactionCommodity;
    }

    private TransactionCommodity saveTrCommodityLogistics(ApiShipDto apiShipDto, Long userId, Long recordId, Long transactionCommodityId, Integer type) {
        TransactionCommodityLogistics logistics = new TransactionCommodityLogistics();
        logistics.setCreateTime(new Date());
        logistics.setDeliveryNum(apiShipDto.getNum());
        logistics.setRecordId(recordId);
        logistics.setTransactionCommodityId(transactionCommodityId);
        logistics.setUserId(userId);
        logistics.setType(type);
        commodityLogisticsService.save(logistics);

        TransactionCommodity transactionCommodityUpdate = new TransactionCommodity();
        transactionCommodityUpdate.setId(transactionCommodityId);
        return transactionCommodityUpdate;
    }

    @Override
    public Map<String, Object> selectReceivingAddress(Long transactionCommodityId, Long userId, HttpServletRequest request) {
        QueryWrapper<TransactionCommodityDelivery> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        wrapper.eq("transaction_commodity_id", transactionCommodityId);
        wrapper.eq("type", TransactionCommodityDelivery.TYPE_SUPPLIER);
        TransactionCommodityDelivery commodityDelivery = this.transactionCommodityDeliveryService.getOne(wrapper);
        if (commodityDelivery == null) {
            throw new RRException(messageUtils.getMessage("api.order.unassigned.supplier", request));
        }
        UserSupplierApplication supplierApplication = this.userSupplierApplicationService.selectByUserId(commodityDelivery.getUserId());
        if (supplierApplication == null) {
            throw new RRException(messageUtils.getMessage("api.supplier.application.not.submitted", request));
        }
        UserRespDto user = adminUserService.queryPersonInfo(supplierApplication.getUserId());
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("phone", supplierApplication.getPhone());
        returnMap.put("name", supplierApplication.getName());
        returnMap.put("address", user.getAddress());
        returnMap.put("city", user.getCityStr());
        returnMap.put("province", user.getProvinceStr());
        returnMap.put("country", user.getCountry());
        if (StringUtils.isNotBlank(user.getPostalCode())) {
            returnMap.put("postalCode", user.getPostalCode());
        }
        return returnMap;
    }


}
