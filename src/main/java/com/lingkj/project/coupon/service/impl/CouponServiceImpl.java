package com.lingkj.project.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.DateUtils;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.commodity.entity.Commodity;
import com.lingkj.project.commodity.service.CommodityService;
import com.lingkj.project.coupon.dto.CouponMerchanAddDto;
import com.lingkj.project.coupon.dto.CouponUserAddDto;
import com.lingkj.project.coupon.entity.Coupon;
import com.lingkj.project.coupon.mapper.CouponMapper;
import com.lingkj.project.coupon.service.CouponService;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.service.AdminUserService;
import com.lingkj.project.user.service.UserCouponMapService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;

import static com.lingkj.common.utils.ShiroUtils.getSysUserId;

/**
 * 优惠券
 *
 * @author chenyongsong
 * @date 2019-09-11 10:38:24
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements CouponService {

    @Autowired
    @Lazy
    private AdminUserService userService;

    @Autowired
    @Lazy
    private UserCouponMapService userCouponMapService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        boolean flg = true;
        if (StringUtils.isEmpty(name)) {
            flg = false;
        }
        String reg = "\"\"";
        if (reg.equals(name)) {
            flg = false;
        }
        IPage<Coupon> page = this.page(
                new Query<Coupon>(params).getPage(),
                new QueryWrapper<Coupon>().eq(true, "delete_status", 0)
                        .like(flg, "name", name).orderByDesc("create_at")
        );
        List<HashMap<String, Object>> couponType = this.getCouponType();
        List<Coupon> records = page.getRecords();
        for (Coupon coupon : records) {
            if (coupon.getCouponType() != null) {
                for (int j = 0; j < couponType.size(); j++) {
                    if (couponType.get(j) != null) {
                        BigInteger id = (BigInteger) couponType.get(j).get("id");
                        if (coupon.getCouponType().equals(Long.valueOf(id.toString()))) {
                            coupon.setCouponTypeName((String) couponType.get(j).get("name"));
                        }
                    }

                }
            }

            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("coupon_id", coupon.getId());
            int count = this.userCouponMapService.count(wrapper);
            coupon.setTotalNum(count);
        }
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void saveOrUpdateCoupon(Coupon coupon) {
        //所有用户集合
        List<User> userList = new ArrayList<>();
        if (coupon.getId() == null) {
            //新增
            coupon.setCreateAt(new Date());
            coupon.setCreateBy(getSysUserId());
            //根据是否全部用户获得优惠卷人数 0否 1是
            if (coupon.getGeneralPeople() == 0) {
                int length = coupon.getUserId().length;
                coupon.setTotalNum(length);
            }/* else {
                userList = baseMapper.getUserList();
                List<User> list = userService.getUserList();
                int size = userList.size();
                coupon.setTotalNum(size);
            }*/
            this.save(coupon);
            this.insertBatchIds(coupon);
            this.insertBatchUserIds(coupon, userList);
        }
    }

    @Override
    public List<HashMap<String, Object>> getCouponType() {
        return baseMapper.getCouponType();
    }

    @Override
    public List<HashMap<String, Object>> getCommodityId() {

        return baseMapper.getCommodityId();
    }

    @Override
    public List<HashMap<String, Object>> getUserList() {
        return baseMapper.getUserList();
    }

    @Override
    public void deleteById(Long id) {
        baseMapper.deleteById(id);
    }


    private void insertBatchIds(Coupon coupon) {
        Long id = coupon.getId();
        List<CouponMerchanAddDto> list = new ArrayList<>();
        if (coupon.getGeneral() == 0) {
            Long[] ids = coupon.getCommodityId();
            for (int i = 0; i < ids.length; i++) {
                CouponMerchanAddDto couponMerchanAddDto = new CouponMerchanAddDto();
                couponMerchanAddDto.setCouponId(id);
                couponMerchanAddDto.setCommodityId(ids[i]);
                couponMerchanAddDto.setCreateDate(DateUtils.current());
                couponMerchanAddDto.setCreateBy(getSysUserId());
                list.add(couponMerchanAddDto);
            }
            baseMapper.insertBatchIds(list);
        }
    }

    /**
     * 优惠卷和用户对应集合
     *
     * @param coupon   数据库存入的优惠卷实体
     * @param userList
     * @return void
     * @author XXX <XXX@163.com>
     * @date 2019/10/9 11:05
     */
    private void insertBatchUserIds(Coupon coupon, List<User> userList) {
        List<CouponUserAddDto> list = new ArrayList<>();
        //优惠卷id
        Long id = coupon.getId();
        //根据是否全部用户
        if (coupon.getGeneralPeople() == 0) {
            //不是全部用户使用时, 用户的id数组
            Long[] ids = coupon.getUserId();
            for (int i = 0; i < ids.length; i++) {
                //新增一个用户实体
                CouponUserAddDto couponUserAddDto = new CouponUserAddDto();
                //保存优惠卷id
                couponUserAddDto.setCouponId(id);
                //保存用户id
                couponUserAddDto.setUserId(ids[i]);
                couponUserAddDto.setCreateTime(coupon.getCreateAt());
                couponUserAddDto.setStartTime(coupon.getUseStartAt());
                couponUserAddDto.setExpireTime(coupon.getUseEndAt());
                list.add(couponUserAddDto);
                baseMapper.insertBatchUserIds(list);
            }
        } else {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("status", 0);
            Integer totalCount = userService.count(wrapper);
            Integer pageSize = 100;
            Integer totalPage = (int) Math.ceil((double) totalCount / pageSize);
            for (Integer page = 1; page <= totalPage; page++) {
                List<User> users = this.userService.queryList(page, pageSize);
                //全部用户使用
                for (User user : users) {
                    //新增一个用户实体
                    CouponUserAddDto couponUserAddDto = new CouponUserAddDto();
                    //保存优惠卷id
                    couponUserAddDto.setCouponId(id);
                    //保存用户id
                    couponUserAddDto.setUserId(user.getId());
                    couponUserAddDto.setCreateTime(coupon.getCreateAt());
                    couponUserAddDto.setStartTime(coupon.getUseStartAt());
                    couponUserAddDto.setExpireTime(coupon.getUseEndAt());
                    list.add(couponUserAddDto);
                }
                baseMapper.insertBatchUserIds(list);
            }
        }


    }

    @Override
    public List<HashMap<String, Object>> selectUserId(long id) {
        return baseMapper.selectUserName(id);
    }

    @Override
    public List<HashMap<String, Object>> selectCommodityId(long id) {
        return baseMapper.selectCommodityId(id);
    }

    @Override
    public List<Long> selectIdsByCommodity(Long commodityId) {
        return baseMapper.selectIdsByCommodity(commodityId, new Date());
    }

    @Override
    public List<HashMap<String, Object>> selectUserByName(String name) {
        return baseMapper.selectUserByName(name);
    }

    @Override
    public Coupon selectByCouponMapId(Long couponMapId) {
        return this.baseMapper.selectByCouponMapId(couponMapId);
    }
}
