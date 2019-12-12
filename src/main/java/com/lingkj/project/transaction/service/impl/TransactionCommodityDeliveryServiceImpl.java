package com.lingkj.project.transaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.enums.TransactionStatusEnum;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.project.api.transaction.dto.ApiUploadManuscriptDto;
import com.lingkj.project.api.transaction.dto.ApiChangeCommodityDeliverReqDto;
import com.lingkj.project.transaction.dto.TransactionCommodityDeliveryAndCountRespDto;
import com.lingkj.project.transaction.dto.TransactionCommodityDeliveryRespDto;
import com.lingkj.project.transaction.entity.TransactionCommodity;
import com.lingkj.project.transaction.entity.TransactionCommodityDelivery;
import com.lingkj.project.transaction.entity.TransactionServiceApplication;
import com.lingkj.project.transaction.mapper.TransactionCommodityDeliveryMapper;
import com.lingkj.project.transaction.service.TransactionCommodityDeliveryService;
import com.lingkj.project.transaction.service.TransactionCommodityService;
import com.lingkj.project.transaction.service.TransactionCommodityStatusLogService;
import com.lingkj.project.transaction.service.TransactionServiceApplicationService;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.service.AdminUserService;
import com.lingkj.project.user.service.UserApplicationFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理人员 分配订单给  设计师/供应商
 *
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
@Service
public class TransactionCommodityDeliveryServiceImpl extends ServiceImpl<TransactionCommodityDeliveryMapper, TransactionCommodityDelivery> implements TransactionCommodityDeliveryService {
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private TransactionCommodityService transactionCommodityService;
    @Autowired
    private UserApplicationFileService userApplicationFileService;
    @Autowired
    private TransactionCommodityStatusLogService transactionCommodityStatusLogService;
    @Autowired
    private MessageUtils messageUtils;
    @Autowired
    @Lazy
    private TransactionServiceApplicationService serviceApplicationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TransactionCommodityDelivery> page = this.page(
                new Query<TransactionCommodityDelivery>(params).getPage(),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public TransactionCommodityDelivery selectById(Long recordId, Long transactionCommodityId, Integer type) {
        QueryWrapper<TransactionCommodityDelivery> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        wrapper.eq("record_id", recordId);
        wrapper.eq("transaction_commodity_id", transactionCommodityId);
        wrapper.eq("type", type);
        return this.baseMapper.selectOne(wrapper);
    }

    @Override
    public TransactionCommodityDelivery selectByRecordIdForUpdate(Long recordId, Long transactionCommodityId, Integer type) {
        return this.baseMapper.selectByRecordIdForUpdate(recordId, transactionCommodityId, type);
    }

    @Override
    public List<TransactionCommodityDeliveryRespDto> queryWorkSheets(Long userId, Integer type, Integer status, Integer start, Integer end) {
        return this.baseMapper.queryWorkSheet(userId, type, status, start, end);
    }

    @Override
    public void queryWorkStatusCount(Long userId, Integer type, TransactionCommodityDeliveryAndCountRespDto trans) {
    }

    @Override
    public PageUtils queryCommodityDeliverPageApi(Page page, Long userId, Integer type) {
        User user = adminUserService.getById(userId);
        Integer totalCount = this.baseMapper.queryCommodityDeliverPageApiCount(userId, type, user.getUserRoleId().intValue());
        List<TransactionCommodityDeliveryRespDto> list = this.baseMapper.queryCommodityDeliverPageApi(page.getPageSize(), page.getLimit(), userId, type, user.getUserRoleId().intValue());
        for (TransactionCommodityDeliveryRespDto  dto: list) {
            TransactionServiceApplication byTrCommodityId = serviceApplicationService.getByTrCommodityId(dto.getTransactionCommodityId());
            if(byTrCommodityId!=null){
                dto.setTransactionServiceType(byTrCommodityId.getType());
            }
        }
        return new PageUtils(list, totalCount, page.getPageSize(), page.getPage());
    }

    @Override
    @Transactional
    public void changeCommodityDeliver(ApiChangeCommodityDeliverReqDto commodityDeliverReqDto, Long userId, HttpServletRequest request) {
        TransactionCommodityDelivery commodityDelivery = getTransactionCommodityDelivery(userId, commodityDeliverReqDto.getDeliveryId(),request);
        TransactionCommodity transactionCommodity = getCommodity(commodityDelivery,request);
        TransactionCommodityDelivery commodityDeliveryUpdate = new TransactionCommodityDelivery();
        TransactionCommodity transactionCommodityUpdate = new TransactionCommodity();
        User byId = adminUserService.getById(userId);
        if (byId.getUserRoleId() == 3) {
            if (!transactionCommodity.getStatus().equals(TransactionStatusEnum.deliveryDesigner.getStatus())) {
                throw new RRException(messageUtils.getMessage("api.transaction.commodity.status.change", request));
            }
            if (!transactionCommodity.getSubstate().equals(TransactionStatusEnum.design_delivered.getStatus())) {
                throw new RRException(messageUtils.getMessage("api.transaction.commodity.canNot.operation", request));
            }
            if (commodityDeliverReqDto.getStatus().equals(TransactionCommodityDelivery.STATUS_ACCEPTED)) {
                commodityDeliveryUpdate.setId(commodityDelivery.getId());
                commodityDeliveryUpdate.setStatus(TransactionCommodityDelivery.STATUS_ACCEPTED);

                transactionCommodityUpdate.setId(transactionCommodity.getId());
                transactionCommodityUpdate.setSubstate(TransactionStatusEnum.design_agree.getStatus());
            } else if (commodityDeliverReqDto.getStatus().equals(TransactionCommodityDelivery.STATUS_REFUSE)) {
                commodityDeliveryUpdate.setId(commodityDelivery.getId());
                commodityDeliveryUpdate.setStatus(TransactionCommodityDelivery.STATUS_REFUSE);

                transactionCommodityUpdate.setId(transactionCommodity.getId());
                transactionCommodityUpdate.setSubstate(TransactionStatusEnum.design_reject.getStatus());

            }
            //记录设计师改变状态type2
            transactionCommodityStatusLogService.saveStatusLog(userId, transactionCommodity.getId(), transactionCommodity.getRecordId(), 2, transactionCommodity.getStatus(), transactionCommodityUpdate.getSubstate());
        } else if (byId.getUserRoleId() == 4) {
            if (!transactionCommodity.getStatus().equals(TransactionStatusEnum.designatedSupplier.getStatus())) {
                throw new RRException(messageUtils.getMessage("api.transaction.commodity.status.change", request));
            }
            if (!transactionCommodity.getSubstate().equals(TransactionStatusEnum.supplier_delivered.getStatus())) {
                throw new RRException(messageUtils.getMessage("api.transaction.commodity.canNot.operation", request));
            }
            if (commodityDeliverReqDto.getStatus().equals(TransactionCommodityDelivery.STATUS_ACCEPTED)) {
                commodityDeliveryUpdate.setId(commodityDelivery.getId());
                commodityDeliveryUpdate.setStatus(TransactionCommodityDelivery.STATUS_ACCEPTED);

                transactionCommodityUpdate.setId(transactionCommodity.getId());
                transactionCommodityUpdate.setSubstate(TransactionStatusEnum.supplier_agree.getStatus());
            } else if (commodityDeliverReqDto.getStatus().equals(TransactionCommodityDelivery.STATUS_REFUSE)) {
                commodityDeliveryUpdate.setId(commodityDelivery.getId());
                commodityDeliveryUpdate.setStatus(TransactionCommodityDelivery.STATUS_REFUSE);

                transactionCommodityUpdate.setId(transactionCommodity.getId());
                transactionCommodityUpdate.setSubstate(TransactionStatusEnum.supplier_reject.getStatus());
            }
            //记录供应商type为3
            transactionCommodityStatusLogService.saveStatusLog(userId, transactionCommodity.getId(), transactionCommodity.getRecordId(), 3, transactionCommodity.getStatus(), transactionCommodityUpdate.getSubstate());
        }
        if (commodityDeliveryUpdate.getId() != null) {
            this.baseMapper.updateById(commodityDeliveryUpdate);
        }
        if (transactionCommodityUpdate.getId() != null) {

            this.transactionCommodityService.updateById(transactionCommodityUpdate);
        }


    }

    private TransactionCommodity getCommodity(TransactionCommodityDelivery commodityDelivery, HttpServletRequest request) {
        TransactionCommodity transactionCommodity = transactionCommodityService.selectByIdForUpdate(commodityDelivery.getTransactionCommodityId());
        if (transactionCommodity == null) {
            throw new RRException(messageUtils.getMessage("api.transaction.commodity.isEmpty", request));
        }
        return transactionCommodity;
    }

    @Override
    @Transactional
    public void uploadManuscript(ApiUploadManuscriptDto uploadManuscriptDto, Long userId, HttpServletRequest request) {
        TransactionCommodityDelivery commodityDelivery = getTransactionCommodityDelivery(userId, uploadManuscriptDto.getDeliveryId(),request);
        TransactionCommodity transactionCommodity = getCommodity(commodityDelivery,request);
        if (!transactionCommodity.getStatus().equals(TransactionStatusEnum.deliveryDesigner.getStatus())) {
            throw new RRException(messageUtils.getMessage("api.transaction.commodity.status.change", request));
        }
        if (!transactionCommodity.getSubstate().equals(TransactionStatusEnum.design_agree.getStatus())) {
            throw new RRException(messageUtils.getMessage("api.transaction.commodity.canNot.operation", request));
        }
        //上传稿件 并 更改订单
        TransactionCommodity transactionCommodityUpdate = new TransactionCommodity();
        transactionCommodityUpdate.setId(transactionCommodity.getId());
        transactionCommodityUpdate.setSubstate(TransactionStatusEnum.design_complete.getStatus());
        transactionCommodityUpdate.setManuscriptUrl(uploadManuscriptDto.getManuscriptUrl());
        this.transactionCommodityService.updateById(transactionCommodityUpdate);
        Integer type = 1;
        //上传作品
        userApplicationFileService.uploadWork(uploadManuscriptDto.getImages(), userId, type);

    }

    @Override
    public Map<String, Integer> queryCommodityDeliverCountApi(Long userId) {
        /*type 0-全部  1 未处理订单  2 未确认订单   3 已确认订单 4 售后订单*/
        User user = adminUserService.getById(userId);
        Integer totalAll = this.baseMapper.queryCommodityDeliverPageApiCount(userId, 0, user.getUserRoleId().intValue());
        Integer totalOne = this.baseMapper.queryCommodityDeliverPageApiCount(userId, 1, user.getUserRoleId().intValue());
        Integer totalTwo = this.baseMapper.queryCommodityDeliverPageApiCount(userId, 2, user.getUserRoleId().intValue());
        Integer totalThr = this.baseMapper.queryCommodityDeliverPageApiCount(userId, 3, user.getUserRoleId().intValue());
        Map<String, Integer> map = new HashMap<>(5);
        map.put("totalAll", totalAll);
        map.put("totalOne", totalOne);
        map.put("totalTwo", totalTwo);
        map.put("totalThr", totalThr);
        if (user.getUserRoleId().equals(User.member_role_supplier)) {
            Integer totalThi = this.baseMapper.queryCommodityDeliverPageApiCount(userId, 4, user.getUserRoleId().intValue());
            map.put("totalThi", totalThi);
        }
        return map;
    }

    @Override
    public TransactionCommodityDelivery selectByTrCommodityId(Long userId, Long trCommodityId) {
        return this.baseMapper.selectByTransactionCommodityId(userId, trCommodityId);
    }

    @Override
    public TransactionCommodityDelivery selectByTrCommodityIdType(Long transactionCommodityId, Integer typeSupplier) {
        return this.baseMapper.selectByTrCommodityIdType(transactionCommodityId, typeSupplier);
    }

    @Override
    public Integer countUnSettlement(Long userId, Integer type) {

        return baseMapper.countUnSettlement(userId, type);
    }

    @Override
    public List<Map<String, Object>> queryUnSettlement(Integer limit, Integer pageSize, Long userId, Integer type) {
        return baseMapper.queryUnSettlement(limit, pageSize, userId, type);
    }

    @Override
    public Integer getSettledCount(Long userId, Integer type) {

        return baseMapper.settled(userId, type);
    }

    @Override
    public List<Map<String, Object>> getSettledList(Integer limit, Integer pageSize, Long userId, Integer type) {
        return baseMapper.querySettled(limit, pageSize, userId, type);
    }



    @Override
    public void updateByUserId(Long userId) {
        baseMapper.updateByUserId(userId);
    }

    private TransactionCommodityDelivery getTransactionCommodityDelivery(Long userId, Long deliveryId,HttpServletRequest request) {
        TransactionCommodityDelivery commodityDelivery = this.baseMapper.selectByIdForUpdate(deliveryId, userId);
        if (commodityDelivery == null) {
            throw new RRException(messageUtils.getMessage("api.deliver.record.null", request));
        }
        return commodityDelivery;
    }

}
