package com.lingkj.project.manage.operation.controller;

import com.lingkj.common.utils.DateUtils;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.manage.sys.controller.AbstractController;
import com.lingkj.project.operation.entity.OperateRules;
import com.lingkj.project.operation.service.OperateRulesService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 积分规则
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:26
 */
@RestController
@RequestMapping("/manage/integralrules")
public class IntegralRulesController extends AbstractController {
    @Autowired
    private OperateRulesService operateRulesService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("manage:integralrules:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = operateRulesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("manage:integralrules:info")
    public R info(@PathVariable("id") Long id) {
        OperateRules integralRules = operateRulesService.getById(id);

        return R.ok().put("integralRules", integralRules);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("manage:integralrules:save")
    public R save(@RequestBody(required = false) OperateRules integralRules) {
        integralRules.setCreateSysUserId(getSysUserId());
        integralRules.setCreateTime(DateUtils.current());
        integralRules.setStatus(0);
        operateRulesService.save(integralRules);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("manage:integralrules:update")
    public R update(@RequestBody(required = false) OperateRules integralRules) {
        integralRules.setUpdateSysUserId(getSysUserId());
        integralRules.setUpdateTime(DateUtils.current());
        operateRulesService.updateById(integralRules);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("manage:integralrules:delete")
    public R delete(@RequestParam("ids") Long[] ids) {
        operateRulesService.updateStatusByIds(Arrays.asList(ids));

        return R.ok();
    }

}
