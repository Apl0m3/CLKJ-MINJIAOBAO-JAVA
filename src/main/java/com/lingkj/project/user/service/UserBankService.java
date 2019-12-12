package com.lingkj.project.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.entity.UserBank;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 *
 * @author chenyongsong
 * @date 2019-10-14 17:24:27
 */
public interface UserBankService extends IService<UserBank> {

    PageUtils queryPage(Map<String, Object> params);
    UserBank getUserBank(Long userId,Long applicationId,Integer type);


    /**
     * 申请设计师，供应商时，银行账户的保存与修改
     * @param userBank
     * @param userId
     * @param applicationId
     * @return
     */
    boolean saveAndUpdate(UserBank userBank, Long userId, Long applicationId, HttpServletRequest request);

    /**
     * 查询用户的银行
     * @param userId
     * @return
     */
    UserBank selectByUserId(Long userId);

    UserBank selectByUserIdAndApplicationId(Long userId,Long applicationId);
}

