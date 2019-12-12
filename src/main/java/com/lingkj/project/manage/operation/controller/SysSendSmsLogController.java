package com.lingkj.project.manage.operation.controller;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.message.entity.MessageEmailCodeLog;
import com.lingkj.project.message.service.MessageSmsLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 短信记录表
 *
 * @author chenyongsong
 *
 * @date 2019-06-27 10:02:37
 */
@RestController
@RequestMapping("/manage/syssendsmslog")
public class SysSendSmsLogController {
    @Autowired
    private MessageSmsLogService messageSmsLogService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("manage:syssendsmslog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = messageSmsLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("manage:syssendsmslog:info")
    public R info(@PathVariable("id") Long id){
			MessageEmailCodeLog sysSendSmsLog = messageSmsLogService.getById(id) ;

        return R.ok().put("sysSendSmsLog", sysSendSmsLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("manage:syssendsmslog:save")
    public R save(@RequestBody MessageEmailCodeLog sysSendSmsLog){
			messageSmsLogService.save(sysSendSmsLog) ;

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("manage:syssendsmslog:update")
    public R update(@RequestBody MessageEmailCodeLog sysSendSmsLog){
			messageSmsLogService.updateById(sysSendSmsLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("manage:syssendsmslog:delete")
    public R delete(@RequestBody Long[] ids){
			messageSmsLogService.removeByIds(Arrays.asList(ids)) ;

        return R.ok();
    }

}
