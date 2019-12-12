package com.lingkj.common.utils.payment.wxpay;

import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.MD5Tools;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 创建统一订单
 *
 * @author admin
 */
public class CreateOrder {

//	public static final String appId = "wx99c469ab4eb247b2";
//
//	public static final String mch_id = "1335728401";
//
//	public static final String shopKey = "z19Uau72wqyq5QXVtEgeE6ljdBMy2FwB"; // 商户号API密钥
    // 必须替换

    /**
     * 生成随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) { // length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * web端退款
     *
     * @param orderNo
     * @param money
     * @return
     * @throws
     */
    public static Map<String, String> weixinWRefund(String orderNo, Integer money) throws RRException {
        String appId = WeChatConfig.APP_ID;
        String mch_id = WeChatConfig.MCH_ID;
        String appKey = WeChatConfig.KEY;
        try {
            WXWebPayConfigImpl config = WXWebPayConfigImpl.getInstance();
            return getStringStringMap(orderNo, money, appId, mch_id, appKey, config);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("退款失败");
        }
    }

    private static Map<String, String> getStringStringMap(String orderNo, Integer money, String appId, String mch_id, String appKey, WXWebPayConfigImpl config) throws Exception {
        WXPayRequest wxPayRequest = new WXPayRequest(config);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        Map<String, String> map = new HashMap<String, String>();
        String idNo = getRandomString(16);
        map.put("appid", appId);
        map.put("mch_id", mch_id);
        map.put("nonce_str", MD5Tools.MD5(idNo).toUpperCase());
        map.put("out_trade_no", orderNo); //商户订单号
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        map.put("out_refund_no", sdf.format(new Date()) + (1 + (int) (Math.random() * 10000))); //退款订单号
        map.put("refund_fee", money.toString()); //退款金额
        map.put("total_fee", String.valueOf(Double.valueOf(money).intValue())); //订单金额
        map.put("sign", WXPayUtil.generateSignature(map, appKey)); //签名
        //验证签名是否正确
        System.out.println("签名是否正确-->" + WXPayUtil.isSignatureValid(map, appKey));

        String xml = WXPayUtil.generateSignedXml(map, appKey);

        System.out.println(xml);

        String result = wxPayRequest.requestWithCert("/secapi/pay/refund", uuid, xml, 10000, 10000, true);

        System.out.println(result);

        Map<String, String> xmlToMap = WXPayUtil.xmlToMap(result);

        return xmlToMap;
    }


    /**
     * 生成订单xml
     *
     * @param parameters
     * @return
     */
    private static String orderXml(Map<String, String> parameters) {

        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        sb.append("<appid>").append(parameters.get("appid")).append("</appid>");
        sb.append("<mch_id>").append(parameters.get("mch_id")).append("</mch_id>"); // 商户号
        sb.append("<nonce_str>").append(parameters.get("nonce_str")).append("</nonce_str>"); // 随机字符串
        sb.append("<out_trade_no>").append(parameters.get("out_trade_no")).append("</out_trade_no>"); // 回调
        sb.append("<out_refund_no>").append(parameters.get("out_refund_no")).append("</out_refund_no>"); // 订单号
        sb.append("<refund_fee>").append(parameters.get("refund_fee")).append("</refund_fee>");
        sb.append("<total_fee>").append(parameters.get("total_fee")).append("</total_fee>");
        sb.append("<transaction_id>").append(parameters.get("transaction_id")).append("</transaction_id>");
        // 加密签名
        sb.append("<sign>").append(parameters.get("sign")).append("</sign>"); // sign签名
        sb.append("</xml>");

        return sb.toString();
    }

    /***
     * 做ASCII码从小到大排序（字典序）
     *
     * @param parameters
     * @param key
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String createSign(SortedMap<Object, Object> parameters,
                                    String key) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);
        System.out.println("签名字符串-->" + sb.toString());
        return MD5Tools.MD5(sb.toString()).toUpperCase();
    }

    /**
     * 解析返回的xml字符串
     *
     * @param protocolXML
     * @return
     */
    public static SortedMap<Object, Object> parse(String protocolXML) {

        SortedMap<Object, Object> map = new TreeMap<Object, Object>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(
                    protocolXML)));

            Element root = doc.getDocumentElement();
            NodeList books = root.getChildNodes();
            if (books != null) {
                for (int i = 0; i < books.getLength(); i++) {
                    Node book = books.item(i);
                    map.put(book.getNodeName(), book.getFirstChild()
                            .getNodeValue());
                }
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
