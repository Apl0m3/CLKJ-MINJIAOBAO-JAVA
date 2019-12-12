package com.lingkj.project.cart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.api.cart.dto.ApiCartExpectDto;
import com.lingkj.project.cart.entity.CartExpect;
import org.apache.ibatis.annotations.Mapper;

/**
 * 购物车商品 预计到货时间
 * 
 * @author chenyongsong
 * @date 2019-10-22 15:27:26
 */
@Mapper
public interface CartExpectMapper extends BaseMapper<CartExpect> {
    /**
     *  购物车商品 预计到货时间
     * @param id
     * @return
     */
    ApiCartExpectDto selectByCartId(Long id);
}
