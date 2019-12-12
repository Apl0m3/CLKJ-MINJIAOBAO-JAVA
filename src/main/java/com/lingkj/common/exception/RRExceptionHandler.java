package com.lingkj.common.exception;

import com.lingkj.common.utils.R;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.project.manage.sys.controller.AbstractController;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理器
 *
 * @author admin
 * @date 2016年10月27日 下午10:16:19
 */
@RestControllerAdvice
public class RRExceptionHandler extends AbstractController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MessageUtils messageUtils;
    /**
     * 处理自定义异常
     */
    @ExceptionHandler(RRException.class)
    public R handleRRException(RRException e) {
        R r = new R();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());

        return r;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public R handlerNoFoundException(Exception e, HttpServletRequest request) {
        logger.error(e.getMessage(), e);
        return R.error(404, getMessage("common.exception.404",request));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e, HttpServletRequest request) {
        logger.error(e.getMessage(), e);
        if(e.getMessage().contains("user_name")) return R.error(getMessage("common.exception.registered",request));
        else return R.error(getMessage("common.exception.existed",request));
    }

    @ExceptionHandler(AuthorizationException.class)
    public R handleAuthorizationException(AuthorizationException e, HttpServletRequest request) {
        logger.error(e.getMessage(), e);
        return R.error(getMessage("common.exception.authorization",request));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public R handleAuthorizationException(UnauthorizedException e, HttpServletRequest request) {
        logger.error(e.getMessage(), e);
        return R.error(getMessage("common.exception.authorization",request));
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e, HttpServletRequest request) {
        logger.error(e.getMessage(), e);
        return R.error(getMessage("common.exception.common",request));
    }
}
