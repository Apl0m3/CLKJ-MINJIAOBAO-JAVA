package com.lingkj.project.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.user.entity.UserCommission;

/**
 * 设计师或者供应商佣金
 *
 * @author chenyongsong
 * @date 2019-10-25 14:16:42
 */
public interface UserCommissionService extends IService<UserCommission> {
    /**
     * 查询佣金
     *
     * @param transactionCommodityId
     * @param designUserId
     * @return
     */
    UserCommission selectByTransactionCommodityId(Long transactionCommodityId, Long designUserId);


}

