package com.lingkj.project.manage.sys.controller;

import com.lingkj.common.utils.R;
import com.lingkj.common.utils.RedisUtils;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.project.sys.entity.SysUserEntity;
import com.lingkj.project.sys.form.SysLoginForm;
import com.lingkj.project.sys.service.SysCaptchaService;
import com.lingkj.project.sys.service.SysUserService;
import com.lingkj.project.sys.service.SysUserTokenService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * 登录相关
 *
 * @author admin
 * @date 2016年11月10日 下午1:15:31
 */
@RestController
public class SysLoginController extends AbstractController {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;
    @Autowired
    private SysCaptchaService sysCaptchaService;


    /**
     * 验证码
     */
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, String uuid,HttpServletRequest request) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //获取图片验证码
        BufferedImage image = sysCaptchaService.getCaptcha(uuid,request);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 登录
     */
    @PostMapping("/sys/login")
    public Map<String, Object> login(@RequestBody SysLoginForm form, HttpServletRequest request) throws IOException {
        boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
        if (!captcha) {
            return R.error(getMessage("sys.user.validate.captcha",request));
        }

        //用户信息
        SysUserEntity user = sysUserService.queryByUserName(form.getUsername());

        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
            return R.error(getMessage("sys.user.validate.account",request));
        }

        //账号锁定
        if (user.getStatus() == 0) {
            return R.error(getMessage("sys.user.validate.status",request));
        }

        //生成token，并保存到数据库
        R r = sysUserTokenService.createToken(user.getUserId());
        r.put("locale", user.getLocale());
        return r;
    }


    /**
     * 退出
     */
    @PostMapping("/sys/logout")
    public R logout() {
        sysUserTokenService.logout(getSysUserId());
        return R.ok();
    }

    @GetMapping("/sys/stop")
    public R stop(String pwd) {
        redisUtils.stop(pwd);
        return R.ok();
    }

}
