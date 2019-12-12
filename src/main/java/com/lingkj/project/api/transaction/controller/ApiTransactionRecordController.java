package com.lingkj.project.api.transaction.controller;

import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.enums.TransactionStatusEnum;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.api.transaction.dto.*;
import com.lingkj.project.integral.service.UserIntegralService;
import com.lingkj.project.transaction.dto.ReviewManuscriptReqDto;
import com.lingkj.project.transaction.entity.TransactionCommodity;
import com.lingkj.project.transaction.entity.TransactionCommodityStatusLog;
import com.lingkj.project.transaction.entity.TransactionRecord;
import com.lingkj.project.transaction.service.TransactionCommodityService;
import com.lingkj.project.transaction.service.TransactionCommodityStatusLogService;
import com.lingkj.project.transaction.service.TransactionRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ApiTransactionRecordController
 *
 * @author chen yongsong
 * @className ApiTransactionRecordController
 * @date 2019/9/19 11:22
 */
@Api("订单记录")
@Slf4j
@RestController
@RequestMapping("/api/transaction/record")
public class ApiTransactionRecordController {

    @Autowired
    private TransactionRecordService transactionRecordService;
    @Autowired
    private TransactionCommodityService transactionCommodityService;

    @Login
    @ApiOperation(value = "用户普通商品下单接口")
    @PostMapping("/order")
    public R order(@RequestBody ApiTransactionRecordReqDto apiTransactionRecordReqDto, @RequestAttribute("userId") Long userId, HttpServletRequest request) {
        Map<String, Object> map = this.transactionRecordService.order(apiTransactionRecordReqDto, userId, request);
        return R.ok().put("transaction", map);
    }

    @Login
    @ApiOperation(value = "用户确认确认支付")
    @PostMapping("/confirmPayment")
    public R confirmPayment(@RequestBody ApiConfirmPaymentDto confirmPaymentDto, @RequestAttribute("userId") Long userId, HttpServletRequest request) {
        this.transactionRecordService.confirmPayment(confirmPaymentDto, userId, request);
        return R.ok();
    }

    @Login
    @ApiOperation("订单分页查询")
    @GetMapping("/queryPage")
    public R queryRecordPage(@RequestAttribute("userId") Long userId, Page page, Integer type, HttpServletRequest request) {
        PageUtils pageUtils = transactionRecordService.queryPageApi(userId, page, type, request);
        return R.ok().put("page", pageUtils);
    }

    @Login
    @ApiOperation("订单统计")
    @GetMapping("/queryRecordCount")
    public R queryRecordCount(@RequestAttribute("userId") Long userId, HttpServletRequest request) {
        Map<String, Integer> map = transactionRecordService.queryRecordCountList(userId, request);
        return R.ok().put("map", map);
    }

    @Login
    @ApiOperation("订单详情查询")
    @GetMapping("/info/{id}")
    public R transactionInfoApi(@RequestAttribute("userId") Long userId, @PathVariable("id") Long id, HttpServletRequest request) {
        ApiTransactionRecordDto apiTransactionRecordDto = transactionRecordService.transactionInfoApi(userId, id, request);
        return R.ok().put("info", apiTransactionRecordDto);
    }

    @Login
    @ApiOperation("积分商品下单接口")
    @PostMapping("/integralOrder")
    public R integralOrder(@RequestBody ApiIntegralTransactionRecordDto integralTransactionRecordDto, @RequestAttribute("userId") Long userId,HttpServletRequest request) {
        return transactionRecordService.integralTransaction(integralTransactionRecordDto, userId,request);
    }

    /**
     * 用户确认稿件，且交给平台审核
     */
    @ApiOperation("订单确认稿件，交给平台审核")
    @Login
    @GetMapping("/confirmationOfManuscript")
    public R confirmationOfManuscript(Long transactionCommodityId, @RequestAttribute("userId") Long userId,HttpServletRequest request) {
        return transactionCommodityService.updateStatusOrSubstate(transactionCommodityId, userId, 0, TransactionStatusEnum.deliveryDesigner.getStatus(), TransactionStatusEnum.design_determined.getStatus(),request);
    }

    /**
     * 用户取消订单
     */
    @ApiOperation("取消订单")
    @GetMapping("/cancellationOfOrder/{transactionRecordId}")
    public R cancellationOfOrderUpdateStatus(@PathVariable("transactionRecordId") Long transactionRecordId) {
        return transactionRecordService.cancellationOfOrder(transactionRecordId);
    }

    /**
     * 用户确认收货
     */
    @ApiOperation("确认收货")
    @GetMapping("/confirmReceipt")
    @Login
    public R confirmReceiptUpdateStatus(Long transactionCommodityId, @RequestAttribute("userId") Long userId,HttpServletRequest request) {
        TransactionCommodity transactionCommodity = transactionCommodityService.selectByIdForUpdate(transactionCommodityId);
        if(!transactionCommodity.getSubstate().equals(TransactionStatusEnum.after_sales_supplier_ship.getStatus())){
            return transactionCommodityService.updateStatusOrSubstate(transactionCommodityId, userId, 0, TransactionStatusEnum.confirmReceipt.getStatus(), null,request);
        }else {
            return transactionCommodityService.updateStatusOrSubstate(transactionCommodityId, userId, 0,TransactionStatusEnum.applyForAfterSale.getStatus(),  TransactionStatusEnum.after_sales_user_confirm_receipt.getStatus(),request);
        }
    }

    /**
     * 用户上传稿件
     */
    @ApiOperation("用户上传稿件")
    @GetMapping("/userUpload")
    @Login
    public R userUpload(ApiUploadManuscriptDto apiUploadManuscriptDto, @RequestAttribute("userId") Long userId, HttpServletRequest request) {
        TransactionCommodity byId = transactionCommodityService.selectByIdForUpdate(apiUploadManuscriptDto.getTransactionCommodityId());
        byId.setManuscriptUrl(apiUploadManuscriptDto.getManuscriptUrl());
        transactionCommodityService.updateById(byId);
        return transactionCommodityService.updateStatusOrSubstate(apiUploadManuscriptDto.getTransactionCommodityId(), userId, 0, TransactionStatusEnum.reviewManuscript.getStatus(), TransactionStatusEnum.manuscript_review.getStatus(),request);
    }

    /**
     * 立即付款
     */
    @ApiOperation("立即付款订单详情")
    @GetMapping("/payment")
    @Login
    public R payment(@RequestAttribute("userId") Long userId, Long transactionRecordId, HttpServletRequest request) {
        ApiTransactionRecordDto transactionRecordDto = transactionRecordService.transactionInfoApi(userId, transactionRecordId, request);
        return R.ok().put("info", transactionRecordDto);
    }

    /**
     * 订单商品详情
     */
    @Login
    @ApiOperation(value = "订单商品详情")
    @GetMapping("/transactionCommodityDto")
    public R getTransactionCommodityDto(Long id, Long transactionRecordId, HttpServletRequest request) {
        ApiTransactionCommodityRespDto apiTransactionCommodityRespDto = transactionCommodityService.selectByIdOne(id, transactionRecordId, request);
        return R.ok().put("info", apiTransactionCommodityRespDto);
    }


    /**
     * 查询 供应商收货地址
     */
    @Login
    @ApiOperation(value = "查询 供应商收货地址")
    @GetMapping("/supplierReceivingAddress")
    public R supplierReceivingAddress(Long transactionCommodityId, @RequestAttribute("userId") Long userId, HttpServletRequest request) {
        Map<String, Object> map = this.transactionCommodityService.selectReceivingAddress(transactionCommodityId, userId,request);
        return R.ok().put("info", map);
    }



}
