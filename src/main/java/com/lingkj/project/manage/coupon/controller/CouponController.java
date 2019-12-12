package com.lingkj.project.manage.coupon.controller;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.coupon.entity.Coupon;
import com.lingkj.project.coupon.service.CouponService;
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
@RequestMapping("manage/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("coupon:coupon:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = couponService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("coupon:coupon:info")
    public R info(@PathVariable("id") Long id){
			Coupon coupon = couponService.getById(id);
        List<HashMap<String, Object>> list = couponService.selectUserId(id);
        List<HashMap<String, Object>> commodityIdList = couponService.selectCommodityId(id);
        coupon.setUserName(list);
        coupon.setCommodityName(commodityIdList);
        return R.ok().put("coupon", coupon);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("coupon:coupon:save")
    public R save(@RequestBody Coupon coupon){
        couponService.saveOrUpdateCoupon(coupon);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("coupon:coupon:update")
    public R update(@RequestBody Coupon coupon){
        couponService.saveOrUpdate(coupon);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("coupon:coupon:delete")
    public R delete(@RequestParam Map<String, Object> params){
        String id = (String)params.get("id");
        couponService.deleteById(Long.valueOf(id));

        return R.ok();
    }

    /**
     *查询所有优惠卷类型
     */
    @RequestMapping("/getCouponType")
    @RequiresPermissions("coupon:coupon:type")
    public R getCouponType(){
        List<HashMap<String, Object>> list = couponService.getCouponType();
        return R.ok().put("list",list);
    }
    /**
     *查询所有商品
     */
    @RequestMapping("/getCommodityId")
    @RequiresPermissions("coupon:coupon:type")
    public R getCommodityId(){
        List<HashMap<String, Object>> list = couponService.getCommodityId();
        return R.ok().put("list",list);
    }

    /**
     *查询所有用户
     */
    @RequestMapping("/getUserList")
    @RequiresPermissions("coupon:coupon:type")
    public R getUserList(){
        List<HashMap<String, Object>> list = couponService.getUserList();
        return R.ok().put("list",list);
    }

    /**
     *通过名字查询相关用户
     */
    @GetMapping("/getUserListByName")
    @RequiresPermissions("coupon:coupon:type")
    public R getUserListByName(@RequestParam("name") String name){
        List<HashMap<String, Object>> list = couponService.selectUserByName(name);
        return R.ok().put("list",list);
    }
}
