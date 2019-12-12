package com.lingkj.project.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.project.integral.entity.IntegralUser;
import com.lingkj.project.integral.entity.IntegralUserLog;
import com.lingkj.project.integral.service.UserIntegralLogService;
import com.lingkj.project.integral.service.UserIntegralService;
import com.lingkj.project.operation.entity.OperateRules;
import com.lingkj.project.operation.service.OperateRulesService;
import com.lingkj.project.user.entity.UserShareLog;
import com.lingkj.project.user.mapper.UserShareLogMapper;
import com.lingkj.project.user.service.AdminUserService;
import com.lingkj.project.user.service.UserShareLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;


/**
 * 分享网站记录
 *
 * @author chenyongsong
 * @date 2019-09-23 16:03:51
 */
@Service
public class UserShareLogServiceImpl extends ServiceImpl<UserShareLogMapper, UserShareLog> implements UserShareLogService {

    @Autowired
    private OperateRulesService operateRulesService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private UserIntegralLogService userIntegralLogService;
    @Autowired
    private UserIntegralService userIntegralService;
    @Autowired
    private MessageUtils messageUtils;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserShareLog> page = this.page(
                new Query<UserShareLog>(params).getPage(),
                new QueryWrapper<UserShareLog>()
        );

        return new PageUtils(page);
    }

    @Override
    public UserShareLog selectByIdAndUrl(Long userId, String url) {
        QueryWrapper<UserShareLog>  wrapper=new QueryWrapper<UserShareLog>();
        wrapper.eq("user_id",userId);
        wrapper.eq("url",url);
        return  this.getOne(wrapper);
    }

    @Transactional
    @Override
    public void userShare(Long userId, String url, HttpServletRequest request) {
        UserShareLog userShareLog = selectByIdAndUrl(userId, url);
        OperateRules operateRules = operateRulesService.selectOneByType(2,1);
        IntegralUser integralUser = userIntegralService.selectByUserIdForUpdate(userId);
        Integer previousValue=integralUser.getIntegral();
        Integer  changeValue=operateRules.getResult().intValue();
        Integer currentValue=integralUser.getIntegral()+changeValue;
        if(userShareLog==null){
            UserShareLog shareLog =new UserShareLog();
            shareLog.setCreateTime(new Date());
            shareLog.setUserId(userId);
            shareLog.setUrl(url);
            this.save(shareLog);
            userIntegralService.updateIntegral(integralUser.getId(),changeValue,new Date());
            IntegralUserLog integralUserLog=new IntegralUserLog();
            integralUserLog.setCreateTime(new Date());
            integralUserLog.setUserId(userId);
            integralUserLog.setType(1);
            integralUserLog.setPreviousValue(previousValue);
            integralUserLog.setChangeValue(changeValue);
            integralUserLog.setCurrentValue(currentValue);
            integralUserLog.setRemark(messageUtils.getMessage("api.user.share", request));
            userIntegralLogService.save(integralUserLog);
        }
    }


}
