package com.lingkj.project.manage.operation.controller;

import java.util.Arrays;
import java.util.Map;

import com.lingkj.project.operation.entity.OperateType;
import com.lingkj.project.operation.service.OperateTypeService;
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
 * 供应商 供应产品 类型
 *
 * @author chenyongsong
 * @date 2019-09-12 11:54:40
 */
@RestController
@RequestMapping("commodity/operatetype")
public class OperateTypeController {
    @Autowired
    private OperateTypeService operateTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("commodity:operatetype:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = operateTypeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("commodity:operatetype:info")
    public R info(@PathVariable("id") Long id){
			OperateType operateType = operateTypeService.getById(id);

        return R.ok().put("operateType", operateType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("commodity:operatetype:save")
    public R save(@RequestBody OperateType operateType){
			operateTypeService.save(operateType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("commodity:operatetype:update")
    public R update(@RequestBody OperateType operateType){
			operateTypeService.updateById(operateType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("commodity:operatetype:delete")
    public R delete(@RequestBody Long[] ids){
			operateTypeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
