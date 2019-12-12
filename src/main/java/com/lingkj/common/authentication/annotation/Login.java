package com.lingkj.common.authentication.annotation;

import java.lang.annotation.*;

/**
 * app登录效验
 *
 * @author admin
 * @date 2017/9/23 14:30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
    boolean required() default true;
}
