package com.lingkj.project.manage.adminuser.controller;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.integral.entity.IntegralUser;
import com.lingkj.project.integral.service.UserIntegralService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;





/**
 * 用户积分
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:25
 */
@RestController
@RequestMapping("/manage/userintegral")
public class IntegralUserController {
    @Autowired
    private UserIntegralService userIntegralService;

    /**
     * 列表
     */
    @GetMapping("/list")
//    @RequiresPermissions("manage:userintegral:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userIntegralService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("manage:userintegral:info")
    public R info(@PathVariable("id") Long id){
			IntegralUser IntegralUser = userIntegralService.getById(id);

        return R.ok().put("userIntegral", IntegralUser);
    }

    /**
     * 保存
     */
     @PostMapping("/save")
    @RequiresPermissions("manage:userintegral:save")
    public R save(@RequestBody IntegralUser userIntegral){
			userIntegralService.save(userIntegral);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("manage:userintegral:update")
    public R update(@RequestBody IntegralUser userIntegral){
			userIntegralService.updateById(userIntegral);

        return R.ok();
    }

    /**
     * 删除
     */
     @PostMapping("/delete")
    @RequiresPermissions("manage:userintegral:delete")
    public R delete(@RequestBody Long[] ids){
			userIntegralService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
