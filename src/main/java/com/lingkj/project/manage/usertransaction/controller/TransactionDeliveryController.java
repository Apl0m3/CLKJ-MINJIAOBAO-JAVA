package com.lingkj.project.manage.usertransaction.controller;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.transaction.entity.TransactionDelivery;
import com.lingkj.project.transaction.service.TransactionDeliveryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 交易平台配送信息
 *
 * @author chenyongsong
 * @date 2019-07-19 11:59:54
 */
@RestController
@RequestMapping("/manage/commodity/transactiondelivery")
public class TransactionDeliveryController {
    @Autowired
    private TransactionDeliveryService transactionDeliveryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("commodity:transactiondelivery:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = transactionDeliveryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("commodity:transactiondelivery:info")
    public R info(@PathVariable("id") Long id){
			TransactionDelivery transactionDelivery = transactionDeliveryService.getById(id);

        return R.ok().put("transactionDelivery", transactionDelivery);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("commodity:transactiondelivery:save")
    public R save(@RequestBody TransactionDelivery transactionDelivery){
			transactionDeliveryService.save(transactionDelivery);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("commodity:transactiondelivery:update")
    public R update(@RequestBody TransactionDelivery transactionDelivery){
			transactionDeliveryService.updateById(transactionDelivery);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("commodity:transactiondelivery:delete")
    public R delete(@RequestBody Long[] ids){
			transactionDeliveryService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
