package com.lingkj.project.cart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.project.api.cart.dto.ApiCartNumberAttributeDto;
import com.lingkj.project.cart.entity.CartNumberAttributes;

import java.util.List;

/**
 * 
 *
 * @author chenyongsong
 * @date 2019-10-22 15:27:26
 */
public interface CartNumberAttributesService extends IService<CartNumberAttributes> {

    void saveCartNumberAttributes(List<ApiCartNumberAttributeDto> numberAttributesList, Long commodityId, Long id);

    List<ApiCartNumberAttributeDto> selectNumberAttributeByCartId(Long id);
}

