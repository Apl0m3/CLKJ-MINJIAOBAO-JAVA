package com.lingkj.project.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;

import com.lingkj.project.user.dto.SettlementAmountDto;
import com.lingkj.project.user.entity.UserAccount;


import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * 用户金额
 *
 * @author chenyongsong
 * @date 2019-09-24 11:37:31
 */
public interface UserAccountService extends IService<UserAccount> {



    UserAccount getUserAccountOne(Long userId);

    /**
     * 修改用户 钱包金额
     *
     * @param userId
     * @param type
     * @param amount
     * @param remark
     * @param recordId
     * @param transactionCommodityId
     */
    void updateUserAccount(Long userId, Integer type, BigDecimal amount, String remark, Long recordId, Long transactionCommodityId);

    /**
     * 后端接口  返回佣金
     * @param page
     * @param type
     * @param key
     * @return
     */
    PageUtils queryPage(Page page, Integer type, String key);

    /**
     * 后端接口  结算佣金
     * @param settlementAmountDto
     * @param sysUserId
     */
    void settleAccounts(SettlementAmountDto settlementAmountDto, Long sysUserId, HttpServletRequest request);

    PageUtils queryDeliveryPage(Page page, Integer type, Long userId);

    PageUtils querySettledDeliveryPage(Page page, Integer type, Long userId);
}

