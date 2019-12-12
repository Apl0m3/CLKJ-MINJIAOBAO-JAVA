package com.lingkj.project.manage.operation.controller;

import com.lingkj.common.utils.R;
import com.lingkj.project.operation.service.SalesCountService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author XXX <XXX@163.com>
 * @version V1.0.0
 * @description
 * @date 2019/11/2 15:01
 */

@RestController
@RequestMapping("manage/operate/sales")
public class SalesCountController {

    @Autowired
    private SalesCountService salesCountService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("manage:sales:list,")
    public R list(@RequestParam Map<String, Object> params){
        Map<String,Object> map = salesCountService.queryPage(params);
        return R.ok().put("page", map);
    }
}
