package com.lingkj.project.cart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.api.cart.dto.ApiCartNumberAttributeDto;
import com.lingkj.project.cart.entity.CartNumberAttributes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author chenyongsong
 * @date 2019-10-22 15:27:26
 */
@Mapper
public interface CartNumberAttributesMapper extends BaseMapper<CartNumberAttributes> {
    /**
     * 查询属性
     *
     * @param id
     * @return
     */
    List<ApiCartNumberAttributeDto> selectNumberAttributeByCartId(Long id);
}
