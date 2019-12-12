package com.lingkj.common.enums;

/**
 * ErrorMessage
 *
 * @author chen yongsong
 * @className ErrorMessage
 * @date 2019/7/4 14:49
 */
public enum ErrorMessage {
    /**
     * 用户模块错误code:10000-20000
     */
    user_exist(10001, "手机号或密码错误"),
    /**
     * 商品模块错误code:20000-30000
     * 商品规格  21000-22000
     * 购物车  22000-23000
     */
    commodity_exist(20001, "此商品不存在"),
    /**
     * 运营模块错误code:30000-40000
     */
    operate_message_exist(30001, "此消息不存在"),
    /**
     * 交易模块错误code:40000-50000
     */
    transaction_message_exist(40001, "此消息不存在"),
    ;
    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ErrorMessage(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
