package com.lingkj.project.coupon.mapper;

import com.lingkj.project.coupon.dto.CouponMerchanAddDto;
import com.lingkj.project.coupon.dto.CouponUserAddDto;
import com.lingkj.project.coupon.entity.Coupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 优惠券
 *
 * @author chenyongsong
 * @date 2019-09-11 10:38:24
 */
@Mapper
public interface CouponMapper extends BaseMapper<Coupon> {
    /**
     * 查询所有优惠卷类型
     */
    List<HashMap<String, Object>> getCouponType();

    /**
     * 查询商品
     * @return
     */

    List<HashMap<String, Object>> getCommodityId();

    /**
     * 查询正常用户
     *
     * @return
     */
    List<HashMap<String, Object>> getUserList();

    /**
     * 逻辑删除
     *
     * @param id
     */
    void deleteById(@Param("id") Long id);

    /**
     * 插入多个商品id
     *
     * @param list
     */
    void insertBatchIds(List<CouponMerchanAddDto> list);

    /**
     * 插入多个用户id
     *
     * @param list
     */
    void insertBatchUserIds(List<CouponUserAddDto> list);

    /**
     * 查询优惠卷使用的人
     *
     * @param id couponId
     * @return 名字集合
     */
    List<HashMap<String, Object>> selectUserName(@Param("couponId") long id);

    /**
     * 通过名字查询相关的用户
     * @author XXX <XXX@163.com>
     * @date 2019/10/9 16:39
     * @param name
     * @return java.util.List<java.util.HashMap<java.lang.String,java.lang.Object>>
     */
    List<HashMap<String, Object>> selectUserByName(@Param("name") String name);
    /**
     * 查询优惠卷对应的商品
     *
     * @param id couponId
     * @return 商品名字集合
     */
    List<HashMap<String, Object>> selectCommodityId(@Param("couponId") long id);

    /**
     * 查询未过期 对应商品的优惠券
     *
     * @param commodityId
     * @param date
     * @return
     */
    List<Long> selectIdsByCommodity(@Param("commodityId") Long commodityId, @Param("date") Date date);


    Coupon selectByCouponMapId(Long couponMapId);
}
