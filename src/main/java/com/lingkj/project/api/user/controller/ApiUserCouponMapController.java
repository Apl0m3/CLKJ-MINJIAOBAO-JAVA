package com.lingkj.project.api.user.controller;

import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.AmountUtil;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.api.user.dto.ApiCouponDto;
import com.lingkj.project.api.user.dto.ApiUserCouponMapReqDto;
import com.lingkj.project.user.dto.UserCouponAndCouponDto;
import com.lingkj.project.user.service.UserCouponMapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * ApiUserCouponMapController
 *
 * @author chen yongsong
 * @className ApiUserCouponMapController
 * @date 2019/9/30 18:04
 */
@Api(value = "用户优惠券接口")
@RestController
@RequestMapping("/api/user/coupon")
public class ApiUserCouponMapController {
    @Autowired
    private UserCouponMapService userCouponMapService;

    @Login
    @ApiModelProperty("查询")
    @PostMapping("/queryUseCoupon")
    public R queryUseCoupon(@RequestBody ApiUserCouponMapReqDto couponMapReqDto, @RequestAttribute("userId") Long userId, HttpServletRequest request) {
        List<ApiCouponDto> list=userCouponMapService.queryUseCoupon(couponMapReqDto,userId,request);
        return R.ok().put("list",list);
    }

    /**
     * 优惠券列表
     */
    @ApiModelProperty("用户卡包 优惠券列表")
    @PostMapping("/couponMap")
    @Login
    public R couponMapList(@RequestBody Page page, @RequestAttribute("userId") Long userId) {
        Integer start = page.getLimit();
        Integer end = page.getPageSize();
        List<UserCouponAndCouponDto> userCouponMap = userCouponMapService.getUserCouponMap(userId, start, end);
        Integer userCouponMapCount = userCouponMapService.getUserCouponMapCount(userId);
        PageUtils pageUtils = new PageUtils(userCouponMap, userCouponMapCount, page.getPageSize(), page.getPage());
        return R.ok().put("page", pageUtils);
    }

}
