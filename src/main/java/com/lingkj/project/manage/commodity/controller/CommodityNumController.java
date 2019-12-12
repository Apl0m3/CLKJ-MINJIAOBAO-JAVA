package com.lingkj.project.manage.commodity.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lingkj.project.commodity.entity.CommodityLadder;
import com.lingkj.project.commodity.service.CommodityLadderService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;



/**
 * 商品数量阶梯
 *
 * @author chenyongsong
 * @date 2019-09-17 15:13:42
 */
@RestController
@RequestMapping("/manage/commoditynum")
public class CommodityNumController {
    @Autowired
    private CommodityLadderService commodityNumService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("commodity:commoditynum:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = commodityNumService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("commodity:commoditynum:info")
    public R info(@PathVariable("id") Long id){
			CommodityLadder commodityNum = commodityNumService.getById(id);

        return R.ok().put("commodityNum", commodityNum);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("commodity:commoditynum:save")
    public R save(@RequestBody CommodityLadder commodityNum){
			commodityNumService.save(commodityNum);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:commoditynum:update")
    public R update(@RequestBody CommodityLadder commodityNum){
			commodityNumService.updateById(commodityNum);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:commoditynum:delete")
    public R delete(@RequestBody Long[] ids){
			commodityNumService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
