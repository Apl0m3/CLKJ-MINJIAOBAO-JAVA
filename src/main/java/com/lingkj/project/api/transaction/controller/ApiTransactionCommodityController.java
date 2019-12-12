package com.lingkj.project.api.transaction.controller;


import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.api.transaction.dto.*;
import com.lingkj.project.transaction.entity.TransactionCommodity;
import com.lingkj.project.transaction.entity.TransactionCommodityDelivery;
import com.lingkj.project.transaction.entity.TransactionServiceApplication;
import com.lingkj.project.transaction.service.TransactionCommodityDeliveryService;
import com.lingkj.project.transaction.service.TransactionCommodityService;
import com.lingkj.project.transaction.service.TransactionReceivingAddressService;
import com.lingkj.project.transaction.service.TransactionServiceApplicationService;
import com.lingkj.project.user.service.UserMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Administrator
 */
@Api(value = "用户工作清单接口")
@RestController
@RequestMapping("/api/transaction/commodityDeliver")
public class ApiTransactionCommodityController {

    @Autowired
    private TransactionCommodityDeliveryService transactionCommodityDeliveryService;
    @Autowired
    private TransactionCommodityService transactionCommodityService;
    @Autowired
    private UserMessageService userMessageService;
    @Autowired
    private TransactionReceivingAddressService transactionReceivingAddressService;
    @Autowired
    private TransactionServiceApplicationService serviceApplicationService;

    @Login
    @ApiOperation(value = "分页查询设计师/供应商的订单")
    @GetMapping("/queryCommodityDeliver")
    public R queryWorkSheet(Page page, Integer type, @RequestAttribute("userId") Long userId) {
        PageUtils pageUtils = transactionCommodityDeliveryService.queryCommodityDeliverPageApi(page, userId, type);
        return R.ok().put("page", pageUtils);
    }

    @Login
    @ApiOperation(value = "统计查询设计师/供应商的订单")
    @GetMapping("/queryCommodityDeliverCount")
    public R queryWorkSheetCount(@RequestAttribute("userId") Long userId) {
        Map<String, Integer> map = transactionCommodityDeliveryService.queryCommodityDeliverCountApi(userId);
        return R.ok().put("map", map);
    }

    @Login
    @ApiOperation(value = "同意或取消 设计师/供应商 订单")
    @PostMapping("/changeDeliver")
    public R changeCommodityDeliver(@RequestBody ApiChangeCommodityDeliverReqDto commodityDeliverReqDto, @RequestAttribute("userId") Long userId,HttpServletRequest request) {
        transactionCommodityDeliveryService.changeCommodityDeliver(commodityDeliverReqDto, userId,request);
        return R.ok();
    }

    @Login
    @ApiOperation(value = "设计师上传稿件")
    @PostMapping("/uploadManuscript")
    public R uploadManuscript(@RequestBody ApiUploadManuscriptDto uploadManuscriptDto, @RequestAttribute("userId") Long userId, HttpServletRequest request) {
        transactionCommodityDeliveryService.uploadManuscript(uploadManuscriptDto, userId,request);
        return R.ok();
    }

    @Login
    @ApiOperation(value = "发送站内消息")
    @PostMapping("/sendMessage")
    public R sendMessage(@RequestBody ApiSendMessageDto sendMessageDto, @RequestAttribute("userId") Long userId) {
        userMessageService.sendMessage(sendMessageDto, userId);
        return R.ok();
    }

    @Login
    @ApiOperation(value = "供应商派送订单")
    @PostMapping("/ship")
    public R ship(@RequestBody ApiShipDto apiShipDto, HttpServletRequest request, @RequestAttribute("userId") Long userId) {
        transactionCommodityService.ship(apiShipDto, userId, request);
        return R.ok();
    }

    @Login
    @ApiOperation(value = "工作清单商品订单详情")
    @GetMapping("/workDetails")
    public R workDetails(Long trCommodityId, @RequestAttribute("userId") Long userId, HttpServletRequest request) {
        TransactionCommodity byId = transactionCommodityService.getById(trCommodityId);
        ApiTransactionCommodityRespDto apiTransactionCommodityRespDto = transactionCommodityService.selectByIdOne(trCommodityId, byId.getRecordId(), request);
        TransactionCommodityDelivery delivery = transactionCommodityDeliveryService.selectByTrCommodityId(userId, trCommodityId);
        if (delivery != null) {
            apiTransactionCommodityRespDto.setDeliveryId(delivery.getId());
            if (delivery.getStatus() != null) {
                apiTransactionCommodityRespDto.setDeliveryStatus(delivery.getStatus());
            }
        }
        TransactionServiceApplication byTrCommodityId = serviceApplicationService.getByTrCommodityId(apiTransactionCommodityRespDto.getId());
        if(byTrCommodityId!=null){
            apiTransactionCommodityRespDto.setTransactionServiceType(byTrCommodityId.getType());
        }
        return R.ok().put("info", apiTransactionCommodityRespDto);
    }

    /**
     * 供应商确认收货
     *
     * @param trCommodityId
     * @param userId
     * @return
     */
    @Login
    @ApiOperation(value = "售后供应商确认物流")
    @GetMapping("/afterSalesConfirmation")
    public R afterSalesConfirmation(Long trCommodityId, @RequestAttribute("userId") Long userId) {
        transactionCommodityService.userAfterSalesConfirmation(trCommodityId, userId);
        return R.ok();
    }
    //供应商发货查询收货人地址
    @ApiOperation(value = "供应商发货查询用户收货地址")
    @GetMapping("/getConsigneeAddress")
    public R getConsigneeAddress(Long recordId) {
        ApiTransactionReceivingAddressDto apiTransactionReceivingAddressDto = transactionReceivingAddressService.selectByRecordId(recordId);
        return R.ok().put("userAddress",apiTransactionReceivingAddressDto);
    }



    //售后换货，用户和供应商重新发货
    @Login
    @ApiOperation(value = "售后换货，用户和供应商重新发货")
    @PostMapping("/againShip")
    public R againShip(@RequestBody ApiShipDto apiShipDto, HttpServletRequest request, @RequestAttribute("userId") Long userId) {
        transactionCommodityService.afterSale(apiShipDto, userId, request);
        return R.ok();
    }
}
