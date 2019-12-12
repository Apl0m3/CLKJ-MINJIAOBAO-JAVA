package com.lingkj.project.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.common.utils.R;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.project.user.dto.UserAndAgentApplicationDto;
import com.lingkj.project.user.entity.UserAgentApplication;
import com.lingkj.project.user.mapper.UserAgentApplicationMapper;
import com.lingkj.project.user.service.AdminUserService;
import com.lingkj.project.user.service.UserAgentApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;


/**
 * 用户 代理商申请
 *
 * @author chenyongsong
 * @date 2019-10-08 16:23:51
 */
@Service
public class UserAgentApplicationServiceImpl extends ServiceImpl<UserAgentApplicationMapper, UserAgentApplication> implements UserAgentApplicationService {
 @Autowired
 private AdminUserService adminUserService;
    @Autowired
    private MessageUtils messageUtils;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserAgentApplication> page = this.page(
                new Query<UserAgentApplication>(params).getPage(),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public UserAgentApplication getUserAgentApplicationOne(Long userId) {
        QueryWrapper<UserAgentApplication> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        return this.getOne(wrapper);
    }

    @Override
    public Integer getUserAgentApplicationCount(Long userId) {
        QueryWrapper<UserAgentApplication> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        return this.count(wrapper);
    }

    @Transactional
    @Override
    public void saveAgent(UserAndAgentApplicationDto userAndAgentApplicationDto, Long userId, HttpServletRequest request) {
        UserAgentApplication userAgentApplication = userAndAgentApplicationDto.getUserAgentApplication();
        if(userAgentApplication.getId()==null){
            userAgentApplication.setCreateTime(new Date());
            userAgentApplication.setStatus(0);
            userAgentApplication.setUserId(userId);
            this.save(userAgentApplication);
        }else if(userAgentApplication.getStatus()!=1){
            userAgentApplication.setStatus(0);
            this.updateById(userAgentApplication);
        }else {
            throw new RRException(messageUtils.getMessage("api.user.apply", request),400);
        }
        adminUserService.updateUser(userId,userAndAgentApplicationDto.getUserRespDto(),request);
    }

}
