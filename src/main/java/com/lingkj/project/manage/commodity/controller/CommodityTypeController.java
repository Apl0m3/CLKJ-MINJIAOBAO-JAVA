package com.lingkj.project.manage.commodity.controller;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.commodity.entity.CommodityType;
import com.lingkj.project.commodity.service.CommodityTypeService;
import com.lingkj.project.manage.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:26
 */
@RestController
@RequestMapping("/manage/commoditytype")
public class CommodityTypeController extends AbstractController {
    @Autowired
    private CommodityTypeService commodityTypeService;

    /**
     * 分页
     */
    @GetMapping("/list")
    @RequiresPermissions("manage:commoditytype:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = this.commodityTypeService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @GetMapping("/listAll")
    @RequiresPermissions("manage:commoditytype:listAll")
    public R listAll() {
        List<CommodityType> list = this.commodityTypeService.queryList();
        return R.ok(list);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("manage:commoditytype:info")
    public R info(@PathVariable("id") Long id) {
        CommodityType commodityType = commodityTypeService.getById(id);

        return R.ok().put("commodityType", commodityType);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("manage:commoditytype:save")
    public R save(@RequestBody CommodityType commodityType) {
        commodityType.setCreateSysUserId(getSysUserId());
        commodityType.setCreateTime(new Date());
        commodityTypeService.save(commodityType);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("manage:commoditytype:update")
    public R update(@RequestBody CommodityType commodityType) {
        commodityType.setUpdateTime(new Date());
        commodityType.setUpdateSysUserId(getSysUserId());
        commodityTypeService.updateById(commodityType);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("manage:commoditytype:delete")
    public R delete(@RequestBody Long[] ids) {
        commodityTypeService.updateBatchIds(ids);
        return R.ok();
    }


}
