package com.lingkj.project.api.user.controller;


import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.utils.R;
import com.lingkj.project.user.service.UserShareLogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
@Api(value = "用户分享网站")
@RestController
@RequestMapping("/api/user/shareLog")
public class ApiUserShareLogController {

    @Autowired
    private UserShareLogService userShareLogService;
    /**
     * 分享网站
     *
     * @param url
     * @param userId
     * @return
     */
    @Login
    @GetMapping("/save")
    public R share(@RequestAttribute("userId") Long userId, String url, HttpServletRequest request){
        userShareLogService.userShare(userId,url,request);
        return R.ok();
    }
}
