package com.lingkj.project.api.user.controller;

import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.common.validator.Assert;
import com.lingkj.project.manage.sys.controller.AbstractController;
import com.lingkj.project.message.dto.MessageRespDto;
import com.lingkj.project.message.service.MessageService;
import com.lingkj.project.sys.form.SysLoginForm;
import com.lingkj.project.sys.service.SysCaptchaService;
import com.lingkj.project.user.dto.UserRespDto;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.service.AdminUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class ApiUserLoginController extends AbstractController {

    @Autowired
    private SysCaptchaService sysCaptchaService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private MessageService messageService;


    /**
     * 登录
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody SysLoginForm form, HttpServletRequest request) throws IOException {

        Assert.isBlank(form.getUsername(), getMessage("api.user.validate.email.null", request), 400);
        Assert.isBlank(form.getPassword(), getMessage("api.user.validate.password.null", request), 400);
        Assert.isBlank(form.getCaptcha(), getMessage("api.user.validate.captcha.null", request), 400);
        if (StringUtils.isNotBlank(form.getCaptcha())) {
            boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
            if (!captcha) {
                return R.error(getMessage("sys.user.validate.captcha", request));
            }
        }

        //用户信息
        User user = adminUserService.queryByMail(form.getUsername());

        //账号不存在、密码错误
        String anObject = DigestUtils.sha256Hex(form.getPassword());
        if (user == null || !user.getPassword().equals(anObject)) {
            return R.error(getMessage("sys.user.validate.account", request));
        }

        //账号锁定
        if (user.getStatus() == 1) {
            return R.error(getMessage("sys.user.validate.status", request));
        }
        UserRespDto userRespDto = adminUserService.queryPersonInfo(user.getId());
        //生成token，并保存到数据库
        Map<String, Object> loginMap = adminUserService.getLoginMap(user);
        MessageRespDto dto = messageService.queryNewMessage(user.getId());
        if (dto != null) {
            loginMap.put("sysMessage", dto);
        }
        loginMap.put("user", userRespDto);
        loginMap.put("code", 200);
        return loginMap;

    }

    /**
     * 退出
     */
    @PostMapping("/logout")
    @Login
    public R logout(@RequestAttribute("userId") long userId) {
        adminUserService.saveMuseOpenId(userId);
        return R.ok();
    }


}
