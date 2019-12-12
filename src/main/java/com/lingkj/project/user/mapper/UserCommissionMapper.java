package com.lingkj.project.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.user.entity.UserCommission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 设计师或者供应商佣金
 *
 * @author chenyongsong
 * @date 2019-10-25 14:16:42
 */
@Mapper
public interface UserCommissionMapper extends BaseMapper<UserCommission> {
    /**
     * 查询订单商品 需要支付的设计师费用
     *
     * @param transactionCommodityId
     * @param designUserId
     * @return
     */
    UserCommission selectByTransactionCommodityId(@Param("transactionCommodityId") Long transactionCommodityId, @Param("designUserId") Long designUserId);
}
