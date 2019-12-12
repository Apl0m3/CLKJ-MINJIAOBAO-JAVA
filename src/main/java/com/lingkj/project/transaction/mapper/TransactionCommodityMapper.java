package com.lingkj.project.transaction.mapper;

import com.lingkj.project.api.transaction.dto.ApiTransactionCommodityRespDto;
import com.lingkj.project.transaction.dto.TransactionCommodityRespDto;
import com.lingkj.project.transaction.dto.TransactionDeliveryListReqDto;
import com.lingkj.project.transaction.dto.TransactionDeliveryRespDto;
import com.lingkj.project.transaction.entity.TransactionCommodity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
@Mapper
public interface TransactionCommodityMapper extends BaseMapper<TransactionCommodity> {

    List<ApiTransactionCommodityRespDto> selectByRecordId(String transactionId);

    List<TransactionCommodityRespDto> queryCommodityByRecordId(Long id);

    /**
     * 订单状态 修改
     *
     * @param recordId
     * @param status
     */
    void updateStatusByRecordId(@Param("recordId") Long recordId, @Param("status") Integer status);

    /**
     * 查询 订单商品
     *
     * @param reqDto
     * @return
     */
    Integer countDeliverDesigner(TransactionDeliveryListReqDto reqDto);
    Integer countDeliverSupplier(TransactionDeliveryListReqDto reqDto);

    /**
     * 查询设计师/供应商
     *
     * @param reqDto
     * @return
     */
    List<TransactionDeliveryRespDto> queryDeliverDesigner(@Param("reqDto") TransactionDeliveryListReqDto reqDto, @Param("pageSize") Integer pageSize, @Param("limit") Integer limit);
    List<TransactionDeliveryRespDto> queryDeliverSupplier(@Param("reqDto") TransactionDeliveryListReqDto reqDto, @Param("pageSize") Integer pageSize, @Param("limit") Integer limit);

    /**
     * 加锁 查询
     * @param id
     * @return
     */
    TransactionCommodity selectByIdForUpdate(Long id);


    /**
     * 订单详情
     * @param id
     * @return
     */
    ApiTransactionCommodityRespDto queryCommodityById(@Param("id")Long id);
}
