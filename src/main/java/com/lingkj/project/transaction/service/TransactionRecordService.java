package com.lingkj.project.transaction.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.api.transaction.dto.*;
import com.lingkj.project.transaction.dto.*;
import com.lingkj.project.transaction.entity.TransactionRecord;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:25
 */
public interface TransactionRecordService extends IService<TransactionRecord> {
    /*************************************manage
     * @param transactionReqDto
     * @param page*********************************/
    PageUtils queryPage(TransactionReqDto transactionReqDto, Page page);

    /**
     * 订单详情查询
     *
     * @param id
     * @return
     */
    TransactionDetailRespDto getInfo(Long id);

    /*******************api****************************/
    /**
     * @param userId
     * @param page
     * @param type
     * @param request
     * @return
     */
    PageUtils queryPageApi(Long userId, Page page, Integer type, HttpServletRequest request);

    /**
     * @param userId
     * @param id
     * @param request
     * @return
     */


    /**
     * 用户下单
     *
     * @param apiTransactionRecordReqDto
     * @param userId
     * @param request
     * @return
     */
    Map<String, Object> order(ApiTransactionRecordReqDto apiTransactionRecordReqDto, Long userId, HttpServletRequest request);

    /**
     * 用户确认 支付
     *
     * @param confirmPaymentDto
     * @param userId
     * @param request
     */
    void confirmPayment(ApiConfirmPaymentDto confirmPaymentDto, Long userId, HttpServletRequest request);

    /**
     * 修改订单状态
     *
     * @param transactionId
     * @param sysUserId
     * @param type
     * @param request
     */
    void updateStatus(String transactionId, Long sysUserId, Integer type, HttpServletRequest request);


    /**
     * 积分商品下单
     *
     * @param apiIntegralTransactionRecordDto
     * @param userId
     * @return
     */
    R integralTransaction(ApiIntegralTransactionRecordDto apiIntegralTransactionRecordDto, Long userId,HttpServletRequest request);

    /**
     * 查询该商品的所有设计师/供应商
     *
     * @param page
     * @param reqDto
     * @return
     */
    PageUtils queryDeliveryList(Page page, TransactionDeliveryListReqDto reqDto);


    /**
     * 派送设计师/供应商
     *
     * @param reqDto
     * @param sysUserId
     * @param request
     */
    void delivery(TransactionDeliveryReqDto reqDto, Long sysUserId, HttpServletRequest request);

    Integer getUserCount(Long userId);

    /**
     * 查询稿件信息
     *
     * @param transactionCommodityId
     * @param request
     * @return
     */
    Map<String, Object> queryManuscriptInfo(Long transactionCommodityId, HttpServletRequest request);

    R cancellationOfOrder(Long id);

    void reviewManuscript(ReviewManuscriptReqDto reviewManuscriptReqDto, Long sysUserId, HttpServletRequest request);

    /**
     * 订单详情
     *
     * @param transactionRecordId
     * @param userId
     * @return
     */
    ApiTransactionRecordDto transactionInfoApi(Long userId, Long transactionRecordId, HttpServletRequest request);

    Map<String, Integer> queryRecordCountList(Long userId, HttpServletRequest request);
    int getCallback(String orderNo,String amount);
    JSONObject getVisaConfig(String orderNo);

    /**
     * 统计月售
     * @return
     */
    BigDecimal monthlySales();
}

