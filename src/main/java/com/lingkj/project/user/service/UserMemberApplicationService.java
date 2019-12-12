package com.lingkj.project.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.user.dto.DiscountDto;
import com.lingkj.project.user.entity.UserMemberApplication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 用户分销会员申请
 *
 * @author chenyongsong
 * @date 2019-08-16 09:31:05
 */
public interface UserMemberApplicationService extends IService<UserMemberApplication> {

    PageUtils queryPage(Map<String, Object> params);

    Map<String, Object> selectByUserId(Long id);

    void saveOrUpdateMemberApplication(UserMemberApplication userMemberApplication);

    void verify(UserMemberApplication userMemberApplication, HttpServletRequest request);
    List<DiscountDto> getTypeList(Long userId);


    /**
     * 保存折扣率
     * @author XXX <XXX@163.com>
     * @date 2019/11/21 14:49
     * @param
     * @return
     */
    void saveDiscount(UserMemberApplication userMemberApplication);
}

