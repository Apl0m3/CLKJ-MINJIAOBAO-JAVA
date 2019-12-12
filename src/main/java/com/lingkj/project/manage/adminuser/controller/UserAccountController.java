package com.lingkj.project.manage.adminuser.controller;

import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.manage.sys.controller.AbstractController;
import com.lingkj.project.user.dto.SettlementAmountDto;
import com.lingkj.project.user.entity.UserAccount;
import com.lingkj.project.user.entity.UserBank;
import com.lingkj.project.user.service.UserAccountService;
import com.lingkj.project.user.service.UserBankService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * 查询 用户 获得的佣金
 *
 * @author chenyongsong
 * @date 2019-10-25 14:16:42
 */
@RestController
@RequestMapping("/manage/user/userAccount")
public class UserAccountController extends AbstractController {
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserBankService userBankService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("manage:userAccount:list")
    public R list(Page page,Integer type,String key) {
        PageUtils pageUtils = userAccountService.queryPage(page,type,key);

        return R.ok().put("page", pageUtils);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("manage:userAccount:info")
    public R info(@PathVariable("id") Long id) {
        UserAccount userAccount = userAccountService.getById(id);
        UserBank userBank = userBankService.selectByUserId(userAccount.getUserId());

        return R.ok().put("userAccount", userAccount).put("bank", userBank);
    }


    /**
     * 列表
     */
    @GetMapping("/deliveryList")
    @RequiresPermissions("manage:userAccount:deliveryList")
    public R deliveryList(Page page,Integer type,Long userId) {
        PageUtils pageUtils = userAccountService.queryDeliveryPage(page,type,userId);
        return R.ok().put("page", pageUtils);
    }

    /**
     * 列表
     */
    @GetMapping("/settledDeliveryList")
    @RequiresPermissions("manage:userAccount:settledDeliveryList")
    public R settledDeliveryList(Page page,Integer type,Long userId) {
        PageUtils pageUtils = userAccountService.querySettledDeliveryPage(page,type,userId);
        return R.ok().put("page", pageUtils);
    }


    /**
     * 结算金额
     */
    @PostMapping("/settleAccounts")
    @RequiresPermissions("manage:userAccount:settleAccounts")
    public R settleAccounts(@RequestBody SettlementAmountDto settlementAmountDto, HttpServletRequest request) {
       userAccountService.settleAccounts(settlementAmountDto,getSysUserId(),request);

        return R.ok();
    }

}
