package com.lingkj.project.api.user.controller;


import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.mail.MailService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.common.utils.RedisUtils;
import com.lingkj.common.utils.UUIDUtil;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.common.validator.Assert;
import com.lingkj.project.message.dto.MessageRespDto;
import com.lingkj.project.message.entity.MessageEmailCodeLog;
import com.lingkj.project.message.service.MessageService;
import com.lingkj.project.message.service.MessageSmsLogService;
import com.lingkj.project.user.dto.UserLogonDto;
import com.lingkj.project.user.dto.UserRespDto;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.service.AdminUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/user/logon")
public class ApiUserLogonController {

    @Autowired
    private MailService mailService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private MessageService messageService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
            private MessageSmsLogService messageSmsLogService;
    @Autowired
    private MessageUtils messageUtils;

    String user="user:";
    String register="register:"; //注册
    String reset="reset:";//忘记
    public static final String mailVerification = "^[a-zA-Z0-9]+([._\\\\\\\\-]*[a-zA-Z0-9])*@([a-zA-Z0-9]+[-a-zA-Z0-9]*[a-zA-Z0-9]+.){1,63}[a-zA-Z0-9]+$";
    public static final String  codeVerification  ="[A-Za-z0-9]{6}";
    public static final String  passwordVerification="^(?![^a-zA-Z]+$)(?!\\D+$).{6,}$";

    //发送邮箱验证码
    @PostMapping("/mail")
    public R mail(@RequestParam("mail") String mail, @RequestParam("type") Integer type, HttpServletRequest request){
        MessageEmailCodeLog messageEmailCodeLog=new MessageEmailCodeLog();
        if(mail==null){
            return R.error(400,messageUtils.getMessage("api.user.validate.email", request));
        }
        if (!mail.matches(mailVerification)) {
            return R.error(400,messageUtils.getMessage("api.user.validate.email.verification", request));
        }
        messageEmailCodeLog.setCreateTime(new Date());
        messageEmailCodeLog.setPhone(mail);
        String codeKey=user+mail;
         String  registerKey=register+mail;
         String  resetKye=reset+mail;
        User user = adminUserService.queryByMail(mail);
         if(type==0){
             Assert.isNotNull(user,messageUtils.getMessage("common.exception.registered", request),400);
             messageEmailCodeLog.setType(1);
             return mailSendCode(mail, codeKey, registerKey,messageEmailCodeLog,request);
         }else {
             Assert.isNull(user,messageUtils.getMessage("common.exception.null", request),400);
             messageEmailCodeLog.setType(3);
             return mailSendCode(mail, codeKey, resetKye,messageEmailCodeLog,request);
         }
    }
    // 邮箱发送验证码
    private R mailSendCode(String mail, String codeKey, String typeKey,MessageEmailCodeLog messageEmailCodeLog,HttpServletRequest request) {
        if(StringUtils.isNotBlank(redisUtils.get(codeKey))){
            return R.error(400,messageUtils.getMessage("api.send.email.captcha", request));
        }
        String uuid = UUIDUtil.uuid().substring(0,6);
        boolean b = mailService.sendHtmlMail(mail, "【全球定制网】", "您的验证码是：" + uuid+"，请不要把验证码泄漏给其他人，此验证码10分钟内输入有效，如非本人请勿操作。");
        messageEmailCodeLog.setCode(uuid);
        if(b){
            redisUtils.set(codeKey,uuid, 60);
            redisUtils.set(typeKey,uuid,600);
            messageEmailCodeLog.setStatus(0);
            messageSmsLogService.save(messageEmailCodeLog);
        }else {
            messageEmailCodeLog.setStatus(1);
            messageSmsLogService.save(messageEmailCodeLog);
            return  R.error(400,messageUtils.getMessage("api.send.email.captcha.error", request));
        }
        return  R.ok();
    }

