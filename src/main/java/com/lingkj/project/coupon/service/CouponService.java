package com.lingkj.project.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.coupon.entity.Coupon;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 优惠券
 *
 * @author chenyongsong
 * @date 2019-09-11 10:38:24
 */
public interface CouponService extends IService<Coupon> {

    PageUtils queryPage(Map<String, Object> params);


    /**
     * 新增
     * @param coupon
     */
    void saveOrUpdateCoupon(Coupon coupon);

    /**
     * 查询所有优惠卷类型
     */
    List<HashMap<String, Object>> getCouponType();


    /**
     * 查询普通未下架商品id
     */
    List<HashMap<String, Object>> getCommodityId();


    /**
     * 查询正常用户
     */
    List<HashMap<String, Object>> getUserList();

    /**
     * 逻辑删除
     * @param  ids
     */
    void deleteById(Long ids);
//    /**
//     * 根据用户选择的优惠商品 插入到表coupon_merchan中 优惠卷使用方
//     * @param coupon
//     */
//    void insertBatchIds(Coupon coupon);


//    /**
//     * 根据用户选择的用户 插入到表user_coupon_map
//     * @param coupon
//     */
//    void insertBatchUserIds(Coupon coupon);


    /**
     * 查询所有用户
     * @param id
     * @return
     */
    List<HashMap<String, Object>> selectUserId(long id);

    /**
     *  通过邮箱like查询
     * @author XXX <XXX@163.com>
     * @date 2019/10/9 16:38
     * @param name
     * @return java.util.List<java.util.HashMap<java.lang.String,java.lang.Object>>
     */
    List<HashMap<String, Object>> selectUserByName(String name);

    /**
     * 查询优惠卷相关商品id
     * @param id
     * @return
     */
    List<HashMap<String, Object>> selectCommodityId(long id);

    List<Long> selectIdsByCommodity(Long commodityId);

    Coupon selectByCouponMapId(Long couponMapId);
}

