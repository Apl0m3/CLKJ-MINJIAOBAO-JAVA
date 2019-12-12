package com.lingkj.project.manage.usertransaction.controller;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.cart.entity.Cart;
import com.lingkj.project.cart.service.CartService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 *
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:25
 */
@RestController
@RequestMapping("/manage/usercommoditycart")
public class UserCommodityCartController {
    @Autowired
    private CartService cartService;

    /**
     * 列表
     */
    @GetMapping("/list")
   @RequiresPermissions("manage:usercommoditycart:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cartService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
   @RequiresPermissions("manage:usercommoditycart:info")
    public R info(@PathVariable("id") Long id){
			Cart cart = cartService.getById(id);

        return R.ok().put("userCommodityCart", cart);
    }

    /**
     * 保存
     */
     @PostMapping("/save")
   @RequiresPermissions("manage:usercommoditycart:save")
    public R save(@RequestBody Cart cart){
			cartService.save(cart);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
   @RequiresPermissions("manage:usercommoditycart:update")
    public R update(@RequestBody Cart cart){
			cartService.updateById(cart);

        return R.ok();
    }

    /**
     * 删除
     */
     @PostMapping("/delete")
   @RequiresPermissions("manage:usercommoditycart:delete")
    public R delete(@RequestBody Long[] ids){
			cartService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
