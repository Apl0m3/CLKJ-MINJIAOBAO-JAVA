package com.lingkj.project.manage.hot.controller;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.coupon.entity.Coupon;
import com.lingkj.project.coupon.service.CouponService;
import com.lingkj.project.hot.service.HotWordsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 优惠券
 *
 * @author chenyongsong
 * @date 2019-09-11 10:38:24
 */
@RestController
@RequestMapping("manage/hotWords")
public class HotWordsController {
    @Autowired
    private HotWordsService hotWordsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("manage:hotWords:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = hotWordsService.queryPage(params);
        return R.ok().put("page", page);
    }


}
