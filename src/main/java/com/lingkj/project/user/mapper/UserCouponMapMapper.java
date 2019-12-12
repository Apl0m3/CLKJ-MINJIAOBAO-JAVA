package com.lingkj.project.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.api.user.dto.ApiCouponDto;
import com.lingkj.project.user.dto.UserCouponAndCouponDto;
import com.lingkj.project.user.entity.UserCouponMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * user transaction log
 *
 * @author chenyongsong
 * @date 2019-09-20 16:05:21
 */
@Mapper
public interface UserCouponMapMapper extends BaseMapper<UserCouponMap> {
    List<UserCouponAndCouponDto> getUserCoupon(@Param("userId") Long userId, @Param("date") Date date, @Param("start")Integer start, @Param("end") Integer end);
 Integer queryUserCouponCount(@Param("userId") Long userId, @Param("date") Date date);

    /**
     * 查询可用优惠劵
     * @param userId
     * @param commodityId
     * @param date
     * @param couponIds
     * @return
     */
    List<ApiCouponDto> queryUseCoupon(@Param("userId") Long userId, @Param("commodityId") Long commodityId, @Param("date") Date date, @Param("couponIds") List<Long> couponIds);
}
