package com.lingkj.common.enums;

public enum OperateRulesEnum {
    callback(1, "返现规则"),
    pay(2, "支付抵扣"),
    payMax(3, "最高抵扣"),
    invite(4, "邀请使用返现积分"),

    ;
    private Integer type;
    private String remark;

    OperateRulesEnum(Integer type, String remark) {
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
