package com.lingkj.common.utils.payment.wxpay;

import com.lingkj.common.exception.RRException;
import com.lingkj.project.transaction.entity.TransactionRecord;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Service("wePay")
public class WeChatPayService {
    /**
     * 微信支付统一下单
     **/
    public Map<String, Object> unifiedOrder(TransactionRecord order, Map<String, Object> map) throws RRException {
        Map<String, Object> resultMap;
        try {
            WxPaySendData paySendData = new WxPaySendData();
            //构建微信支付请求参数集合
            paySendData.setAppId(WeChatConfig.APP_ID);
            paySendData.setBody("购买商品");
            paySendData.setMchId(WeChatConfig.MCH_ID);
            paySendData.setNonceStr(WeChatUtils.getRandomStr(32));
            paySendData.setNotifyUrl(WeChatConfig.NOTIFY_URL);
            paySendData.setDeviceInfo("WEB");
            paySendData.setOutTradeNo(order.getTransactionId());
            BigDecimal order_price = order.getAmount().multiply(new BigDecimal(100));
            paySendData.setTotalFee(order_price.intValue());
            paySendData.setTradeType(WeChatConfig.TRADE_TYPE_JSAPI);
            paySendData.setSpBillCreateIp((String) map.get("remoteIp"));
            paySendData.setOpenId((String) map.get("openId"));
            //将参数拼成map,生产签名
            SortedMap<String, Object> params = buildParamMap(paySendData);
            paySendData.setSign(WeChatUtils.getSign(params));
            //将请求参数对象转换成xml
            String reqXml = WeChatUtils.sendDataToXml(paySendData);
            //发送请求
            HttpURLConnection urlConnection = getHttpURLConnection(reqXml);
            resultMap = WeChatUtils.parseXml(urlConnection.getInputStream());
        } catch (Exception e) {
            throw new RRException("微信支付统一下单异常", e);
        }
        return resultMap;
    }

    private HttpURLConnection getHttpURLConnection(String reqXml) throws IOException {
        byte[] xmlData = reqXml.getBytes("UTF-8");
        URL url = new URL(WeChatConfig.UNIFIED_ORDER_URL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);
        urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
        urlConnection.setRequestProperty("contentType", "UTF-8");
        urlConnection.setRequestProperty("Content_Type", "text/xml");
        urlConnection.setRequestProperty("Content-length", String.valueOf(xmlData.length));
        DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
        outputStream.write(xmlData);
        outputStream.flush();
        outputStream.close();
        return urlConnection;
    }

    /**
     * 支付
     *
     * @param order
     * @param map
     * @return
     * @throws RRException
     */
   /* public Map<String, Object> paySyaoOrder(SynergismAttendOrder order, Map<String, Object> map) throws RRException {
        Map<String, Object> resultMap;
        try {
            WxPaySendData paySendData = new WxPaySendData();
            //构建微信支付请求参数集合
            paySendData.setAppId(WeChatConfig.APP_ID);
            paySendData.setBody("购买互助计划");
            paySendData.setMchId(WeChatConfig.MCH_ID);
            paySendData.setNonceStr(WeChatUtils.getRandomStr(32));
            paySendData.setNotifyUrl(WeChatConfig.NOTIFY_URL);
            paySendData.setDeviceInfo("WEB");
            paySendData.setOutTradeNo(order.getSyaoOrderNum());
            //System.out.println(BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(order.getSyaoOrderPrice())));
            BigDecimal order_price = BigDecimal.valueOf(order.getSyaoOrderPrice()).multiply(new BigDecimal(100));
            paySendData.setTotalFee(order_price.intValue());
            paySendData.setTradeType(WeChatConfig.TRADE_TYPE_JSAPI);
            paySendData.setSpBillCreateIp((String) map.get("remoteIp"));
            paySendData.setOpenId((String) map.get("openId"));
            //将参数拼成map,生产签名
            SortedMap<String, Object> params = buildParamMap(paySendData);
            paySendData.setSign(WeChatUtils.getSign(params));
            //将请求参数对象转换成xml
            String reqXml = WeChatUtils.sendDataToXml(paySendData);
            //发送请求
            byte[] xmlData = reqXml.getBytes("UTF-8");
            URL url = new URL(WeChatConfig.UNIFIED_ORDER_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
//            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            urlConnection.setRequestProperty("contentType", "UTF-8");
            urlConnection.setRequestProperty("Content_Type", "text/xml");
            urlConnection.setRequestProperty("Content-length", String.valueOf(xmlData.length));
            DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
            outputStream.write(xmlData);
            outputStream.flush();
            outputStream.close();
            resultMap = WeChatUtils.parseXml(urlConnection.getInputStream());
            resultMap.put("package", params.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("微信支付统一下单异常:" + e.getMessage(), e);
        }
        return resultMap;
    }*/

