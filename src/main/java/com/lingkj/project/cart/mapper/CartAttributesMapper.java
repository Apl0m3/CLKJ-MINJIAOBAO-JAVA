package com.lingkj.project.cart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.api.cart.dto.ApiCartAttributesDto;
import com.lingkj.project.cart.entity.CartAttributes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单 商品属性
 * 
 * @author chenyongsong
 * @date 2019-10-22 15:27:26
 */
@Mapper
public interface CartAttributesMapper extends BaseMapper<CartAttributes> {
    /**
     * 查询购物车订单属性
     * @param id
     * @return
     */
    List<ApiCartAttributesDto> selectListByCartId(Long id);
}
