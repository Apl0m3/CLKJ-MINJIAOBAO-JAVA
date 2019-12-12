package com.lingkj.project.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.transaction.dto.ApiUploadManuscriptDto;
import com.lingkj.project.api.transaction.dto.ApiChangeCommodityDeliverReqDto;
import com.lingkj.project.transaction.dto.TransactionCommodityDeliveryAndCountRespDto;
import com.lingkj.project.transaction.dto.TransactionCommodityDeliveryRespDto;
import com.lingkj.project.transaction.entity.TransactionCommodityDelivery;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 管理人员 分配订单给  设计师/供应商
 *
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
public interface TransactionCommodityDeliveryService extends IService<TransactionCommodityDelivery> {

    PageUtils queryPage(Map<String, Object> params);

    TransactionCommodityDelivery selectById(Long transactionId, Long id, Integer type);

    TransactionCommodityDelivery selectByRecordIdForUpdate(Long recordId, Long transactionCommodityId, Integer type);

    List<TransactionCommodityDeliveryRespDto> queryWorkSheets(Long userId, Integer type, Integer status, Integer start, Integer end);
    void  queryWorkStatusCount(Long userId, Integer type, TransactionCommodityDeliveryAndCountRespDto trans);

    /**
     * 管理人员 分配给设计师/供应商的订单
     * @param page
     * @param userId
     * @param type
     * @return
     */
    PageUtils queryCommodityDeliverPageApi(Page page, Long userId, Integer type);

    /**
     * 同意或取消 设计师/供应商 订单
     * @param commodityDeliverReqDto
     * @param userId
     */
    void changeCommodityDeliver(ApiChangeCommodityDeliverReqDto commodityDeliverReqDto, Long userId, HttpServletRequest request);

    /**
     * 设计师上传稿件
     * @param uploadManuscriptDto
     * @param userId
     */
    void uploadManuscript(ApiUploadManuscriptDto uploadManuscriptDto, Long userId, HttpServletRequest request);

    /**
     * 统计 分配给设计师/供应商的订单
     * @param userId
     * @return
     */
    Map<String, Integer> queryCommodityDeliverCountApi(Long userId);

    /**
     * 根据商品订单Id得到工作清单
     * @param trCommodityId
     * @return
     */
    TransactionCommodityDelivery selectByTrCommodityId(Long userId,Long trCommodityId);

    TransactionCommodityDelivery selectByTrCommodityIdType(Long transactionCommodityId, Integer type_supplier);

    /**
     * 统计未结算 订单数量
     * @param userId
     * @param type
     * @return
     */
    Integer countUnSettlement(Long userId, Integer type);

    List<Map<String, Object>> queryUnSettlement(Integer limit, Integer pageSize, Long userId, Integer type);

    /**
     * 统计已结算 订单数量
     * @param userId
     * @param type
     * @return
     */
    Integer getSettledCount(Long userId, Integer type);

    List<Map<String, Object>> getSettledList(Integer limit, Integer pageSize, Long userId, Integer type);

    /**
     * 修改结算状态
     * @param userId
     */
    void updateByUserId(Long userId);
}

