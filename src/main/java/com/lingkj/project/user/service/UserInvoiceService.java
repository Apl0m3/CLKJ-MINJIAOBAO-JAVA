package com.lingkj.project.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.user.dto.ApiUserInvoiceDto;
import com.lingkj.project.user.entity.UserInvoice;

import java.util.Map;

/**
 * 订单 用户发票信息
 *
 * @author chenyongsong
 * @date 2019-09-30 16:44:13
 */
public interface UserInvoiceService extends IService<UserInvoice> {

    PageUtils queryPage(Map<String, Object> params);

    ApiUserInvoiceDto selectByUserId(Long userId);

}

