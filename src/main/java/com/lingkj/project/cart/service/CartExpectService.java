package com.lingkj.project.cart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.cart.dto.ApiCartExpectDto;
import com.lingkj.project.cart.entity.CartExpect;

import java.util.Map;

/**
 * 购物车 预计到货时间
 *
 * @author chenyongsong
 * @date 2019-10-22 15:27:26
 */
public interface CartExpectService extends IService<CartExpect> {
    /**
     * 购物车  预计到货时间
     * @param id
     * @return
     */
    ApiCartExpectDto selectByCartId(Long id);
}

