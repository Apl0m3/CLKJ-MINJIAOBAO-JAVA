package com.lingkj.common.utils;

import com.lingkj.project.operation.entity.OperateRules;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Created by jojo on 2018/11/30
 */
public class AmountUtil {
    public final static BigDecimal HUNDRED = new BigDecimal("100");
    public final static BigDecimal ZERO = new BigDecimal("0");
    public final static Pattern AMOUNT_PATTERN = Pattern.compile("^\\d{1,9}(\\.\\d{1,2})?$");

    /**
     * 把金额元转换成分
     *
     * @param amount
     * @return
     */
    public static Long amountConvert(Object amount) {
        if (amount == null) return null;
        return new BigDecimal(amount.toString()).multiply(HUNDRED).longValue();
    }

    /**
     * 把金额分转换成元
     * 如果金额为null,默认为0
     *
     * @param amount
     * @return
     */
    public static String amountConvertToYuan(Object amount) {
        if (amount == null) amount = 0;
        return new BigDecimal(amount.toString()).divide(HUNDRED, 2, BigDecimal.ROUND_FLOOR).toString();
    }

    /**
     * 把金额分转换成元
     * 如果金额为null,默认为0
     *
     * @param amount
     * @return
     */
    public static BigDecimal amountConvertToYuanDecimal(Object amount) {
        if (amount == null) amount = 0;
        return new BigDecimal(amount.toString()).divide(HUNDRED, 2, BigDecimal.ROUND_FLOOR);
    }

    /**
     * 检查是否是正确的金额（保留小数点两位）
     *
     * @param amount
     * @return
     */
    public static boolean checkAmount(Object amount) {
        if (amount == null) return false;
        return AMOUNT_PATTERN.matcher(amount.toString()).find() && new BigDecimal(amount.toString()).compareTo(ZERO) > 0;
    }

    /**
     * @param amount
     * @return
     */
    public static String amountConvertToYuanNoScale(Object amount) {
        if (amount == null) amount = 0;
        return new BigDecimal(amount.toString()).divide(HUNDRED, BigDecimal.ROUND_FLOOR).toString();
    }

    /**
     * 计算 积分抵扣金额
     *
     * @param integral
     * @param operateRules
     * @return
     */
    public static BigDecimal integralDeductionAmount(Integer integral, OperateRules operateRules) {
        BigDecimal integralBig = new BigDecimal(integral);
        BigDecimal factor = operateRules.getFactor();
        BigDecimal resultBig =operateRules.getResult();
        BigDecimal decimal = integralBig.divide(factor, 2, BigDecimal.ROUND_HALF_DOWN).multiply(resultBig);
        return decimal;
    }

    /**
     * 计算 金额抵扣积分
     *
     * @param amount
     * @param operateRules
     * @return
     */
    public static Long integral(Object amount, OperateRules operateRules) {
        BigDecimal factor =operateRules.getFactor();
        BigDecimal resultBig = operateRules.getResult();
        BigDecimal decimal = new BigDecimal(amount.toString()).divide(factor, 2, BigDecimal.ROUND_HALF_DOWN).multiply(resultBig);
        return decimal.longValue();
    }


    public static void main(String[] args) {
        String num_str = "3424.43";
        System.out.println(checkAmount("0"));
        System.out.println(checkAmount("0.00"));
        System.out.println(checkAmount("342"));
        System.out.println(checkAmount("3424.43"));
    }
}
