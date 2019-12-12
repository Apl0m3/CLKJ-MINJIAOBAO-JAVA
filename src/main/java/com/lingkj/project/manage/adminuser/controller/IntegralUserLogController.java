package com.lingkj.project.manage.adminuser.controller;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.integral.entity.IntegralUserLog;
import com.lingkj.project.integral.service.UserIntegralLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 用户积分记录
 *
 * @author chenyongsong
 * @date 2019-06-26 16:10:25
 */
@RestController
@RequestMapping("/manage/userintegrallog")
public class IntegralUserLogController {
    @Autowired
    private UserIntegralLogService userIntegralLogService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("manage:userintegrallog:list")
    public R list(@RequestParam Map<String, Object> params, Long userId) {
        PageUtils page = userIntegralLogService.queryPage(params, userId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("manage:userintegrallog:info")
    public R info(@PathVariable("id") Long id) {
        IntegralUserLog userIntegralLog = userIntegralLogService.getById(id);

        return R.ok().put("userIntegralLog", userIntegralLog);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("manage:userintegrallog:save")
    public R save(@RequestBody IntegralUserLog userIntegralLog) {
        userIntegralLogService.save(userIntegralLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("manage:userintegrallog:update")
    public R update(@RequestBody IntegralUserLog userIntegralLog) {
        userIntegralLogService.updateById(userIntegralLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("manage:userintegrallog:delete")
    public R delete(@RequestBody Long[] ids) {
        userIntegralLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
