package com.lingkj.project.manage.operation.controller;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.operation.entity.OperateRate;
import com.lingkj.project.operation.service.OperateRateService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;



/**
 * 费率表
 *
 * @author chenyongsong
 * @date 2019-10-10 09:15:52
 */
@RestController
@RequestMapping("/manage/operation/operateRate")
public class OperateRateController {
    @Autowired
    private OperateRateService operateRateService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operation:operateRate:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = operateRateService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("operation:operateRate:info")
    public R info(@PathVariable("id") Long id){
			OperateRate operateRate = operateRateService.getById(id);

        return R.ok().put("operateRate", operateRate);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("operation:operateRate:save")
    public R save(@RequestBody OperateRate operateRate){
        operateRate.setCreateAt(new Date());
        operateRateService.save(operateRate);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("operation:operateRate:update")
    public R update(@RequestBody OperateRate operateRate){
        operateRate.setUpdateAt(new Date());
        operateRateService.updateById(operateRate);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("operation:operateRate:delete")
    public R delete(@RequestBody Long[] ids){
        operateRateService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
}
