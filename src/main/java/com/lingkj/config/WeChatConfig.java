package com.lingkj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */

@Component
public class WeChatConfig {
    /**
     * 公众号AppId
     */
    public static final String APP_ID = "wx36d4881a53c8e9da";

    /**
     * 公众号AppSecret
     */
    public static final String APP_SECRET = "891d59c217c812083b97d1cc06b31fc6";

    /**
     * 微信支付商户号
     */
    public static final String MCH_ID = "1372632802";

    /**
     * 微信支付API秘钥
     */
    public static final String KEY = "cHTCK9jLcU0ga5itTravbu7fZsTQx1Ix";

    /**
     * 微信支付api证书路径
     */
    public static final String CERT_PATH = "***/apiclient_cert.p12";

    /**
     * 微信统一下单url
     */
    public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 微信申请退款url
     */
    public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    /**
     * 微信支付通知url
     */
    public static final String NOTIFY_URL ="/app/wxpayNotify.do";

    public static final String NOTIFY_URL_EPUER ="/app/epuer/wxpayNotify.do";

    /**
     * 微信交易类型:公众号支付
     */
    public static final String TRADE_TYPE_JSAPI = "JSAPI";

    /**
     * 微信交易类型:原生扫码支付
     */
    public static final String TRADE_TYPE_NATIVE = "NATIVE";

    /**
     * 微信甲乙类型:APP支付
     */
    public static final String TRADE_TYPE_APP = "APP";
    /**
     * 登录授权回调
     */
    public static final String REDIRECT_URI =  "/app/updataMuseOpenId.do";
}
