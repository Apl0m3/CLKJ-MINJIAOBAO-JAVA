package com.lingkj.project.cart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.api.cart.dto.ApiCartLadderDto;
import com.lingkj.project.cart.entity.CartLadder;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单商品数量阶梯价
 *
 * @author chenyongsong
 * @date 2019-10-22 15:27:25
 */
@Mapper
public interface CartLadderMapper extends BaseMapper<CartLadder> {
    /**
     * 查询购物车 商品数量阶梯价
     *
     * @param id
     * @return
     */
    ApiCartLadderDto selectByCartId(Long id);
}
