package com.lingkj.common.utils.payment.wxpay;


import com.lingkj.config.SystemParams;

/**
 * @author Administrator
 */
public class WeChatConfig {
    /**
     * 公众号AppId
     */
    public static final String APP_ID = "wxb870abc79c7e09ad";

    /**
     * 公众号AppSecret
     */
    public static final String APP_SECRET = "cd46d02372819e8023707763e9209ffe";

    /**
     * 微信支付商户号
     */
    public static final String MCH_ID = "1543310501";

    /**
     * 微信支付API秘钥
     */
    public static final String KEY = "G7Z7D0cRNcqfWAOtycVn8YIgLw0lZx6T";

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
    public static final String NOTIFY_URL =SystemParams.webUrl +"/api/callback/wxPayAppNotify";


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
    public static final String REDIRECT_URI = SystemParams.webUrl + "/api/user/updateMuseOpenId";
}
