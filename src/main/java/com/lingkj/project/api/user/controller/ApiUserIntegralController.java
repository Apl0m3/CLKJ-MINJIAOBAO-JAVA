package com.lingkj.project.api.user.controller;


import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.integral.entity.IntegralUser;
import com.lingkj.project.integral.service.UserIntegralLogService;
import com.lingkj.project.integral.service.UserIntegralService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Api(value = "用户积分接口")
@RestController
@RequestMapping("/api/user/integral")
public class ApiUserIntegralController {
    @Autowired
    private UserIntegralService userIntegralService;
    @Autowired
    private UserIntegralLogService userIntegralLogService;

    /**
     * 积分记录明细
     * @param page
     * @param userId
     * @return
     */
    @PostMapping("/integralLog")
    @Login
    public R queryIntegralLog(@RequestBody Page page, @RequestAttribute("userId") Long userId) {
        IntegralUser integralUser = userIntegralService.selectByUserIdForUpdate(userId);
        Map<String, Object> params = new HashMap<>();
        params.put("limit", page.getPageSize());
        params.put("page", page.getPage());
        PageUtils pageUtils = userIntegralLogService.queryIntegralLog(page,userId);
        return R.ok().put("page", pageUtils).put("userTotalIntegral", integralUser.getIntegral());
    }
}
