package com.lingkj.project.transaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.enums.TransactionStatusEnum;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.common.validator.Assert;
import com.lingkj.project.commodity.entity.Commodity;
import com.lingkj.project.commodity.service.CommodityService;
import com.lingkj.project.message.entity.Message;
import com.lingkj.project.message.service.MessageService;
import com.lingkj.project.sys.entity.SysUserEntity;
import com.lingkj.project.sys.service.SysUserService;
import com.lingkj.project.api.transaction.dto.ApiTransactionServiceAndFileDto;
import com.lingkj.project.transaction.entity.TransactionCommodity;
import com.lingkj.project.transaction.entity.TransactionCommodityDelivery;
import com.lingkj.project.transaction.entity.TransactionServiceApplication;
import com.lingkj.project.transaction.entity.TransactionServiceFile;
import com.lingkj.project.transaction.mapper.TransactionServiceApplicationMapper;
import com.lingkj.project.transaction.service.*;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.entity.UserAccountLog;
import com.lingkj.project.user.entity.UserCommission;
import com.lingkj.project.user.service.AdminUserService;
import com.lingkj.project.user.service.UserAccountService;
import com.lingkj.project.user.service.UserCommissionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.lingkj.common.utils.ShiroUtils.getSysUserId;

/**
 * 售后  申请表
 *
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
@Service
public class TransactionServiceApplicationServiceImpl extends ServiceImpl<TransactionServiceApplicationMapper, TransactionServiceApplication> implements TransactionServiceApplicationService {

    @Autowired
    private CommodityService commodityService;
    @Autowired
    @Lazy
    private MessageService messageService;

    @Autowired
    @Lazy
    private AdminUserService adminUserService;
    @Autowired
    @Lazy
    private UserCommissionService userCommissionService;
    @Autowired
    @Lazy
    private UserAccountService userAccountService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TransactionCommodityService transactionCommodityService;
    @Autowired
    private TransactionServiceFileService transactionServiceFileService;
    @Autowired
    private TransactionCommodityStatusLogService transactionCommodityStatusLogService;
    @Autowired
    private TransactionCommodityDeliveryService transactionCommodityDeliveryService;
    @Autowired
    private MessageUtils messageUtils;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<TransactionServiceApplication> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank((String) params.get("status"))) {
            wrapper.eq("status", params.get("status"));
        }
        if (StringUtils.isNotBlank((String) params.get("type"))) {
            wrapper.eq("type", params.get("type"));
        }

        IPage<TransactionServiceApplication> page = this.page(
                new Query<TransactionServiceApplication>(params).getPage(),
                wrapper.orderByDesc("create_time")
        );
        List<TransactionServiceApplication> records = page.getRecords();
        if (records != null) {
            for (TransactionServiceApplication transactionServiceApplication : records) {

                if(transactionServiceApplication != null){
                    //得到退换货订单的id
                    if (transactionServiceApplication.getTransactionCommodityId() != null) {
                        Long transactionCommodityId = transactionServiceApplication.getTransactionCommodityId();
                        //通过订单id查询产品id 和订单号
                        TransactionCommodity transactionCommodity = transactionCommodityService.getById(transactionCommodityId);
                       if(transactionCommodity != null){
                           //添加订单号
                           transactionServiceApplication.setTransactionId(transactionCommodity.getTransactionId());
                           Long commodityId = transactionCommodity.getCommodityId();
                           //得到的产品存在
                           if (commodityId != null) {
                               Commodity commodity = commodityService.getById(commodityId);
                               transactionServiceApplication.setCommodityName(commodity.getName());
                           }
                       }

                    }
                }


                //得到申请人的id
                if (transactionServiceApplication.getUserId() != null) {
                    Long userId = transactionServiceApplication.getUserId();
                    //如果用户存在
                    if (adminUserService.getById(userId) != null) {
                        User adminUser = adminUserService.getById(userId);
                        transactionServiceApplication.setUserName(adminUser.getUserName());
                        transactionServiceApplication.setName(adminUser.getName());
                    }

                }
                //得到审核人的id
                if (transactionServiceApplication.getApplicationBy() != null) {
                    Long applicationBy = transactionServiceApplication.getApplicationBy();
                    //如果用户存在
                    if (sysUserService.getById(applicationBy) != null) {
                        SysUserEntity sysUserEntity = sysUserService.getById(applicationBy);
                        String applicationName = sysUserEntity.getUsername();
                        transactionServiceApplication.setApplicationName(applicationName);
                    }
                }
            }
        }


        return new PageUtils(page);
    }

    @Override
    public TransactionServiceApplication getById(Long id) {
        TransactionServiceApplication transactionServiceApplication = this.baseMapper.selectById(id);
        //得到退换货产品的id
        if (transactionServiceApplication.getTransactionCommodityId() != null) {
            Long transactionCommodityId = transactionServiceApplication.getTransactionCommodityId();
            //得到的产品存在
            //通过订单id查询产品id 和订单号
            TransactionCommodity transactionCommodity = transactionCommodityService.getById(transactionCommodityId);
            if(transactionCommodity != null){
                if (transactionCommodity.getCommodityId() != null) {
                    Long commodityId = transactionCommodity.getCommodityId();
                    Commodity commodity = commodityService.getById(commodityId);
                    String commodityName = commodity.getName();
                    transactionServiceApplication.setCommodityName(commodityName);
                }
            }

        }

        //得到申请人的id
        if (transactionServiceApplication.getUserId() != null) {
            Long userId = transactionServiceApplication.getUserId();
            //如果用户存在
            if (adminUserService.getById(userId) != null) {
                User adminUser = adminUserService.getById(userId);
                String userName = adminUser.getName();
                transactionServiceApplication.setUserName(userName);
            }

        }
        //得到审核人的id
        if (transactionServiceApplication.getApplicationBy() != null) {
            Long applicationBy = transactionServiceApplication.getApplicationBy();
            //如果用户存在
            if (sysUserService.getById(applicationBy) != null) {
                SysUserEntity sysUserEntity = sysUserService.getById(applicationBy);
                String applicationName = sysUserEntity.getUsername();
                transactionServiceApplication.setApplicationName(applicationName);
            }
        }
        //查询退换货上传的图片url
        List<TransactionServiceFile> transactionServiceFiles = this.queryFileList(id);
        transactionServiceApplication.setFileList(transactionServiceFiles);
        return transactionServiceApplication;
    }

    @Override
    public TransactionServiceApplication getByTrCommodityId(Long trCommodityId) {
        QueryWrapper<TransactionServiceApplication> wrapper = new QueryWrapper<>();
        wrapper.eq("transaction_commodity_id",trCommodityId);
        return this.getOne(wrapper);
    }

    @Override
    public TransactionServiceApplication getByTrCommodityIdAndUserId(Long userId, Long trCommodityId) {
        QueryWrapper<TransactionServiceApplication> wrapper = new QueryWrapper<>();
        wrapper.eq("user_Id",userId);
        wrapper.eq("transaction_commodity_id",trCommodityId);
        TransactionServiceApplication one = this.getOne(wrapper);
        return one;
    }

    @Override
    public void removeById(Long id) {
        TransactionServiceApplication transactionServiceApplication = this.baseMapper.selectById(id);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveService(ApiTransactionServiceAndFileDto apiTransactionServiceAndFileDto, Long userId, HttpServletRequest request) {
        Assert.isBlank(apiTransactionServiceAndFileDto.getReturnReason(),messageUtils.getMessage("api.afterSale.application.reason", request),400);
        Assert.isBlank(apiTransactionServiceAndFileDto.getReturnExplain(),messageUtils.getMessage("api.afterSale.application.notes", request),400);
        if(apiTransactionServiceAndFileDto.getList()==null||apiTransactionServiceAndFileDto.getList().size()<=0){
            throw new RRException(messageUtils.getMessage("api.upload.pictures.null", request),400);
        }
        TransactionCommodity byId = transactionCommodityService.selectByIdForUpdate(apiTransactionServiceAndFileDto.getTransactionCommodityId());
        byId.setStatus(TransactionStatusEnum.applyForAfterSale.getStatus());
        byId.setSubstate(TransactionStatusEnum.after_sales_application.getStatus());
        transactionCommodityService.updateById(byId);

        TransactionServiceApplication  trService= this.getByTrCommodityIdAndUserId(userId, byId.getId());
        if(trService!=null){
            trService.setType(apiTransactionServiceAndFileDto.getType());
            trService.setReturnReason(apiTransactionServiceAndFileDto.getReturnReason());
            trService.setReturnExplain(apiTransactionServiceAndFileDto.getReturnExplain());
            trService.setStatus(0);
            trService.setReason(null);
            trService.setApplicationBy(null);
            trService.setApplicationTime(null);
            this.updateById(trService);
            QueryWrapper<TransactionServiceFile> wrapper = new QueryWrapper<>();
            wrapper.eq("user_Id",userId);
            wrapper.eq("service_id",trService.getId());
            List<TransactionServiceFile> list = transactionServiceFileService.list(wrapper);
            if(list!=null&&list.size()>0){
                transactionServiceFileService.remove(wrapper);
            }
            saveTrServiceFileList(apiTransactionServiceAndFileDto.getList(), userId, trService.getId());
        }else {
            TransactionServiceApplication transactionService = new TransactionServiceApplication();
            transactionService.setType(apiTransactionServiceAndFileDto.getType());
            transactionService.setReturnReason(apiTransactionServiceAndFileDto.getReturnReason());
            transactionService.setReturnExplain(apiTransactionServiceAndFileDto.getReturnExplain());
            transactionService.setCreateTime(new Date());
            transactionService.setUserId(userId);
            transactionService.setTransactionCommodityId(apiTransactionServiceAndFileDto.getTransactionCommodityId());
            transactionService.setStatus(0);
            this.save(transactionService);
            saveTrServiceFileList(apiTransactionServiceAndFileDto.getList(), userId, transactionService.getId());
        }
        transactionCommodityStatusLogService.saveStatusLog(userId, byId.getId(), byId.getRecordId(), apiTransactionServiceAndFileDto.getUserType(), 10, null);
    }

    private void saveTrServiceFileList(List<TransactionServiceFile> serviceFileList, Long userId, Long trServiceId) {
        for (TransactionServiceFile file : serviceFileList) {
            file.setUserId(userId);
            file.setServiceId(trServiceId);
            transactionServiceFileService.save(file);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInfo(TransactionServiceApplication entity, HttpServletRequest request) {
        TransactionServiceApplication serviceApplication = this.baseMapper.selectByIdForUpdate(entity.getId());
        TransactionServiceApplication serviceApplicationUpdate = new TransactionServiceApplication();
        TransactionCommodity commodity = transactionCommodityService.selectByIdForUpdate(serviceApplication.getTransactionCommodityId());

        serviceApplicationUpdate.setId(entity.getId());
        serviceApplicationUpdate.setStatus(entity.getStatus());
        serviceApplicationUpdate.setApplicationBy(getSysUserId());
        serviceApplicationUpdate.setApplicationTime(new Date());
        //通知消息
        Message message = new Message();
        message.setCreateSysUserId(getSysUserId());
        message.setCreateTime(new Date());
        message.setType(Message.type_user);
        message.setTitle( messageUtils.getMessage("manage.after.sales.application.notice", request));
        message.setUserId(serviceApplication.getUserId());
        if (entity.getStatus().equals(TransactionServiceApplication.STATUS_FAIL)) {
            message.setContent( messageUtils.getMessage("manage.after.sales.application.failed", request) + entity.getReason());
            this.messageService.save(message);
            serviceApplicationUpdate.setReason(entity.getReason());
            baseMapper.updateById(serviceApplicationUpdate);
            commodity.setSubstate(TransactionStatusEnum.after_sales_application_failed.getStatus());
            transactionCommodityService.updateById(commodity);
            transactionCommodityStatusLogService.saveStatusLog(serviceApplication.getUserId(),commodity.getId(),commodity.getRecordId(),1,commodity.getStatus(),commodity.getSubstate());
        } else if (entity.getStatus().equals(TransactionServiceApplication.STATUS_SUCCESS)) {

            baseMapper.updateById(serviceApplicationUpdate);
            commodity.setSubstate(TransactionStatusEnum.after_sales_application_success.getStatus());
            transactionCommodityService.updateById(commodity);
            transactionCommodityStatusLogService.saveStatusLog(serviceApplication.getUserId(),commodity.getId(),commodity.getRecordId(),1,commodity.getStatus(),commodity.getSubstate());
            if (serviceApplication.getType().equals(TransactionServiceApplication.TYPE_REFUND)){
                //扣除 供应商  佣金
//              transactionCommodityDeliveryService.selectByTrCommodityId()
                TransactionCommodityDelivery transactionCommodityDelivery = transactionCommodityDeliveryService.selectByTrCommodityIdType(commodity.getId(), TransactionCommodityDelivery.TYPE_SUPPLIER);
                //修改结算状态
                TransactionCommodityDelivery commodityDeliveryUpdate=new TransactionCommodityDelivery();
                commodityDeliveryUpdate.setId(transactionCommodityDelivery.getId());
                commodityDeliveryUpdate.setSettlementStatus(TransactionCommodityDelivery.SETTLEMENT_STATUS_NO);
                this.transactionCommodityDeliveryService.updateById(commodityDeliveryUpdate);
                this.userAccountService.updateUserAccount(transactionCommodityDelivery.getUserId(), UserAccountLog.type_out, commodity.getFactoryPrice(), "订单退款扣除", transactionCommodityDelivery.getRecordId(), transactionCommodityDelivery.getTransactionCommodityId());
            }
            message.setContent(messageUtils.getMessage("manage.after.sales.application.success", request));
            this.messageService.save(message);
        }

    }

    /**
     * 查询退换货上传的图片url
     *
     * @param id 售后服务事件id
     * @return java.util.List<com.lingkj.project.transaction.entity.TransactionServiceFile>
     * @author XXX <XXX@163.com>
     * @date 2019/10/28 14:36
     */
    private List<TransactionServiceFile> queryFileList(Long id) {
        List<TransactionServiceFile> transactionServiceFiles = baseMapper.queryFileList(id);
        return transactionServiceFiles;
    }

}
