package com.lingkj.common.utils;

import java.util.UUID;

/**
 * 生成交易id
 *
 * @author clkj
 * @date 09/05/2017
 */
public class TransIdGenerate {


    public static final String orderPrefix = "70";
    public static final String payOrderPrefix = "7910";
    //退款
    public static final String refundIdPrefix = "RF";

    /**
     * 生成silot系统交易唯一ID
     *
     * @return string
     */
    public static String generateTransId(String pre) {
//        String timeStr = String.valueOf(System.currentTimeMillis()).substring(1);
        String ranNumStr = String.valueOf(System.nanoTime()).substring(6, 10);
        String uuid = getOrderIdByUUId();
        return pre + ranNumStr  + uuid;
    }
    /**
     * 生成pay交易唯一ID
     *
     * @return string
     */
    public static String generateTransIdPay(String pre) {
//        String timeStr = String.valueOf(System.currentTimeMillis()).substring(1);
        String ranNumStr = String.valueOf(System.nanoTime()).substring(8, 10);
        String uuid = getOrderIdByUUId().substring(2, 8);
        return pre + ranNumStr  + uuid;
    }

    public static void main(String[] args) {
        System.out.println(generateTransIdPay("3910"));
    }

    /**
     * 手机号账号
     *
     * @param str
     * @return
     */
    public static String generatePhoneByUUId(String str) {
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        //         0 代表前面补充0
        //         4 代表长度为4
        //         d 代表参数为正数型
        return str + String.format("%010d", hashCodeV);
    }


    public static String getOrderIdByUUId() {
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        //有可能是负数
        if (hashCodeV < 0) {
            hashCodeV = -hashCodeV;
        }
        //         0 代表前面补充0     
        //         4 代表长度为4     
        //         d 代表参数为正数型
        return String.format("%010d", hashCodeV);
    }


}