    /**
     * 邮箱验证码 验证 及用户注册
     */
    @PostMapping("/mail/save")
    public  Map<String, Object> list(@RequestBody UserLogonDto userLogonDto,HttpServletRequest request) {
        Assert.isBlank(userLogonDto.getMail(),messageUtils.getMessage("api.user.validate.email.null", request),400);
        if (!userLogonDto.getMail().matches(mailVerification)) {
            return R.error(400,messageUtils.getMessage("api.user.validate.email.verification", request));
        }
        Assert.isBlank(userLogonDto.getCode(),messageUtils.getMessage("api.user.validate.captcha.null", request),400);
        if(!userLogonDto.getCode().matches(codeVerification)){
            return R.error(400,messageUtils.getMessage("api.user.validate.email.captcha.verification", request));
        }
        Assert.isBlank(userLogonDto.getPassword(),messageUtils.getMessage("api.user.validate.password.null", request),400);
        if(!userLogonDto.getPassword().matches(passwordVerification)){
            return R.error(400,messageUtils.getMessage("api.user.validate.password.verification", request));
        }
        Assert.isBlank(userLogonDto.getConfirmPassword(),messageUtils.getMessage("api.user.validate.confirmPassword.null", request),400);

        if(!userLogonDto.getPassword().equals(userLogonDto.getConfirmPassword())){
            return R.error(400,messageUtils.getMessage("api.user.validate.password.confirmPassword", request));
        }
        Assert.isBlank(userLogonDto.getName(),messageUtils.getMessage("api.user.name.null", request),400);
        User user = adminUserService.queryByMail(userLogonDto.getMail());
        Assert.isNotNull(user,messageUtils.getMessage("common.exception.registered", request),400);
        String s = redisUtils.get(register + userLogonDto.getMail());
        Assert.isBlank(s,messageUtils.getMessage("api.send.email.captcha.Invalid", request),400);
        if(!s.equals(userLogonDto.getCode())){
            return  R.error(400,messageUtils.getMessage("sys.user.validate.captcha", request));
        }

        Map<String, Object> map = adminUserService.saveOrUpdateUser(userLogonDto);
//        UserRespDto userRespDto = adminUserService.queryPersonInfo(use.getId());
//        //生成token，并保存到数据库
//        Map<String, Object> map = adminUserService.getLoginMap(use);
//        map.put("user",userRespDto);
//        map.put("code",200);
        return map;
    }

    /**
     * 邮箱验证码 验证 及用户忘记密码  重置密码
     */
    @PostMapping("/mail/reset")
    public R resetPassword(@RequestBody UserLogonDto userLogonDto,HttpServletRequest request) {
        Assert.isBlank(userLogonDto.getMail(),messageUtils.getMessage("api.user.validate.email.null", request),400);
        if (!userLogonDto.getMail().matches(mailVerification)) {
            return R.error(400,messageUtils.getMessage("api.user.validate.email.verification", request));
        }
        Assert.isBlank(userLogonDto.getCode(),messageUtils.getMessage("api.user.validate.captcha.null", request),400);
        if(!userLogonDto.getCode().matches(codeVerification)){
            return R.error(400,messageUtils.getMessage("api.user.validate.email.captcha.verification", request));
        }
        Assert.isBlank(userLogonDto.getPassword(),messageUtils.getMessage("api.user.validate.password.null", request),400);
        if(!userLogonDto.getPassword().matches(passwordVerification)){
            return R.error(400,messageUtils.getMessage("api.user.validate.password.verification", request));
        }
        User user = adminUserService.queryByMail(userLogonDto.getMail());
        Assert.isNull(user,messageUtils.getMessage("common.exception.null", request),400);
        String s = redisUtils.get(reset + userLogonDto.getMail());
        Assert.isBlank(s,messageUtils.getMessage("api.send.email.captcha.Invalid", request),400);
        if(!s.equals(userLogonDto.getCode())){
            return  R.error(400,messageUtils.getMessage("sys.user.validate.captcha", request));
        }
        user.setPassword(DigestUtils.sha256Hex(userLogonDto.getPassword()));
        adminUserService.updateById(user);
        return R.ok();
    }
}
