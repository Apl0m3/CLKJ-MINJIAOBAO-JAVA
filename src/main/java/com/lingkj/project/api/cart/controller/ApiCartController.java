package com.lingkj.project.api.cart.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.project.api.commodity.dto.ApiCommodityCartAddDto;
import com.lingkj.project.cart.entity.Cart;
import com.lingkj.project.cart.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * ApiCommodityCartController
 *
 * @author chen yongsong
 * @className ApiCommodityCartController
 * @date 2019/9/29 17:08
 */
@Slf4j
@Api("购物车接口")
@RestController
@RequestMapping("/api/commodity/cart")
public class ApiCartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private MessageUtils messageUtils;


    @ApiOperation(value = "购物车分页接口", httpMethod = "GET")
    @Login
    @GetMapping("/queryPage")
    public R queryUserCartPage(Page page, @RequestAttribute("userId") Long userId) {
        PageUtils pageUtils = cartService.queryPageApi(page, userId);
        return R.ok().put("page", pageUtils);
    }

    @ApiOperation(value = "添加购物车接口", httpMethod = "POST")
    @Login
    @PostMapping("/saveCart")
    public R saveCart(@RequestBody ApiCommodityCartAddDto commodityCartAddDto, @RequestAttribute("userId") Long userId, HttpServletRequest request) {
        if (commodityCartAddDto.getCommodityId() == null) {
            return R.error(messageUtils.getMessage("api.commodity.cart.commodity.isEmpty", request));
        }
        if (commodityCartAddDto.getLadderExpect() == null) {
            return R.error(messageUtils.getMessage("api.commodity.cart.commodityExpect.isEmpty", request));
        }
        commodityCartAddDto.getAttribute().size();
        cartService.saveCart(commodityCartAddDto, userId, request);
        return R.ok();
    }

    @ApiOperation(value = "删除购物车中一个商品", httpMethod = "GET")
    @Login
    @GetMapping("/deleteShopCartById/{id}")
    public R deleteShopCartById(@PathVariable("id") Long id,@RequestAttribute("userId") Long userId) {
        cartService.removeById(id,userId);
        return R.ok();
    }

    @ApiOperation(value = "查询用户购物车数量", httpMethod = "GET")
    @Login
    @GetMapping("/queryCount")
    public R queryCount(@RequestAttribute("userId") Long userId) {
        Integer count = null;
        if (userId != null){
            count = cartService.count(new QueryWrapper<Cart>().eq("user_id", userId));
        }
            return R.ok(count);
    }

}
