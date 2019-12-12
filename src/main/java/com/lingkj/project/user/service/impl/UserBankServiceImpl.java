package com.lingkj.project.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.project.user.entity.UserBank;
import com.lingkj.project.user.mapper.UserBankMapper;
import com.lingkj.project.user.service.UserBankService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;


/**
 *
 *
 * @author chenyongsong
 * @date 2019-10-14 17:24:27
 */
@Service
public class UserBankServiceImpl extends ServiceImpl<UserBankMapper, UserBank> implements UserBankService {
    @Autowired
    private MessageUtils messageUtils;
    public  final  static String bankVerification = "^[0-9]{1,20}$";

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserBank> page = this.page(
                new Query<UserBank>(params).getPage(),
                new QueryWrapper<UserBank>()
        );

        return new PageUtils(page);
    }

    @Override
    public UserBank getUserBank(Long userId, Long applicationId,Integer type) {
        return  this.baseMapper.queryUseBank(userId,applicationId,type);
    }

    @Override
    public boolean saveAndUpdate(UserBank userBank, Long userId, Long applicationId, HttpServletRequest request) {
        boolean  bo=true;
//        if(StringUtils.isBlank(userBank.getBankAccount())||StringUtils.isBlank(userBank.getBankUserName())||StringUtils.isBlank(userBank.getBankName())){
//            bo=false;
//            return bo;
//        }
        if(StringUtils.isNotBlank(userBank.getBankAccount())){
            if (!userBank.getBankAccount().matches(bankVerification)) {
                throw new RRException(messageUtils.getMessage("api.user.bank.bankAccount.verification", request), 400);
            }
            if(StringUtils.isBlank(userBank.getBankUserName())||StringUtils.isBlank(userBank.getBankName())){
                bo=false;
                return bo;
            }
        }else if(StringUtils.isNotBlank(userBank.getBankName())) {
            if(StringUtils.isBlank(userBank.getBankUserName())||StringUtils.isBlank(userBank.getBankAccount())){
                bo=false;
                return bo;
            }
        }else if(StringUtils.isNotBlank(userBank.getBankUserName())){
            if(StringUtils.isBlank(userBank.getBankName())||StringUtils.isBlank(userBank.getBankAccount())){
                bo=false;
                return bo;
            }
        }
        if(userBank.getId()!=null){
            this.updateById(userBank);
        }else {
            if(StringUtils.isNotBlank(userBank.getBankName())||StringUtils.isNotBlank(userBank.getBankUserName())||StringUtils.isNotBlank(userBank.getBankAccount())){
                userBank.setApplicationId(applicationId);
                userBank.setUserId(userId);
                userBank.setCreateTime(new Date());
                this.save(userBank);
            }
        }
        return bo;
    }

    @Override
    public UserBank selectByUserId(Long userId) {
        return this.baseMapper.selectByUserId(userId);
    }

    @Override
    public UserBank selectByUserIdAndApplicationId(Long userId, Long applicationId) {
        QueryWrapper<UserBank> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("application_id",applicationId);
        return this.getOne(wrapper);
    }

}
