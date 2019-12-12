package com.lingkj.project.api.user.controller;


import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.user.dto.UserAccountLogaAndTransactionIdDto;
import com.lingkj.project.user.entity.UserAccount;
import com.lingkj.project.user.service.UserAccountLogService;
import com.lingkj.project.user.service.UserAccountService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "用户佣金接口")
@RestController
@RequestMapping("/api/user/account")
public class ApiUserAccountController {
    @Autowired
    private UserAccountLogService userAccountLogService;
    @Autowired
    private UserAccountService userAccountService;


    /**
     * 用户金额
     * @param userId
     * @return
     */
    @GetMapping("/userAccount")
    @Login
    public R getUserAccount(@RequestAttribute("userId") Long userId) {
        UserAccount userAccount = userAccountService.getUserAccountOne(userId);
        return R.ok().put("userAccount", userAccount);
    }

    /**
     * 用户佣金明细
     * @param page
     * @param userId
     * @return
     */
    @PostMapping("/userAccountLog")
    @Login
    public R getUserAccountLog(@RequestBody Page page, @RequestAttribute("userId") Long userId) {
        Integer start = page.getLimit();
        Integer end = page.getPageSize();
        List<UserAccountLogaAndTransactionIdDto> userAccountLogList = userAccountLogService.getUserAccountLogList(userId, start, end);
        Integer userAccountLogCount = userAccountLogService.getUserAccountLogCount(userId);
        PageUtils pageUtils = new PageUtils(userAccountLogList, userAccountLogCount, page.getPageSize(), page.getPage());
        return R.ok().put("page", pageUtils);
    }
}
