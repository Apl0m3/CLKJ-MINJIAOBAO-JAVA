package com.lingkj.common.enums;

/**
 * SendMsgTypeEnum
 *
 * @author chen yongsong
 * @className SendMsgTypeEnum
 * @date 2019/8/15 16:53
 */
public enum SendMsgTypeEnum {
    register(1,"注册验证码"),
    login(2,"验证码登录"),
    forget(3,"忘记密码"),
    update(4,"修改密码"),
    expediting(5,"通知平台发货"),
    platformDelivery(6,"平台物流发货通知"),
    logisticsDelivery(7,"外部物流发货通知"),
    ;
    private Integer type;
    private String remark;

    SendMsgTypeEnum(Integer type, String remark) {
        this.type = type;
        this.remark = remark;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
