package com.lingkj.common.authentication.interceptor;


import com.lingkj.common.validator.Assert;
import com.lingkj.project.user.entity.UserToken;
import com.lingkj.project.user.service.UserTokenService;
import io.jsonwebtoken.Claims;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.jwt.JwtUtils;
import com.lingkj.common.authentication.annotation.Login;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限(Token)验证
 *
 * @author admin
 * @date 2017-03-23 15:38
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserTokenService userTokenService;

    public static final String USER_KEY = "userId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Login annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
        } else {
            return true;
        }

        if (annotation == null) {
            return true;
        }
        if (annotation.required()) {
            //获取用户凭证
            String token = request.getHeader(jwtUtils.getHeader());
            if (StringUtils.isBlank(token)) {
                token = request.getParameter(jwtUtils.getHeader());
            }

            //凭证为空
            if (StringUtils.isBlank(token)) {
                response.setHeader("Access-Control-Allow-Origin", "*");
                throw new RRException("未登录,请登录或未绑定，请绑定账户", HttpStatus.UNAUTHORIZED.value());
            }

            UserToken userToken = userTokenService.queryByToken(token);
            if(userToken==null){
                response.setHeader("Access-Control-Allow-Origin", "*");
                throw new RRException("未登录,请登录或未绑定，请绑定账户", HttpStatus.UNAUTHORIZED.value());
            }

            Claims claims = jwtUtils.getClaimByToken(token);
            if (claims == null || jwtUtils.isTokenExpired(claims.getExpiration())) {
                response.setHeader("Access-Control-Allow-Origin", "*");
                throw new RRException("登录失效，请重新登录", HttpStatus.UNAUTHORIZED.value());
            }

            //设置userId到request里，后续根据userId，获取用户信息
            request.setAttribute(USER_KEY, Long.parseLong(claims.getSubject()));
        } else {
            //获取用户凭证
            String token = request.getHeader(jwtUtils.getHeader());
            if (StringUtils.isBlank(token)) {
                token = request.getParameter(jwtUtils.getHeader());
            }
            Long userId = null;
            if (StringUtils.isNotBlank(token) && !token.equals("null")) {
                Claims claims = jwtUtils.getClaimByToken(token);
                if (claims != null && !jwtUtils.isTokenExpired(claims.getExpiration())) {
                    userId = Long.parseLong(claims.getSubject());
                }
            }
            //设置userId到request里，后续根据userId，获取用户信息
            request.setAttribute(USER_KEY, userId);
        }
        return true;
    }
}
