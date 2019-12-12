package com.lingkj.project.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.transaction.entity.TransactionCommodityStatusLog;

import java.util.Map;

/**
 * 订单status 改变记录表
 *
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
public interface TransactionCommodityStatusLogService extends IService<TransactionCommodityStatusLog> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 商品订单改变记录日志
     * @param userId
     * @param transactionCommodityId 商品订单id
     * @param recordId 订单id
     *   @param type  用户类型：0-用户  1-平台  2-设计师  3-供应商
     *                   @param status 商品订单主状态
     *                                   @param substate 商品订单子状态
     * @return
     */
    void saveStatusLog(Long userId,Long transactionCommodityId,Long recordId,Integer type, Integer status, Integer substate );
}

