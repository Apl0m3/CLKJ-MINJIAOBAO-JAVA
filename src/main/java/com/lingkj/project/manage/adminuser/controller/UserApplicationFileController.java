package com.lingkj.project.manage.adminuser.controller;

import java.util.Arrays;
import java.util.Map;

import com.lingkj.project.user.entity.UserApplicationFile;
import com.lingkj.project.user.service.UserApplicationFileService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;



/**
 * 设计师 申请 上传 案例url
 *
 * @author chenyongsong
 * @date 2019-09-12 11:54:40
 */
@RestController
@RequestMapping("commodity/userapplicationfile")
public class UserApplicationFileController {
    @Autowired
    private UserApplicationFileService userApplicationFileService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("commodity:userapplicationfile:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userApplicationFileService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("commodity:userapplicationfile:info")
    public R info(@PathVariable("id") Long id){
			UserApplicationFile userApplicationFile = userApplicationFileService.getById(id);

        return R.ok().put("userApplicationFile", userApplicationFile);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("commodity:userapplicationfile:save")
    public R save(@RequestBody UserApplicationFile userApplicationFile){
			userApplicationFileService.save(userApplicationFile);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("commodity:userapplicationfile:update")
    public R update(@RequestBody UserApplicationFile userApplicationFile){
			userApplicationFileService.updateById(userApplicationFile);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("commodity:userapplicationfile:delete")
    public R delete(@RequestBody Long[] ids){
			userApplicationFileService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
