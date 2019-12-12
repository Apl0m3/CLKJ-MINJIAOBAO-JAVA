package com.lingkj.project.api.transaction.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingkj.common.utils.R;
import com.lingkj.common.utils.TransIdGenerate;
import com.lingkj.common.utils.payment.visa.VisaUtils;
import com.lingkj.config.AliPayConfig;
import com.lingkj.project.operation.entity.OperatePaymentMethod;
import com.lingkj.project.operation.service.PaymentMethodService;
import com.lingkj.project.transaction.entity.TransactionRecord;
import com.lingkj.project.transaction.service.TransactionRecordService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/api/pay")
public class ApiPayController {
    private Log log = LogFactory.getLog(this.getClass());

    private static final String ALIPAY_NOTIFY_URL = "/api/callback/aliCallBack";
    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private TransactionRecordService transactionRecordService;
    /**
     * web端-支付宝支付
     *
     * @param money
     * @param no
     * @param request
     * @param response
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping(value = "/toAliPay")
    @ResponseBody
    public String toAliPay(BigDecimal money, String no, HttpServletRequest request, HttpServletResponse response)
            throws AlipayApiException {
        // 支付宝网关地址
        String gateWay = AliPayConfig.URL;

        // 服务器异步通知页面路径
        String notifyUrl = request.getScheme() + "://"
                + request.getHeader("host") + request.getContextPath() + ALIPAY_NOTIFY_URL;
        // 页面跳转同步通知页面路径
        String returnUrl = request.getScheme() + "://"
                + request.getHeader("host") + request.getContextPath() + "";
        // 付款金额
        String totalAmount = money.toString();

        // 订单名称
        String subject = "建材在线充值";
        OperatePaymentMethod operatePaymentMethod = paymentMethodService.selectByType(OperatePaymentMethod.paymentType_alipay);
        AlipayClient alipayClient = new DefaultAlipayClient(gateWay, operatePaymentMethod.getAppId(),
                operatePaymentMethod.getPrivateKey(),
                "json", AliPayConfig.CHARSET, operatePaymentMethod.getPublicKey(),
                AliPayConfig.SIGNTYPE);
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(notifyUrl);
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + no + "\","
                + "\"total_amount\":\"" + totalAmount + "\","
                + "\"subject\":\"" + subject + "\","
                //+ "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        return alipayClient.pageExecute(alipayRequest).getBody();
    }
    /**
     * visa支付配置
     */
    @GetMapping(value = "/getVisaConfig")
    @ResponseBody
    public R getVisaConfig(String orderNo) throws Exception {
        JSONObject jsonObject=transactionRecordService.getVisaConfig(orderNo);
        return R.ok().put("map",jsonObject);
    }




}
