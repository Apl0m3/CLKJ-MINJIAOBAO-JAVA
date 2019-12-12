package com.lingkj.project.cart.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.project.api.cart.dto.ApiCartLadderDto;
import com.lingkj.project.cart.entity.CartLadder;
import com.lingkj.project.cart.mapper.CartLadderMapper;
import com.lingkj.project.cart.service.CartLadderService;
import org.springframework.stereotype.Service;

/**
 * 订单商品数量阶梯价
 *
 * @author chenyongsong
 * @date 2019-10-22 15:27:25
 */
@Service
public class CartLadderServiceImpl extends ServiceImpl<CartLadderMapper, CartLadder> implements CartLadderService {


    @Override
    public ApiCartLadderDto selectByCartId(Long id) {
        return this.baseMapper.selectByCartId(id);
    }
}
