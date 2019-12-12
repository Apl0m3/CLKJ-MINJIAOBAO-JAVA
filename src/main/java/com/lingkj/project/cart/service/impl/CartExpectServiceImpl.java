package com.lingkj.project.cart.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.project.api.cart.dto.ApiCartExpectDto;
import com.lingkj.project.cart.entity.CartExpect;
import com.lingkj.project.cart.mapper.CartExpectMapper;
import com.lingkj.project.cart.service.CartExpectService;
import org.springframework.stereotype.Service;

/**
 * 订单商品 预计到货时间
 *
 * @author chenyongsong
 * @date 2019-10-22 15:27:26
 */
@Service
public class CartExpectServiceImpl extends ServiceImpl<CartExpectMapper, CartExpect>
        implements CartExpectService {


    @Override
    public ApiCartExpectDto selectByCartId(Long id) {
        return this.baseMapper.selectByCartId(id);
    }
}
