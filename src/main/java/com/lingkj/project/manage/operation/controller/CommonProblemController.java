package com.lingkj.project.manage.operation.controller;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;

import com.lingkj.project.operation.entity.CommonProblem;
import com.lingkj.project.operation.service.CommonProblemService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.Map;


/**
 * 常见问题
 *
 * @author chenyongsong
 * @date 2019-09-19 11:02:41
 */
@RestController
@RequestMapping("manage/operate/commonproblem")
public class CommonProblemController {
    @Autowired
    private CommonProblemService commonProblemService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("commonproblem:commonproblem:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = commonProblemService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("commonproblem:commonproblem:info")
    public R info(@PathVariable("id") Long id){
			CommonProblem operateCommonProblem = commonProblemService.getById(id);

        return R.ok().put("commonProblem", operateCommonProblem);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("commonproblem:commonproblem:save")
    public R save(@RequestBody CommonProblem commonProblem){
        if(commonProblem.getId() != null){
            commonProblem.setUpdateTime(new Date());
        }else {
            commonProblem.setCreateTime(new Date());
        }
        commonProblemService.saveOrUpdate(commonProblem);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("commonproblem:commonproblem:update")
    public R update(@RequestBody CommonProblem operateCommonProblem){
			commonProblemService.updateById(operateCommonProblem);
        return R.ok();
    }
    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("commonproblem:commonproblem:delete")
    public R delete(@RequestBody Long[] ids){

        commonProblemService.updateBatchIds(ids);

        return R.ok();
    }

}
