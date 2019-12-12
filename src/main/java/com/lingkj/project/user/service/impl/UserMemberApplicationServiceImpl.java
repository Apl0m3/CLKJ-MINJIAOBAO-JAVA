package com.lingkj.project.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.DateUtils;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.project.message.entity.Message;
import com.lingkj.project.message.service.MessageService;
import com.lingkj.project.sys.entity.SysUserEntity;
import com.lingkj.project.sys.service.SysUserService;
import com.lingkj.project.user.dto.CommissionDto;
import com.lingkj.project.user.dto.DiscountDto;
import com.lingkj.project.user.dto.UserRespDto;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.entity.UserAgentRate;
import com.lingkj.project.user.entity.UserMemberApplication;
import com.lingkj.project.user.mapper.UserMemberApplicationMapper;
import com.lingkj.project.user.service.AdminUserService;
import com.lingkj.project.user.service.UserAgentRateService;
import com.lingkj.project.user.service.UserMemberApplicationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.lingkj.common.utils.ShiroUtils.getSysUserId;

/**
 * 用户分销会员申请
 *
 * @author chenyongsong
 * @date 2019-08-16 09:31:05
 */
@Service
public class UserMemberApplicationServiceImpl extends ServiceImpl<UserMemberApplicationMapper, UserMemberApplication> implements UserMemberApplicationService {
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserAgentRateService userAgentRateService;
    @Autowired
    private MessageUtils messageUtils;
    @Autowired
    private MessageService messageService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<UserMemberApplication> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank((String) params.get("userId"))){
            queryWrapper.eq("user_id",params.get("userId"));
        }
        queryWrapper.orderByDesc("create_time");
        IPage<UserMemberApplication> page = this.page(new Query<UserMemberApplication>(params).getPage(),
                queryWrapper.orderByDesc("create_time"));
        page.getRecords().forEach(userMemberApplication -> {
            //行业类型
            Integer type = userMemberApplication.getType();
            Map<String, String> operateType = baseMapper.operateType(type);
            if(operateType != null && operateType.size()>0){
                String name = operateType.get("name");
                userMemberApplication.setTypeName(name);
            }
            User user = adminUserService.getById(userMemberApplication.getUserId());
            userMemberApplication.setName(user.getName());
            userMemberApplication.setMail(user.getUserName());
            if (userMemberApplication.getApplicationBy() != null) {
                SysUserEntity sysUserEntity = sysUserService.getById(userMemberApplication.getApplicationBy());
                userMemberApplication.setApplicationName(sysUserEntity.getUsername());
            }
        });

        return new PageUtils(page);
    }

    @Override
    public Map<String, Object> selectByUserId(Long id) {
        UserMemberApplication userMemberApplication = baseMapper.selectById(id);
        UserRespDto userRespDto = new UserRespDto();
        //行业类型
        Integer type = userMemberApplication.getType();
        Map<String, String> operateType = baseMapper.operateType(type);
        if(operateType != null && operateType.size()>0){
            String name = operateType.get("name");
            userMemberApplication.setTypeName(name);
        }
        if(userMemberApplication.getUserId() != null ){
            userRespDto = adminUserService.queryPersonInfo(userMemberApplication.getUserId());
        }
        if (userMemberApplication.getApplicationBy() != null) {
            SysUserEntity sysUserEntity = sysUserService.getById(userMemberApplication.getApplicationBy());
            userMemberApplication.setApplicationName(sysUserEntity.getUsername());
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("userMemberApplication",userMemberApplication);
        map.put("userRespDto",userRespDto);
        return map;
    }

    @Override
    public void saveOrUpdateMemberApplication(UserMemberApplication userMemberApplication) {
        User user = adminUserService.getById(userMemberApplication.getUserId());

        Long id = userMemberApplication.getId();
        userMemberApplication.setCreateTime(new Date());
        userMemberApplication.setStatus(0);
        if (id == null) {
            this.baseMapper.insert(userMemberApplication);
        } else {
            this.baseMapper.updateById(userMemberApplication);
        }
    }

    @Override
    public void verify(UserMemberApplication userMemberApplication, HttpServletRequest request) {
        userMemberApplication.setApplicationTime(new Date());
        Message message = new Message();
        message.setCreateTime(DateUtils.current());
        message.setTitle(messageUtils.getMessage("manage.user.apply.agent.feedback", request));
        message.setCreateSysUserId(getSysUserId());
        message.setUserId(userMemberApplication.getUserId());
        if (userMemberApplication.getStatus() == 1) {
            message.setContent(messageUtils.getMessage("manage.user.apply.agent.success", request));
            User user = this.adminUserService.getById(userMemberApplication.getUserId());
            user.setUserRoleId(User.member_role_agent);
            this.adminUserService.updateById(user);
        }else {
            // 审核未通过
            message.setContent(messageUtils.getMessage("manage.user.apply.agent.fail", request) + userMemberApplication.getReason());

        }
        this.updateById(userMemberApplication);
        messageService.save(message);
    }

    @Override
    public List<DiscountDto> getTypeList(Long userId) {
        //查询所有商品分类
        List<DiscountDto> allTypeList = this.baseMapper.getTypeList();
        //查询代理商已设置折扣率的商品类型集合
        List<DiscountDto> ownTypeList = this.baseMapper.getOwnDiscountList(userId);

        for (int i = 0; i < allTypeList.size(); i++) {
            for (int j = 0; j < ownTypeList.size() ; j++) {
                Long id = ownTypeList.get(j).getId();
                if (ownTypeList.get(j).getCommodityTypeId().equals(allTypeList.get(i).getCommodityTypeId())){
                    allTypeList.get(i).setDiscount(ownTypeList.get(j).getDiscount());
                    allTypeList.get(i).setId(id);
                }
            }
        }

        return allTypeList;
    }

    @Override
    public void saveDiscount(UserMemberApplication userMemberApplication) {
        int count = -1;
        if(userMemberApplication.getUserId()!= null){
            Long userId = userMemberApplication.getUserId();
            count = this.baseMapper.selectCountById(userId);

        }
        List<DiscountDto> discountDtoList = userMemberApplication.getDiscountDtoList();
        List<UserAgentRate> userAgentRates = new ArrayList<>();
        List<UserAgentRate> userAgentRatesUp = new ArrayList<>();
        for (DiscountDto dto:discountDtoList){
            if(dto.getId()!=null){
                UserAgentRate rate= new UserAgentRate();
                rate.setId(dto.getId());
                rate.setUserId(dto.getUserId());
                rate.setRate(dto.getDiscount());
                rate.setCommodityTypeId(dto.getCommodityTypeId());
                rate.setUpdateBy(getSysUserId());
                rate.setUpdateTime(new Date());
                userAgentRatesUp.add(rate);
            }else {
                UserAgentRate rate= new UserAgentRate();
                rate.setUserId(dto.getUserId());
                rate.setRate(dto.getDiscount());
                rate.setCommodityTypeId(dto.getCommodityTypeId());
                rate.setCreateBy(getSysUserId());
                rate.setCreateTime(new Date());
                userAgentRates.add(rate);
            }

        }
        if(count > 0){
            userAgentRateService.updateBatchById(userAgentRatesUp);
            if(userAgentRates!=null&&userAgentRates.size()>0){
                userAgentRateService.saveBatch(userAgentRates);
            }
        }else {
            if(userAgentRates!=null&&userAgentRates.size()>0){
                userAgentRateService.saveBatch(userAgentRates);
            }
        }

    }

}
