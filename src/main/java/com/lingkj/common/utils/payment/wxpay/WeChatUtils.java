package com.lingkj.common.utils.payment.wxpay;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.AuthToken;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class WeChatUtils {

    /**
     * 根据code获取微信授权access_token
     *
     * @param code
     */
    public static AuthToken getTokenByAuthCode(String code) {
        AuthToken authToken;
        StringBuilder json = new StringBuilder();
        try {
            URL url = new URL(WeChatConstant.GET_AUTHTOKEN_URL + "appid=" + WeChatConfig.APP_ID + "&secret=" + WeChatConfig.APP_SECRET + "&code=" + code + "&grant_type=authorization_code");
            getURLRespJson(json, url);
            //将json字符串转成javaBean
            ObjectMapper om = new ObjectMapper();
            authToken = om.readValue(json.toString(), AuthToken.class);
        } catch (Exception e) {
            throw new RRException("微信工具类:根据授权code获取access_token异常", e);
        }
        return authToken;
    }

    /**
     * 获取jsApi调用的时候的appId和appSecret
     *
     * @return
     * @date 2017年6月2日
     * @author ChoxSu
     */
    public static String getJSApiTicket() throws RRException {
        AuthToken authToken = getToken();
        StringBuilder json = new StringBuilder();
        JSONObject jsonObject = null;
        String ticket = "";
        try {
            URL url = new URL(WeChatConstant.jsApiTicket_url + authToken.getAccess_token());
            getURLRespJson(json, url);
            //将json字符串转成javaBean
            jsonObject = JSONObject.parseObject(json.toString());
            ticket = (String) jsonObject.get("ticket");
        } catch (Exception e) {
            throw new RRException("微信工具类:根据授权code获取access_token异常", e);
        }
        return ticket;
    }

    public static void main(String[] args) {
        try {
            WeChatUtils.getJSApiTicket();
        } catch (RRException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取接口访问凭证
     *
     * @return
     */
    public static AuthToken getToken() throws RRException {
        AuthToken authToken;
        StringBuilder json = new StringBuilder();
        try {
            URL url = new URL(WeChatConstant.TOKEN_URL_FX + WeChatConfig.APP_ID + "&secret=" + WeChatConfig.APP_SECRET);
            getURLRespJson(json, url);
            //将json字符串转成javaBean
            ObjectMapper om = new ObjectMapper();
            authToken = om.readValue(json.toString(), AuthToken.class);

        } catch (Exception e) {
            throw new RRException("微信工具类:根据授权code获取access_token异常", e);
        }
        return authToken;
    }

    /**
     * @param sceneStr action_name	二维码类型，QR_SCENE为临时的整型参数值，QR_STR_SCENE为临时的字符串参数值，QR_LIMIT_SCENE为永久的整型参数值，QR_LIMIT_STR_SCENE为永久的字符串参数值
     *                 action_info	二维码详细信息
     *                 scene_id	场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
     *                 scene_str	场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
     * @return
     * @throws IOException
     */
    public static JSONObject qrcode(String sceneStr) throws IOException {
        JSONObject reqJson = new JSONObject();
        JSONObject actionInfo = new JSONObject();
        actionInfo.put("scene", new JSONObject().put("scene_str", sceneStr));
        reqJson.put("action_name", "QR_LIMIT_STR_SCENE");
        reqJson.put("expire_seconds", 24*60*60);
        reqJson.put("action_info", actionInfo);

        JSONObject jsonObject = null;
        StringBuilder json = new StringBuilder();
        AuthToken authToken = getToken();
        URL url = new URL(WeChatConstant.QRCODE_CREATE + authToken.getAccess_token());
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        // 设置是否向connection输出，因为这个是post请求，参数要放在
        // http正文内，因此需要设为true
        urlConnection.setDoOutput(true);
        // Read from the connection. Defaultis true.
        urlConnection.setDoInput(true);
        // 默认是 GET方式
        urlConnection.setRequestMethod("POST");
        // Post 请求不能使用缓存
        urlConnection.setUseCaches(false);

        urlConnection.setRequestProperty("Accept-Charset", "utf-8");
        urlConnection.setRequestProperty("Content-Type", "application/json");

        OutputStream outputStream = urlConnection.getOutputStream();
        OutputStreamWriter outputStreamWriter = null;
        outputStreamWriter = new OutputStreamWriter(outputStream);

        outputStreamWriter.write(reqJson.toJSONString());
        outputStreamWriter.flush();

        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            json.append(inputLine);
        }
        in.close();
        //将json字符串转成javaBean
        jsonObject = JSONObject.parseObject(json.toString());
        return jsonObject;
    }

    private static void getURLRespJson(StringBuilder json, URL url) throws IOException {
        URLConnection uc = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            json.append(inputLine);
        }
        in.close();
    }


    /**
     * 获取微信签名
     *
     * @param map 请求参数集合
     * @return 微信请求签名串
     */
    public static String getSign(SortedMap<String, Object> map) {
        StringBuffer sb = new StringBuffer();
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            //参数中sign、key不参与签名加密
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + WeChatConfig.KEY);
        String sign = MD5.MD5Encode(sb.toString()).toUpperCase();
        return sign;
    }


    /**
     * 解析微信服务器发来的请求
     *
     * @param inputStream
     * @return 微信返回的参数集合
     */
    public static SortedMap<String, Object> parseXml(InputStream inputStream) throws RRException {
        SortedMap<String, Object> map = new TreeMap<String, Object>();
        try {
            //获取request输入流
            SAXReader reader = new SAXReader();
            // 这是优先选择. 如果不允许DTDs (doctypes) ,几乎可以阻止所有的XML实体攻击
            String FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
            reader.setFeature(FEATURE, true);

            FEATURE = "http://xml.org/sax/features/external-general-entities";
            reader.setFeature(FEATURE, false);

            FEATURE = "http://xml.org/sax/features/external-parameter-entities";
            reader.setFeature(FEATURE, false);

            FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
            reader.setFeature(FEATURE, false);

            Document document = reader.read(inputStream);
            //得到xml根元素
            Element root = document.getRootElement();
            //得到根元素所有节点
            List<Element> elementList = root.elements();
            //遍历所有子节点
            for (Element element : elementList) {
                map.put(element.getName(), element.getText());
            }
            //释放资源
            inputStream.close();
        } catch (Exception e) {
            throw new RRException("微信工具类:解析xml异常", e);
        }
        return map;
    }

    /**
     * 扩展xstream,使其支持name带有"_"的节点
     */
    public static XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyReplacer("-_", "_")));

    /**
     * 请求参数转换成xml
     *
     * @param data
     * @return xml字符串
     */
    public static String sendDataToXml(WxPaySendData data) {
        xStream.autodetectAnnotations(true);
        xStream.alias("xml", WxPaySendData.class);
        return xStream.toXML(data);
    }

    /**
     * 获取当前时间戳
     *
     * @return 当前时间戳字符串
     */
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * 获取指定长度的随机字符串
     *
     * @param length
     * @return 随机字符串
     */
    public static String getRandomStr(int length) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
