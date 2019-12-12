package com.lingkj.project.transaction.dto;

import com.lingkj.common.bean.entity.Page;
import lombok.Data;

/**
 * TransactionReqDto
 *
 * @author chen yongsong
 * @className TransactionReqDto
 * @date 2019/10/12 10:16
 */
@Data
public class TransactionReqDto {

    /**
     * 用户手机号
     */

    private String phone;
    /**
     * 设计师邮箱
     */
    private String designName;
    /**
     * 供应商邮箱
     */
    private String supplyName;
    /**
     * 订单号
     */

    private String transactionId;
    /**
     * 时间段
     */

    private String startTime;
    private String endTime;
    /**
     * 订单状态
     */

    private String status;
    /**
     * 订单类型
     *
     */
    private String recordType;

    /**
     * 支付方式
     */

    private String paymentMethodId;
    /**
     * 物流方式
     */
    private String deliveryMethodId;

}
