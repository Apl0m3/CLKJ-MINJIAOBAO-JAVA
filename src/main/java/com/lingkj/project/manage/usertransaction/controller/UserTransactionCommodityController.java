package com.lingkj.project.manage.usertransaction.controller;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.transaction.entity.TransactionCommodity;
import com.lingkj.project.transaction.service.TransactionCommodityService;
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
 * @date 2019-06-26 16:10:25
 */
@RestController
@RequestMapping("/manage/usertransactioncommodity")
public class UserTransactionCommodityController {
    @Autowired
    private TransactionCommodityService transactionCommodityService;

    /**
     * 列表
     */
    @GetMapping("/list")
   @RequiresPermissions("manage:usertransactioncommodity:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = transactionCommodityService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
   @RequiresPermissions("manage:usertransactioncommodity:info")
    public R info(@PathVariable("id") Long id){
			TransactionCommodity userTransactionCommodity = transactionCommodityService.getById(id);

        return R.ok().put("userTransactionCommodity", userTransactionCommodity);
    }

    /**
     * 保存
     */
     @PostMapping("/save")
   @RequiresPermissions("manage:usertransactioncommodity:save")
    public R save(@RequestBody TransactionCommodity userTransactionCommodity){
			transactionCommodityService.save(userTransactionCommodity);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
   @RequiresPermissions("manage:usertransactioncommodity:update")
    public R update(@RequestBody TransactionCommodity userTransactionCommodity){
			transactionCommodityService.updateById(userTransactionCommodity);

        return R.ok();
    }

    /**
     * 删除
     */
     @PostMapping("/delete")
   @RequiresPermissions("manage:usertransactioncommodity:delete")
    public R delete(@RequestBody Long[] ids){
			transactionCommodityService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
