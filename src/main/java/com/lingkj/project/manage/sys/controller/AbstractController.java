package com.lingkj.project.manage.sys.controller;

import com.lingkj.common.utils.i18n.MyLocaleResolver;
import com.lingkj.project.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * Controller公共组件
 *
 * @author admin
 * @date 2016年11月9日 下午9:42:26
 */
public abstract class AbstractController {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private MessageSource messageSource;

    /**
     * 管理后台获取当前登录信息
     *
     * @return
     */
    protected SysUserEntity getSysUser() {
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getSysUserId() {
        SysUserEntity user = getSysUser();
        return user.getUserId();
    }

    /**
     *  * @param code ：对应messages配置的key.
     *  * @return
     *  
     */
    protected String getMessage(String code, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(MyLocaleResolver.I18N_LANGUAGE_SESSION);
        return messageSource.getMessage(code, null, null, locale);
    }

    /**
     *  *
     *  * @param code ：对应messages配置的key.
     *  * @param args : 数组参数.
     *  * @param defaultMessage : 没有设置key的时候的默认值.
     *  * @return
     *  
     */
    public String getMessage(String code, Object[] args, String defaultMessage) {
        //这里使用比较方便的方法，不依赖request.
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }

}
