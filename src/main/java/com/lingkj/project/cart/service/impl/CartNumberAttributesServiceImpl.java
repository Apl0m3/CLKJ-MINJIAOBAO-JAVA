package com.lingkj.project.cart.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.project.api.cart.dto.ApiCartNumberAttributeDto;
import com.lingkj.project.cart.entity.CartNumberAttributes;
import com.lingkj.project.cart.mapper.CartNumberAttributesMapper;
import com.lingkj.project.cart.service.CartNumberAttributesService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyongsong
 * @date 2019-10-22 15:27:26
 */
@Service
public class CartNumberAttributesServiceImpl extends ServiceImpl<CartNumberAttributesMapper, CartNumberAttributes>
        implements CartNumberAttributesService {


    @Override
    public void saveCartNumberAttributes(List<ApiCartNumberAttributeDto> numberAttributesList, Long commodityId, Long id) {
        List<CartNumberAttributes> list = new ArrayList<>();
        for (ApiCartNumberAttributeDto numberAttributeDto : numberAttributesList) {
            if (numberAttributeDto.getNum()!=null && numberAttributeDto.getNum()>0) {
                CartNumberAttributes cartNumberAttributes = new CartNumberAttributes();
                cartNumberAttributes.setCartId(id);
                cartNumberAttributes.setName(numberAttributeDto.getName());
                cartNumberAttributes.setNum(numberAttributeDto.getNum());
                list.add(cartNumberAttributes);
            }
        }
        this.saveBatch(list);
    }

    @Override
    public List<ApiCartNumberAttributeDto> selectNumberAttributeByCartId(Long id) {
        return this.baseMapper.selectNumberAttributeByCartId(id);
    }

}
