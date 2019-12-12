package com.lingkj.project.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.AmountUtil;
import com.lingkj.common.utils.DateUtils;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.api.user.dto.ApiCouponDto;
import com.lingkj.project.api.user.dto.ApiUserCouponMapReqDto;
import com.lingkj.project.coupon.entity.Coupon;
import com.lingkj.project.coupon.service.CouponService;
import com.lingkj.project.user.dto.UserCouponAndCouponDto;
import com.lingkj.project.user.entity.UserCouponMap;
import com.lingkj.project.user.mapper.UserCouponMapMapper;
import com.lingkj.project.user.service.UserCouponMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * user transaction log
 *
 * @author chenyongsong
 * @date 2019-09-20 16:05:21
 */
@Service
public class UserCouponMapServiceImpl extends ServiceImpl<UserCouponMapMapper, UserCouponMap> implements UserCouponMapService {
    @Autowired
    private CouponService couponService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        params.put("sidx", "createTime");
        params.put("order", "ASC");
        QueryWrapper<UserCouponMap> wrapper = new QueryWrapper<>();
        Long userId = (Long) params.get("userId");
        if (userId != null) {
            wrapper.eq("userId", userId);
        }
        IPage<UserCouponMap> page = this.page(
                new Query<UserCouponMap>(params).getPage(),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<UserCouponAndCouponDto> getUserCouponMap(Long userId, Integer start, Integer end) {
        Date date=new Date();
//        String format = DateUtils.format(date, DateUtils.DATE_TIME_PATTERN);
        List<UserCouponAndCouponDto> userCoupon = this.baseMapper.getUserCoupon(userId,date, start, end);
//      Integer count = this.baseMapper.queryUserCouponCount(userId);
        return userCoupon;
    }

    @Override
    public Integer getUserCouponMapCount(Long userId) {
        return this.baseMapper.queryUserCouponCount(userId,new Date());
    }

    @Override
    public List<ApiCouponDto> queryUseCoupon(ApiUserCouponMapReqDto couponMapReqDto, Long userId, HttpServletRequest request) {
        List<Long> couponIds = couponService.selectIdsByCommodity(couponMapReqDto.getCommodityId());
        List<ApiCouponDto> list = this.baseMapper.queryUseCoupon(userId, couponMapReqDto.getCommodityId(), new Date(), couponIds);

        return this.judgmentCoupon(list, couponMapReqDto);
    }

    @Override
    public BigDecimal getDiscountAmount(Long userId, Long couponMapId, BigDecimal totalAmount) {
        BigDecimal couponAmount = BigDecimal.ZERO;
        Coupon coupon = couponService.selectByCouponMapId(couponMapId);
        if (coupon != null) {
            if (coupon.getCouponType() == 1) {
                BigDecimal condition = new BigDecimal(coupon.getRegulation()).multiply(AmountUtil.HUNDRED);
                BigDecimal amount = totalAmount.multiply(AmountUtil.HUNDRED);
                if (amount.compareTo(condition) >= 0) {
                    couponAmount = new BigDecimal(coupon.getResult());
                    updateCouponMapStatus(couponMapId);
                }
            }

        }
        return couponAmount;
    }

    private void updateCouponMapStatus(Long couponMapId) {
        UserCouponMap couponMap = new UserCouponMap();
        couponMap.setId(couponMapId);
        couponMap.setStatus(UserCouponMap.STATUS_USE);
        this.baseMapper.updateById(couponMap);
    }

    @Override
    public Integer getUserCouponMapCount(Long userId, Date date) {
        QueryWrapper<UserCouponMap> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("user_id", userId);
        wrapper2.eq("status", 0);
        wrapper2.gt("expire_time", date);
        wrapper2.le("start_time", date);
        return this.count(wrapper2);
    }

    public List<ApiCouponDto> judgmentCoupon(List<ApiCouponDto> list, ApiUserCouponMapReqDto couponMapReqDto) {
        List<ApiCouponDto> resultList = new ArrayList<>();
        for (ApiCouponDto apiCouponDto : list) {
            if (apiCouponDto.getCouponType() == 1) {
                BigDecimal condition = new BigDecimal(apiCouponDto.getRegulation()).multiply(AmountUtil.HUNDRED);
                if (couponMapReqDto.getCommodityId() != null) {
                    BigDecimal amount = couponMapReqDto.getAmount().multiply(AmountUtil.HUNDRED);
                    if (amount.compareTo(condition) >= 0) {
                        apiCouponDto.setCouponAmount(new BigDecimal(apiCouponDto.getResult()));
                        resultList.add(apiCouponDto);
                    }
                }
            }
        }
        return resultList;
    }


}
