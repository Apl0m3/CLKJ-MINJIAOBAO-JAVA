package com.lingkj.project.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.user.dto.CommissionDto;
import com.lingkj.project.user.dto.SupplierCommissionDto;
import com.lingkj.project.user.dto.UserAndSupplierDto;
import com.lingkj.project.user.entity.UserSupplierApplication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 用户供应商 申请  表
 *
 * @author chenyongsong
 * @date 2019-09-12 09:17:38
 */

public interface UserSupplierApplicationService extends IService<UserSupplierApplication> {

    PageUtils queryPage(Map<String, Object> params);

    void approval(UserSupplierApplication userSupplierApplication,HttpServletRequest request);

    /**
     * 没带佣金Commission
     * @param id 申请商id
     * @return CommissionDto实体
     */
    List<CommissionDto> getCommissionDtoList(long id);
    /**
     * 带佣金Commission
     * @param id 申请商id
     * @return CommissionDto实体 包含佣金
     */
    List<CommissionDto> getCommissionList(long id);

    UserSupplierApplication selectByUserId(Long userId);

    Integer getUserSupplierApplicationCount(Long userId);


    Map<String,Object> queryInfo(Long id);
    void  saveSupplier(UserAndSupplierDto userAndSupplierDto, Long userId, HttpServletRequest request);

}

