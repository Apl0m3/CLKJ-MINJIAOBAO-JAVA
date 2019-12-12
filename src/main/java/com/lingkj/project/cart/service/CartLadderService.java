package com.lingkj.project.cart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.cart.dto.ApiCartLadderDto;
import com.lingkj.project.cart.entity.CartLadder;

import java.util.Map;

/**
 * 订单商品数量阶梯价
 *
 * @author chenyongsong
 * @date 2019-10-22 15:27:25
 */
public interface CartLadderService extends IService<CartLadder> {

    ApiCartLadderDto selectByCartId(Long id);
}

