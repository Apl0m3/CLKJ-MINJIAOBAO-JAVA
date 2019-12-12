package com.lingkj.project.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.project.transaction.service.TransactionCommodityDeliveryService;
import com.lingkj.project.user.dto.SettlementAmountDto;
import com.lingkj.project.user.dto.UserAccountDto;
import com.lingkj.project.user.entity.UserAccount;
import com.lingkj.project.user.entity.UserAccountLog;
import com.lingkj.project.user.mapper.UserAccountMapper;
import com.lingkj.project.user.service.UserAccountLogService;
import com.lingkj.project.user.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 用户金额
 *
 * @author chenyongsong
 * @date 2019-09-24 11:37:31
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {
    @Autowired
    private UserAccountLogService userAccountLogService;
    @Autowired
    private TransactionCommodityDeliveryService commodityDeliveryService;
    @Autowired
    private MessageUtils messageUtils;


    @Override
    public UserAccount getUserAccountOne(Long userId) {
        QueryWrapper<UserAccount> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return this.getOne(wrapper);
    }

    public UserAccount saveUserAccount(Long userId) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(userId);
        userAccount.setCreateTime(new Date());
        userAccount.setAmount(BigDecimal.ZERO);
        userAccount.setStatus(0);
        userAccount.setTotalAmount(BigDecimal.ZERO);
        this.save(userAccount);
        return userAccount;
    }

    /**
     * 修改用户钱包 记录
     */
    @Transactional
    @Override
    public void updateUserAccount(Long userId, Integer type, BigDecimal amount, String remark, Long recordId, Long transactionCommodityId) {
        BigDecimal changeAmount = amount;
        UserAccount userAccount = this.baseMapper.selectByUserIdForUpdate(userId);
        if (userAccount == null) {
            userAccount = this.saveUserAccount(userId);
        }
        if (type.equals(UserAccountLog.type_out)) {
            changeAmount = BigDecimal.ZERO.multiply(amount);
        } else {
            this.baseMapper.updateAccountTotalAmount(userAccount.getId(), amount);
        }
        this.baseMapper.updateAccount(userAccount.getId(), changeAmount);

        UserAccountLog log = new UserAccountLog();
        log.setUserId(userId);
        log.setType(type);
        log.setRemark(remark);
        //以前的值
        log.setPreviousValue(userAccount.getAmount());
        //改变的值
        log.setChangeValue(amount);

        log.setCurrentValue(amount.add(userAccount.getAmount()));
        log.setRecordId(recordId);
        log.setCreateTime(new Date());
        log.setTransactionCommodityId(transactionCommodityId);
        this.userAccountLogService.save(log);
    }

    @Override
    public PageUtils queryPage(Page page, Integer type, String key) {
        Integer totalCount = this.baseMapper.selectCountByCondition(type,key);
        List<UserAccountDto> list = this.baseMapper.selectListByCondition(page.getLimit(), page.getPageSize(), type,key);
        for (UserAccountDto account : list) {
            Integer num = commodityDeliveryService.countUnSettlement(account.getUserId(), type);
            account.setOrderNum(num);
        }
        return new PageUtils(list, totalCount, page.getPageSize(), page.getPage());
    }

    @Transactional
    @Override
    public void settleAccounts(SettlementAmountDto settlementAmountDto, Long sysUserId, HttpServletRequest request) {
        UserAccount userAccount = this.baseMapper.selectByIdForUpdate(settlementAmountDto.getId());
        if (userAccount == null) {
            throw new RRException(messageUtils.getMessage("api.user.apply.agent.url", request));
        }
        BigDecimal changeAmount = BigDecimal.ZERO.subtract(settlementAmountDto.getAmount());
        this.baseMapper.updateAccount(userAccount.getId(), changeAmount);
        this.baseMapper.updateAccountSettlementAmount(userAccount.getId(), settlementAmountDto.getAmount());
        UserAccountLog log = new UserAccountLog();
        log.setUserId(userAccount.getUserId());
        log.setType(UserAccountLog.type_out);
        log.setRemark(messageUtils.getMessage("manage.platform.settlement", request));
        //以前的值
        log.setPreviousValue(userAccount.getAmount());
        //改变的值
        log.setChangeValue(settlementAmountDto.getAmount());

        log.setCurrentValue(changeAmount.add(userAccount.getAmount()));
        log.setCreateTime(new Date());
        this.userAccountLogService.save(log);

        commodityDeliveryService.updateByUserId(userAccount.getUserId());


    }

    @Override
    public PageUtils queryDeliveryPage(Page page, Integer type, Long userId) {
        Integer totalCount = this.commodityDeliveryService.countUnSettlement(userId, type);
        List<Map<String, Object>> list = this.commodityDeliveryService.queryUnSettlement(page.getLimit(), page.getPageSize(), userId, type);
        return new PageUtils(list, totalCount, page.getPageSize(), page.getPage());
    }

    @Override
    public PageUtils querySettledDeliveryPage(Page page, Integer type, Long userId) {
        Integer totalCount = this.commodityDeliveryService.getSettledCount(userId, type);
        List<Map<String, Object>> list = this.commodityDeliveryService.getSettledList(page.getLimit(), page.getPageSize(), userId, type);
        return new PageUtils(list, totalCount, page.getPageSize(), page.getPage());
    }

}
