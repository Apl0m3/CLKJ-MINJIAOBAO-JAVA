package com.lingkj.project.transaction.mapper;

import com.lingkj.project.transaction.dto.TransactionCommodityDeliveryRespDto;
import com.lingkj.project.transaction.entity.TransactionCommodityDelivery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 管理人员 分配订单给  设计师/供应商
 *
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
@Mapper
public interface TransactionCommodityDeliveryMapper extends BaseMapper<TransactionCommodityDelivery> {


    List<TransactionCommodityDeliveryRespDto>  queryWorkSheet(@Param("userId") Long userId, @Param("type") Integer type, @Param("status") Integer status,@Param("start")Integer start, @Param("end") Integer end);

    /**
     * 查询 管理人员 分配订单给 设计师/供应商的数量
     * @param userId
     * @param type
     * @param roleId
     * @return
     */
    Integer queryCommodityDeliverPageApiCount(@Param("userId") Long userId, @Param("type") Integer type, @Param("roleId") int roleId);

    /**
     * 查询 管理人员 分配订单给 设计师/供应商
     * @param pageSize
     * @param limit
     * @param userId
     * @param type
     * @param roleId
     * @return
     */
    List<TransactionCommodityDeliveryRespDto> queryCommodityDeliverPageApi(@Param("pageSize") Integer pageSize, @Param("limit") Integer limit, @Param("userId") Long userId, @Param("type") Integer type, @Param("roleId") Integer roleId);

    /**
     * 查询
     * @param deliveryId
     * @param userId
     * @return
     */
    TransactionCommodityDelivery selectByIdForUpdate(@Param("deliveryId") Long deliveryId, @Param("userId") Long userId);
    /**
     * 根据商品订单ID查询
     * @param userId
     * @param trCommodityId
     * @return
     */
    TransactionCommodityDelivery selectByTransactionCommodityId(@Param("userId") Long userId, @Param("trCommodityId") Long trCommodityId);

    /**
     *  查询 商品订单号
     * @param transactionCommodityId
     * @param typeSupplier
     * @return
     */
    TransactionCommodityDelivery selectByTrCommodityIdType(@Param("transactionCommodityId") Long transactionCommodityId, @Param("typeSupplier") Integer typeSupplier);

    /**
     * 统计未结算订单数量
     * @param userId
     * @param type
     * @return
     */
    Integer countUnSettlement(@Param("userId") Long userId, @Param("type") Integer type);

    /**
     * 查询未结算订单列表
     * @param limit
     * @param pageSize
     * @param userId
     * @param type
     * @return
     */
    List<Map<String, Object>> queryUnSettlement(@Param("limit") Integer limit, @Param("pageSize") Integer pageSize, @Param("userId") Long userId, @Param("type") Integer type);

    /**
     * 统计已结算订单数量
     * @param userId
     * @param type
     * @return
     */
    Integer settled(@Param("userId") Long userId, @Param("type") Integer type);
    /**
     * 查询已结算订单列表
     * @param limit
     * @param pageSize
     * @param userId
     * @param type
     * @return
     */
    List<Map<String, Object>> querySettled(@Param("limit") Integer limit, @Param("pageSize") Integer pageSize, @Param("userId") Long userId, @Param("type") Integer type);


    void updateByUserId(Long userId);

    /**
     * 查询
     * @param recordId
     * @param transactionCommodityId
     * @param type
     * @return
     */
    TransactionCommodityDelivery selectByRecordIdForUpdate(@Param("recordId") Long recordId, @Param("transactionCommodityId") Long transactionCommodityId, @Param("type") Integer type);
}
