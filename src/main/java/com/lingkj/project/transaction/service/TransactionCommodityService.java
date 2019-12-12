package com.lingkj.project.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.api.transaction.dto.ApiShipDto;
import com.lingkj.project.api.transaction.dto.ApiTransactionCommodityRespDto;
import com.lingkj.project.transaction.dto.TransactionCommodityRespDto;
import com.lingkj.project.transaction.dto.TransactionDeliveryListReqDto;
import com.lingkj.project.transaction.dto.TransactionDeliveryRespDto;
import com.lingkj.project.transaction.entity.TransactionCommodity;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
public interface TransactionCommodityService extends IService<TransactionCommodity> {

    PageUtils queryPage(Map<String, Object> params);

    List<ApiTransactionCommodityRespDto> selectByTransactionId(String transactionId);

    List<TransactionCommodityRespDto> queryCommodityByRecordId(Long id);

    void updateStatusByRecordId(Long recordId, Integer status);

    Integer countDelivery(TransactionDeliveryListReqDto reqDto);

    List<TransactionDeliveryRespDto> queryDeliver(@Param("reqDto") TransactionDeliveryListReqDto reqDto, @Param("pageSize") Integer pageSize, @Param("limit") Integer limit);

    TransactionCommodity selectByIdForUpdate(Long id);

    R updateStatusOrSubstate(Long id,Long userId,Integer type,Integer status,Integer substate,HttpServletRequest request);

    ApiTransactionCommodityRespDto selectByIdOne(Long id, Long transactionRecordId, HttpServletRequest request);

    /**
     * 申请售后，返工的用户和供应商确认收货
     * @param trCommodityId
     * @param userId
     */

    void  userAfterSalesConfirmation(Long trCommodityId,Long userId);
    /**
     * 发货
     * @param apiShipDto
     * @param userId
     * @param request
     */
    void ship(ApiShipDto apiShipDto, Long userId, HttpServletRequest request);

    /**
     * 售后换货，重新发货
     * @param apiShipDto
     * @param userId
     * @param request
     */
   void afterSale(ApiShipDto apiShipDto, Long userId, HttpServletRequest request);
    /**
     * 查询供应商
     * @param transactionCommodityId
     * @param userId
     * @return
     */
    Map<String, Object> selectReceivingAddress(Long transactionCommodityId, Long userId, HttpServletRequest request);
}

