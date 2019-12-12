package com.lingkj.project.integral.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.integral.entity.IntegralUser;

import java.util.Date;
import java.util.Map;

/**
 * 用户积分
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:25
 */
public interface UserIntegralService extends IService<IntegralUser> {

    PageUtils queryPage(Map<String, Object> params);
    /**
     * 查询用户积分
     *
     * @param userId
     * @return
     */
    IntegralUser selectByUserId(Long userId);

    void updateIntegral(Long id, Integer result, Date current);
    /**
     * 查询用户积分 加锁
     *
     * @param userId
     * @return
     */
    IntegralUser selectByUserIdForUpdate(Long userId);

    /**
     * 修改用户 钱包金额
     *  @param userId
     * @param type
     * @param amount
     * @param remark
     * @param recordId
     * @param transactionCommodityId
     * @param transactionId
     */
    void updateUserIntegral(Long userId, Integer type, Integer amount, String remark, Long recordId, Long transactionCommodityId, String transactionId);
}

