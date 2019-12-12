package com.lingkj.project.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.user.dto.UserAccountDto;
import com.lingkj.project.user.entity.UserCommission;
import com.lingkj.project.user.mapper.UserCommissionMapper;
import com.lingkj.project.user.service.UserCommissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设计师或者供应商佣金
 *
 * @author chenyongsong
 * @date 2019-10-25 14:16:42
 */
@Service
public class UserCommissionServiceImpl extends ServiceImpl<UserCommissionMapper, UserCommission> implements UserCommissionService {


    @Override
    public UserCommission selectByTransactionCommodityId(Long transactionCommodityId, Long designUserId) {
        return this.baseMapper.selectByTransactionCommodityId(transactionCommodityId, designUserId);
    }


}
