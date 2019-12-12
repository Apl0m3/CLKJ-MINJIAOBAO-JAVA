package com.lingkj.project.commodity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.api.cart.dto.ApiCartDto;
import com.lingkj.project.cart.entity.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:25
 */
@Mapper
public interface CartMapper extends BaseMapper<Cart> {
    /**
     * 根据商品查询购物车
     *
     * @param commodityNormId
     * @param userId
     * @return
     */
    Cart selectByNormId(Long commodityNormId, Long userId);

    /**
     * 删除购物车（逻辑删除）
     *
     * @param asList
     * @param userId
     */
    void updateCommodityCartStatus(@Param("asList") List<Long> asList, @Param("userId") Long userId);

    /**
     * 查询购物车
     *
     * @param commodityUserCardId
     * @param userId
     * @return
     */
    Cart selectByUserCartId(Long commodityUserCardId, Long userId);

    /**
     * 统计用户 购物车数量
     *
     * @param userId
     * @return
     */
    Integer queryCommodityCartCount(@Param("userId")Long userId);

    /**
     * 分页查询购物车
     *
     * @param limit
     * @param pageSize
     * @param id
     * @return
     */
    List<ApiCartDto> queryPageApi(@Param("limit") Integer limit, @Param("pageSize") Integer pageSize, @Param("id") Long id);






    /**
     * 删除CartAttributes表
     * @author XXX <XXX@163.com>
     * @date 2019/10/22 19:46
     * @param id
     * @return void
     */
    void deleteTableCartAttributesByCartId(@Param("id") Long id);


    /**
     * 删除cart_expect表
     * @author XXX <XXX@163.com>
     * @date 2019/10/22 19:46
     * @param id
     * @return void
     */
    void deleteTableCartExpectByCartId(@Param("id")Long id);


    /**
     * 删除cart_ladder表
     * @author XXX <XXX@163.com>
     * @date 2019/10/22 19:46
     * @param id
     * @return void
     */
    void deleteTableCartLadderByCartId(@Param("id")Long id);

    /**
     * 删除cart_number_attributes表
     * @author XXX <XXX@163.com>
     * @date 2019/10/22 19:46
     * @param id
     * @return void
     */
    void deleteTableCartNumberAttributesByCartId(@Param("id")Long id);


}
