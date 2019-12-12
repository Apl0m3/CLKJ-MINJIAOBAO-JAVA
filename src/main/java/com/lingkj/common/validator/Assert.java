package com.lingkj.common.validator;

import com.lingkj.common.exception.RRException;
import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 *
 * @author admin
 * @date 2017-03-23 15:50
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new RRException(message);
        }
    }

    public static void isBlank(String str, String message, Integer code) {
        if (StringUtils.isBlank(str)) {
            throw new RRException(message,code);
        }
    }

    public static void isNull(Object object, String message, Integer code) {
        if (object == null) {
            throw new RRException(message, code);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RRException(message);
        }
    }
    public static void isNotNull(Object object, String message, Integer code) {
        if (object != null) {
            throw new RRException(message,code);
        }
    }
    public static void isNotNull(Object object, String message) {
        if (object != null) {
            throw new RRException(message);
        }
    }
}
