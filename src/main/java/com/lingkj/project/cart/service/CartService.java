package com.lingkj.project.cart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.commodity.dto.ApiCommodityCartAddDto;
import com.lingkj.project.api.dto.AmountDto;
import com.lingkj.project.cart.entity.Cart;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:25
 */
public interface CartService extends IService<Cart> {

    PageUtils queryPage(Map<String, Object> params);


    PageUtils queryPageApi(Page page,Long userId);

    /**
     * 加入购物车
     * @param commodityCartAddDto
     * @param userId
     * @param request
     */
    void saveCart(ApiCommodityCartAddDto commodityCartAddDto, Long userId, HttpServletRequest request);



    /**
     * 删除购物车和关联表记录
     * @author XXX <XXX@163.com>
     * @date 2019/10/22 19:41
     * @param id
     * @return void
     */
    void removeById(Long id,Long userId);


    AmountDto cartAmount(Long cartId, Long userId,HttpServletRequest request);
}

