package com.lingkj.common.utils.i18n;

/**
 * 国际化工具类
 *
 * @author chen yongsong
 * @className MessageUtils
 * @date 2019/9/15 15:22
 */

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;


@Component
public class MessageUtils {

    @Resource
    private MessageSource messageSource;

    /**
     *  *
     *  * @param code ：对应messages配置的key.
     *  * @param args : 数组参数.
     *  * @return
     *  
     */
    public String getMessage(String code, Object[] args) {
        return getMessage(code, args, "");
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

    /**
     *  * @param code ：对应messages配置的key.
     *  * @return
     *  
     */
    public String getMessage(String code, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(MyLocaleResolver.I18N_LANGUAGE_SESSION);
        return messageSource.getMessage(code, null, null, locale);
    }


}
