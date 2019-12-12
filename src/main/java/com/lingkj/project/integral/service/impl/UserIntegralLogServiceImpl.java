package com.lingkj.project.integral.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.DateUtils;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.integral.dto.IntegralUserLogRespDto;
import com.lingkj.project.integral.entity.IntegralUser;
import com.lingkj.project.integral.entity.IntegralUserLog;
import com.lingkj.project.integral.mapper.IntegralUserLogMapper;
import com.lingkj.project.integral.service.UserIntegralLogService;
import com.lingkj.project.integral.service.UserIntegralService;
import com.lingkj.project.user.dto.SaveIntegerUserLogDto;
import com.lingkj.project.user.entity.InvitationUserIntegralLog;
import com.lingkj.project.user.service.InvitationUserIntegralLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 */
@Service
public class UserIntegralLogServiceImpl extends ServiceImpl<IntegralUserLogMapper, IntegralUserLog> implements UserIntegralLogService {

    @Autowired
    @Lazy
    private UserIntegralService userIntegralService;
    @Autowired
    private InvitationUserIntegralLogService invitationUserIntegralLogService;

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long userId) {
        params.put("sidx", "create_time");
        params.put("order", "DESC");
        QueryWrapper<IntegralUserLog> entityWrapper = new QueryWrapper<>();
        if (userId != null) {
            entityWrapper.eq("user_id", userId);
        }
        entityWrapper.orderByDesc("create_time");
        IPage<IntegralUserLog> page = this.page(
                new Query<IntegralUserLog>(params).getPage(),
                entityWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryIntegralLog(com.lingkj.common.bean.entity.Page page, Long userId) {
        Integer totalCount = this.baseMapper.queryIntegralLogCount(userId);
        List<IntegralUserLogRespDto> list = this.baseMapper.queryIntegralLogPage(page.getPageSize(), page.getLimit(), userId);
        return new PageUtils(list, totalCount, page.getPageSize(), page.getPage());
    }

    /**
     * 封装添加用户积分
     *
     * @param saveIntegerUserLogDto
     */
    @Transactional
    @Override
    public void saveIntegralUserLog(SaveIntegerUserLogDto saveIntegerUserLogDto) {
        Long userId = saveIntegerUserLogDto.getUserId();
        Integer integral = saveIntegerUserLogDto.getIntegral();
        String remark = saveIntegerUserLogDto.getRemark();
        Long integralUserId = saveIntegerUserLogDto.getIntegralUserId();
        String transactionId = saveIntegerUserLogDto.getTransactionId();
        //查询用户积分
        IntegralUser integralUser = userIntegralService.selectByUserIdForUpdate(userId);

        IntegralUserLog integralUserLog = new IntegralUserLog();
        integralUserLog.setPreviousValue(integralUser.getIntegral());
        integralUserLog.setChangeValue(integral);
        integralUserLog.setType(1);
        Integer currentValue;
        currentValue = integralUserLog.getPreviousValue() + integralUserLog.getChangeValue();
        integralUserLog.setRemark(remark);
        integralUserLog.setTransactionId(transactionId);

        integralUserLog.setCurrentValue(currentValue);
        integralUserLog.setCreateTime(DateUtils.current());
        integralUserLog.setUserId(userId);
        //添加用户积分记录
        this.save(integralUserLog);
        //修改用户积分
        integralUser.setIntegral(integralUserLog.getCurrentValue());

        if (integralUserId != null && integralUserId != 0) {//添加上级获得积分记录
            InvitationUserIntegralLog invitationUserIntegralLog = new InvitationUserIntegralLog();
            invitationUserIntegralLog.setUserId(userId);
            invitationUserIntegralLog.setInvitedUserId(integralUserId);
            invitationUserIntegralLog.setCreateTime(new Date());
            invitationUserIntegralLog.setIntegral(integral);
            invitationUserIntegralLog.setTransactionId(transactionId);
            invitationUserIntegralLogService.save(invitationUserIntegralLog);

        }
        userIntegralService.updateById(integralUser);


    }

}
