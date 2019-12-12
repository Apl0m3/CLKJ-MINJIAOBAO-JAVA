package com.lingkj.project.integral.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.integral.entity.IntegralUser;
import com.lingkj.project.integral.entity.IntegralUserLog;
import com.lingkj.project.integral.mapper.IntegralUserMapper;
import com.lingkj.project.integral.service.UserIntegralLogService;
import com.lingkj.project.integral.service.UserIntegralService;
import com.lingkj.project.user.entity.UserAccountLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * @author Administrator
 */
@Service
public class UserIntegralServiceImpl extends ServiceImpl<IntegralUserMapper, IntegralUser> implements UserIntegralService {
    @Autowired
    @Lazy
    private UserIntegralLogService userIntegralLogService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        params.put("sidx", "create_time");
        params.put("order", "DESC");
        IPage<IntegralUser> page = this.page(
                new Query<IntegralUser>(params).getPage(),
                new QueryWrapper<IntegralUser>()
        );

        return new PageUtils(page);
    }

    @Override
    public IntegralUser selectByUserId(Long id) {
        IntegralUser integralUser = baseMapper.selectByUserId(id);
        if (integralUser == null) {
            integralUser = new IntegralUser();
            integralUser.setCreateTime(new Date());
            integralUser.setUserId(id);
            integralUser.setIntegral(0);
            integralUser.setStatus(0);
            this.save(integralUser);
        }
        if (integralUser.getStatus() == 1) {
            throw new RRException("用户积分已被禁用,请联系客服", 20011);
        }
        return integralUser;
    }

    @Override
    public void updateIntegral(Long id, Integer result, Date current) {
        baseMapper.updateIntegral(id, result, current);
    }

    @Override
    public IntegralUser selectByUserIdForUpdate(Long userId) {
        return baseMapper.selectByUserIdForUpdate(userId);
    }

    @Override
    @Transactional
    public void updateUserIntegral(Long userId, Integer type, Integer amount, String remark, Long recordId, Long transactionCommodityId, String transactionId) {
        Integer changeAmount = amount;
        IntegralUser integralUser = this.baseMapper.selectByUserIdForUpdate(userId);
        if (integralUser == null) {
            integralUser = new IntegralUser();
            integralUser.setCreateTime(new Date());
            integralUser.setUserId(userId);
            integralUser.setIntegral(0);
            integralUser.setStatus(0);
            this.save(integralUser);
        }
        if (type.equals(UserAccountLog.type_out)) {
            changeAmount = -amount;
        }
        this.baseMapper.updateIntegral(integralUser.getId(), changeAmount, new Date());
        IntegralUserLog log = new IntegralUserLog();
        log.setUserId(userId);
        log.setType(type);
        log.setRemark(remark);
        //以前的值
        log.setPreviousValue(integralUser.getIntegral());
        //改变的值
        log.setChangeValue(amount);

        log.setCurrentValue(changeAmount + integralUser.getIntegral());
        log.setRecordId(recordId);
        log.setCreateTime(new Date());
        log.setTransactionCommodityId(transactionCommodityId);
        this.userIntegralLogService.save(log);


    }


}
