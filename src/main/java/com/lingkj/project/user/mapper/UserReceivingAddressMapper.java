package com.lingkj.project.user.mapper;

import com.lingkj.project.user.dto.ReceivingAddressRespDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.user.entity.UserReceivingAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenyongsong
 *
 * @date 2019-06-27 16:01:56
 */
@Mapper
public interface UserReceivingAddressMapper extends BaseMapper<UserReceivingAddress> {
    /**
     * 根据userId 修改用户默认地址
     *
     * @param userId
     */
    void updateDefaultByUserId(@Param("userId") Long userId);

    /**
     * 查询用户 收货地址
     *
     * @param userId
     * @return
     */
    List<ReceivingAddressRespDto> queryAddressList(Long userId);
    /**
     * 查询用户 收货地址
     *
     * @param userId
     * @param addressId
     * @return
     */
    ReceivingAddressRespDto queryRespDtoById(@Param("userId")Long userId, @Param("addressId")Long addressId);

    /**
     * 查询用户默认收货地址
     * @param userId
     * @return
     */
    ReceivingAddressRespDto queryDefault(Long userId);
}
