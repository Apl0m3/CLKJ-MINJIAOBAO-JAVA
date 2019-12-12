package com.lingkj.project.manage.usertransaction.controller;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.transaction.entity.TransactionServiceApplication;
import com.lingkj.project.transaction.service.TransactionServiceApplicationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;



/**
 * 售后  申请表
 *
 * @author chenyongsong
 * @date 2019-10-21 09:56:52
 */
@RestController
@RequestMapping("manage/transactionserviceapplication")
public class TransactionServiceApplicationController {
    @Autowired
    private TransactionServiceApplicationService transactionServiceApplicationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("usertransaction:transactionserviceapplication:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = transactionServiceApplicationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("usertransaction:transactionserviceapplication:info")
    public R info(@PathVariable("id") Long id){
			TransactionServiceApplication transactionServiceApplication = transactionServiceApplicationService.getById(id);

        return R.ok().put("transactionServiceApplication", transactionServiceApplication);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("usertransaction:transactionserviceapplication:save")
    public R save(@RequestBody TransactionServiceApplication transactionServiceApplication){
			transactionServiceApplicationService.save(transactionServiceApplication);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("usertransaction:transactionserviceapplication:update")
    public R update(@RequestBody TransactionServiceApplication transactionServiceApplication, HttpServletRequest request){
			transactionServiceApplicationService.updateInfo(transactionServiceApplication,request);


        return R.ok();
    }

//    /**
//     * 删除
//     */
//    @RequestMapping("/delete")
//    @RequiresPermissions("usertransaction:transactionserviceapplication:delete")
//    public R delete(@RequestBody Long id){
//			transactionServiceApplicationService.removById(id);
//
//        return R.ok();
//    }

}
