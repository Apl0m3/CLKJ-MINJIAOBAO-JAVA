package com.lingkj.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * TransactionStatus
 *
 * @author chen yongsong
 * @className TransactionStatus
 * @date 2019/7/8 11:00
 */

public enum TransactionStatusEnum {
    /**
     * 订单状态
     */
    pending(0, "未支付"),
    cancelled(1, "已取消订单"),
    paid(2, "已支付"),
    confirmedPaid(3, "确认收款"),


    deliveryDesigner(4, "派送设计师"),
    reviewManuscript(5, "平台审核稿件"),
    designatedSupplier(6, "指定供应商"),
    ship(7, "发货"),
    confirmReceipt(8, "确认收货/待评价"),
    reviewed(9, "已评价"),
    applyForAfterSale(10, "申请售后"),

    /**
     * 设计师 子订单状态
     */
    design_delivered(40001, "等待设计师接单"),
    design_reject(40002, "设计师拒绝订单"),
    design_agree(40003, "设计师接收订单 设计中"),
    design_complete(40004, "上传稿件 待用户确定"),
    design_determined(40005, "上传稿件 用户已确定"),

    /**
     * 稿件审核子状态
     */
    manuscript_review(50001,"稿件审核中 用户已确定稿件"),
    manuscript_review_failure(50002,"稿件审核失败,用户重新上传"),
    manuscript_review_success(50003,"稿件审核成功,待分配设计师"),
    /**
     * 派送供应商 子订单状态
     */
    supplier_delivered(60001, "等待供应商接单"),
    supplier_reject(60002, "供应商拒绝订单"),
    supplier_agree(60003, "供应商接收订单"),


    /**
     * 申请售后 子状态
     */
    after_sales_application(100001,"申请中"),
    after_sales_application_failed(100002,"申请失败"),
    after_sales_application_success(100003,"申请成功"),
    after_sales_user_shipment(100004,"申请成功 用户发货"),
    after_sales_supplier_confirm_receipt(100005,"申请成功 供应商确认收货"),
    after_sales_supplier_ship(100006,"申请成功 供应商发货"),
    after_sales_user_confirm_receipt(100007,"申请成功 用户确认收货"),

    ;


    private Integer status;
    private String remark;


    TransactionStatusEnum(Integer status, String remark) {
        this.status = status;
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
