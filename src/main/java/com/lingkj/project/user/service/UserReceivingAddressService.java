package com.lingkj.project.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.authentication.paramsvalidation.ReceivingAddressForm;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.user.dto.ReceivingAddressRespDto;
import com.lingkj.project.user.entity.UserReceivingAddress;

import java.util.List;
import java.util.Map;

/**
 * @author chenyongsong
 * @date 2019-06-27 16:01:56
 */
public interface UserReceivingAddressService extends IService<UserReceivingAddress> {
    /**
     * 分页查询收货地址
     *
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询用户收货地址列表
     *
     * @param userId
     * @return
     */
    List<ReceivingAddressRespDto> queryList(Long userId);

    /**
     * 保存收货地址
     *
     * @param form
     * @param userId
     */
    void saveOrUpdate(ReceivingAddressForm form, Long userId);

    /**
     * 修改收货地址状态
     *
     * @param id
     * @param userId
     */
    void updateStatus(Long id, Long userId);

    /**
     * 更改默认收货地址
     *
     * @param id
     * @param userId
     */
    void updateDefault(Long id, Long userId);

    /**
     * 查询收货地址详情
     *
     * @param addressId
     * @param userId
     * @return
     */
    ReceivingAddressRespDto selectRespDtoById(Long addressId, Long userId);

    /**
     * 查询默认收货地址
     *
     * @param userId
     *
     * @return
     */
   ReceivingAddressRespDto queryDefault(Long userId);

    /**
     * 查询收货地址数量
     * @param userId
     * @return
     */
    Integer queryAddressCount(Long userId);
}

