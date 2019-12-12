package com.lingkj.project.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.user.dto.ApiCouponDto;
import com.lingkj.project.api.user.dto.ApiUserCouponMapReqDto;
import com.lingkj.project.user.dto.UserCouponAndCouponDto;
import com.lingkj.project.user.entity.UserCouponMap;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * user transaction log
 *
 * @author chenyongsong
 * @date 2019-09-20 16:05:21
 */
public interface UserCouponMapService extends IService<UserCouponMap> {

    PageUtils queryPage(Map<String, Object> params);

    List<UserCouponAndCouponDto> getUserCouponMap(Long userId, Integer start, Integer end);
    Integer getUserCouponMapCount(Long userId);

    List<ApiCouponDto> queryUseCoupon(ApiUserCouponMapReqDto couponMapReqDto, Long userId, HttpServletRequest request);

    /**
     * 计算 优惠金额
     * @param userId
     * @param couponId
     * @param totalAmount
     * @return
     */
    BigDecimal getDiscountAmount(Long userId, Long couponId, BigDecimal totalAmount);


    /**
     * 查找用户所拥有正常且没有过期的优惠券张数，
     */
    Integer getUserCouponMapCount(Long userId, Date date);
}

