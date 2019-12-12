package com.lingkj.project.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.api.user.dto.ApiUserInvoiceDto;
import com.lingkj.project.user.entity.UserInvoice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 订单 用户发票信息
 *
 * @author chenyongsong
 * @date 2019-09-30 16:44:13
 */
@Mapper
public interface UserInvoiceMapper extends BaseMapper<UserInvoice> {
    /**
     * 查询用户 发票信息
     *
     * @param userId
     * @return
     */
    ApiUserInvoiceDto selectByUserId(@Param("userId") Long userId);
}
