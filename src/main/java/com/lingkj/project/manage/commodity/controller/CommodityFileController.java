package com.lingkj.project.manage.commodity.controller;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.commodity.entity.CommodityFile;
import com.lingkj.project.commodity.service.CommodityFileService;
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
 * @date 2019-06-26 16:10:26
 */
@RestController
@RequestMapping("/manage/commodityfile")
public class CommodityFileController {
    @Autowired
    private CommodityFileService commodityFileService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("manage:commodityfile:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = commodityFileService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("manage:commodityfile:info")
    public R info(@PathVariable("id") Long id){
			CommodityFile commodityFile = commodityFileService.getById(id);

        return R.ok().put("commodityFile", commodityFile);
    }

    /**
     * 保存
     */
     @PostMapping("/save")
    @RequiresPermissions("manage:commodityfile:save")
    public R save(@RequestBody CommodityFile commodityFile){
			commodityFileService.save(commodityFile);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("manage:commodityfile:update")
    public R update(@RequestBody CommodityFile commodityFile){
			commodityFileService.updateById(commodityFile);

        return R.ok();
    }

    /**
     * 删除
     */
     @PostMapping("/delete")
    @RequiresPermissions("manage:commodityfile:delete")
    public R delete(@RequestBody Long[] ids){
			commodityFileService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