    /**
     * 构建统一下单参数map 用于生成签名
     *
     * @param data
     * @return SortedMap<String, Object>
     */
    private static SortedMap<String, Object> buildParamMap(WxPaySendData data) {
        SortedMap<String, Object> paramters = new TreeMap<String, Object>();
        if (null != data) {
            if (StringUtils.isNotEmpty(data.getAppId())) {
                paramters.put("appid", data.getAppId());
            }
            if (StringUtils.isNotEmpty(data.getAttach())) {
                paramters.put("attach", data.getAttach());
            }
            if (StringUtils.isNotEmpty(data.getBody())) {
                paramters.put("body", data.getBody());
            }
            if (StringUtils.isNotEmpty(data.getDetail())) {
                paramters.put("detail", data.getDetail());
            }
            if (StringUtils.isNotEmpty(data.getDeviceInfo())) {
                paramters.put("device_info", data.getDeviceInfo());
            }
            if (StringUtils.isNotEmpty(data.getFeeType())) {
                paramters.put("fee_type", data.getFeeType());
            }
            if (StringUtils.isNotEmpty(data.getGoodsTag())) {
                paramters.put("goods_tag", data.getGoodsTag());
            }
            if (StringUtils.isNotEmpty(data.getLimitPay())) {
                paramters.put("limit_pay", data.getLimitPay());
            }
            if (StringUtils.isNotEmpty(data.getMchId())) {
                paramters.put("mch_id", data.getMchId());
            }
            if (StringUtils.isNotEmpty(data.getNonceStr())) {
                paramters.put("nonce_str", data.getNonceStr());
            }
            if (StringUtils.isNotEmpty(data.getNotifyUrl())) {
                paramters.put("notify_url", data.getNotifyUrl());
            }
            if (StringUtils.isNotEmpty(data.getOpenId())) {
                paramters.put("openid", data.getOpenId());
            }
            if (StringUtils.isNotEmpty(data.getOutTradeNo())) {
                paramters.put("out_trade_no", data.getOutTradeNo());
            }
            if (StringUtils.isNotEmpty(data.getSign())) {
                paramters.put("sign", data.getSign());
            }
            if (StringUtils.isNotEmpty(data.getSpBillCreateIp())) {
                paramters.put("spbill_create_ip", data.getSpBillCreateIp());
            }
            if (StringUtils.isNotEmpty(data.getTimeStart())) {
                paramters.put("time_start", data.getTimeStart());
            }
            if (StringUtils.isNotEmpty(data.getTimeExpire())) {
                paramters.put("time_expire", data.getTimeExpire());
            }
            if (StringUtils.isNotEmpty(data.getProductId())) {
                paramters.put("product_id", data.getProductId());
            }
            if (data.getTotalFee().compareTo(0) > 0) {
                paramters.put("total_fee", data.getTotalFee());
            }
            if (StringUtils.isNotEmpty(data.getTradeType())) {
                paramters.put("trade_type", data.getTradeType());
            }
            //申请退款参数
            if (StringUtils.isNotEmpty(data.getTransactionId())) {
                paramters.put("transaction_id", data.getTransactionId());
            }
            if (StringUtils.isNotEmpty(data.getOutRefundNo())) {
                paramters.put("out_refund_no", data.getOutRefundNo());
            }
            if (StringUtils.isNotEmpty(data.getOpUserId())) {
                paramters.put("op_user_id", data.getOpUserId());
            }
            if (StringUtils.isNotEmpty(data.getRefundFeeType())) {
                paramters.put("refund_fee_type", data.getRefundFeeType());
            }
            if (null != data.getRefundFee() && data.getRefundFee() > 0) {
                paramters.put("refund_fee", data.getRefundFee());
            }
        }
        return paramters;
    }

    public static void main(String[] args) {
        Map<String, Object> resultMap;
        //  28	32	4	101		2018-04-02 13:30:07	0	S17069945790362101	2
        try {//获取请求Ip地址=14.116.137.167---用户标识openId=oxvFav4oW-pCjaWz5VgCfKdnjeY4
            WxPaySendData paySendData = new WxPaySendData();
            //构建微信支付请求参数集合
            paySendData.setAppId(WeChatConfig.APP_ID);
            paySendData.setAttach("微信订单支付:测试");
            paySendData.setBody("商品描述");
            paySendData.setMchId(WeChatConfig.MCH_ID);
            paySendData.setNonceStr(WeChatUtils.getRandomStr(32));
            paySendData.setNotifyUrl(WeChatConfig.NOTIFY_URL);
            paySendData.setDeviceInfo("WEB");
            paySendData.setOutTradeNo("S17069945790362101");

            paySendData.setTotalFee(1);
            paySendData.setTradeType(WeChatConfig.TRADE_TYPE_JSAPI);
            paySendData.setSpBillCreateIp("14.116.137.167");
            paySendData.setOpenId("oxvFav4oW-pCjaWz5VgCfKdnjeY4");
            //将参数拼成map,生产签名
            SortedMap<String, Object> params = buildParamMap(paySendData);
            paySendData.setSign(WeChatUtils.getSign(params));
            //将请求参数对象转换成xml
            String reqXml = WeChatUtils.sendDataToXml(paySendData);
            //发送请求
            byte[] xmlData = reqXml.getBytes("UTF-8");
            URL url = new URL(WeChatConfig.UNIFIED_ORDER_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
//            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            urlConnection.setRequestProperty("contentType", "UTF-8");
            urlConnection.setRequestProperty("Content_Type", "text/xml");
            urlConnection.setRequestProperty("Content-length", String.valueOf(xmlData.length));
         /*   DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
//            xmlData = new String(xmlData.getBytes("UTF-8"), "ISO-8859-1");
            System.out.println(xmlData);
            outputStream.write(xmlData);
            outputStream.flush();
            outputStream.close();*/
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(reqXml.getBytes("UTF-8"));
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //返回打开连接读取的输入流，输入流转化为StringBuffer类型，这一套流程要记住，常用
           /* BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = null;
            StringBuffer stringBuffer = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                //转化为UTF-8的编码格式
                line = new String(line.getBytes("UTF-8"));
                stringBuffer.append(line);
            }
            System.out.println("Get请求返回的数据");
            System.out.println(stringBuffer.toString());*/
            resultMap = WeChatUtils.parseXml(urlConnection.getInputStream());
        } catch (Exception e) {
//            throw new RRException("微信支付统一下单异常", e);
        }
    }
}
