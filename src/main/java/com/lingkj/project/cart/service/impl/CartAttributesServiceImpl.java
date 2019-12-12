package com.lingkj.project.cart.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.project.api.cart.dto.ApiCartAttributesDto;
import com.lingkj.project.cart.entity.CartAttributes;
import com.lingkj.project.cart.mapper.CartAttributesMapper;
import com.lingkj.project.cart.service.CartAttributesService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单 商品属性
 *
 * @author chenyongsong
 * @date 2019-10-22 15:27:26
 */
@Service
public class CartAttributesServiceImpl extends ServiceImpl<CartAttributesMapper, CartAttributes>
        implements CartAttributesService {


    @Override
    public List<ApiCartAttributesDto> selectListByCartId(Long id) {
        return this.baseMapper.selectListByCartId(id);
    }
}
