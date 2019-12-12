package com.lingkj.project.cart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.cart.dto.ApiCartAttributesDto;
import com.lingkj.project.cart.entity.CartAttributes;

import java.util.List;
import java.util.Map;

/**
 * 订单 商品属性
 *
 * @author chenyongsong
 * @date 2019-10-22 15:27:26
 */
public interface CartAttributesService extends IService<CartAttributes> {
    /**
     * 查询购物车属性
     * @param id
     * @return
     */
    List<ApiCartAttributesDto> selectListByCartId(Long id);
}

