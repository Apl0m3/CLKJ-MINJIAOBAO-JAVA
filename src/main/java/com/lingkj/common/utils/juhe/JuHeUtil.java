package com.lingkj.common.utils.juhe;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信API服务调用示例代码 － 聚合数据
 * 在线接口文档：http://www.juhe.cn/docs/54
 *
 * @author chen yongsong
 * @className SMSUtil
 * @date 2019/6/27 10:17
 */


public class JuHeUtil {
    /*谢薇: AppKey：6fe4e1df77bd36e440516f5816e1b583
    谢薇: 短信
    谢薇: AppKey：a63dd6c6d0ffe549176cdf36694963b0
    谢薇: 物流
    */
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    //验证身份份证key
    public static final String APPKEY = "******************";
    //短信key
    public static final String APPKEY_msg = "6fe4e1df77bd36e440516f5816e1b583";
    //短信模板
    public static final String TEMPLATE_VERIFYCODE = "176813";

    public static final String MONY_VERIFYCODE = "84710";
    //物流key
    public static final String APPKEY_exp = "a63dd6c6d0ffe549176cdf36694963b0";

    /**
     * 身份证 验证
     *
     * @param user_uname
     * @param anchor_card
     * @return
     */
    public static String info(String user_uname, String anchor_card) {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("realname", user_uname);
        m.put("idcard", anchor_card);
        m.put("key", APPKEY);
        String s;
        try {
            s = net("http://op.juhe.cn/idcard/query", m, "POST");
            return s;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "认证失败";
    }

    //2.发送短信
    public static JSONObject sendMsg(JhTemplateValue jhTemplateValue) {
        String template = null;
        String tpl_value = null;
        switch (jhTemplateValue.getType()) {
            case 1:
            case 2:
            case 3:
            case 4:
                template = "176813";
                tpl_value = "#code#=" + jhTemplateValue.getCode();
                break;
            case 5://通知平台发货
                template = "179857";
                tpl_value = "#username#=" + jhTemplateValue.getUserName();
                break;
            case 6://平台物流发货通知
                template = "179865";
                tpl_value = "#username#=" + jhTemplateValue.getUserName() + "&#company#=" + jhTemplateValue.getName() + "&#ordernumber#=" + jhTemplateValue.getNumber();
                break;
            case 7://外部物流发货通知
                template = "179862";
                tpl_value = "#username#=" + jhTemplateValue.getUserName() + "&#company#=" + jhTemplateValue.getName() + "&#ordernumber#=" + jhTemplateValue.getNumber();
                break;
            default:

        }

        return sendMsg(template, jhTemplateValue.getPhone(), tpl_value);
    }


    private static JSONObject sendMsg(String template, String phone, String tpl_value) {
        String result = null;
        //请求接口地址
        String url = "http://v.juhe.cn/sms/send";
        //请求参数
        Map<String, Object> params = new HashMap<>();
        //接收短信的手机号码
        params.put("mobile", phone);
        //短信模板ID，请参考个人中心短信模板设置
        params.put("tpl_id", template);
        //变量名和变量值对。如果你的变量名或者变量值中带有#&=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，<a href="http://www.juhe.cn/news/index/id/50" target="_blank">详细说明></a>
        params.put("tpl_value", URLEncoder.encode(tpl_value));
        //应用APPKEY(应用详细页查询)
        params.put("key", APPKEY_msg);
        //返回数据的格式,xml或json，默认json
        params.put("dtype", "json");
        try {
            result = net(url, params, "POST");
            JSONObject object = JSONObject.parseObject(result);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 查询物流详情
     *
     * @param com 公司编号
     * @param no  物流号
     * @return
     */
    public static JSONObject getExpDetails(String com, String no) {
        String result = null;
        //请求接口地址
        String url = "http://v.juhe.cn/exp/index";
        //请求参数
        Map<String, Object> params = new HashMap<>();
        //需要查询的快递公司编号
        params.put("com", com);
        //需要查询的订单号
        params.put("no", no);
        //应用APPKEY(应用详细页查询)
        params.put("key", APPKEY_exp);
        //返回数据的格式,xml或json，默认json
        params.put("dtype", "json");

        try {
            result = net(url, params, "POST");
            JSONObject object = JSONObject.parseObject(result);
            if (object.getInteger("error_code") == 0) {

                return object.getJSONObject("result");
            } else {
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 快递公司编号对照表
     *
     * @return
     */
    public static JSONArray getCompany() {
        String result = null;
        //请求接口地址
        String url = "http://v.juhe.cn/exp/com?key=" + APPKEY_exp;
        //请求参数
        Map params = new HashMap();

        try {
            result = net(url, params, "POST");
            JSONObject object = JSONObject.parseObject(result);
            if (object.getInteger("error_code") == 0) {
                return object.getJSONArray("result");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return 网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }


    /**
     * map型转为请求参数型
     *
     * @param data
     * @return
     */
    public static String urlencode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
     /* JSONObject object= sendMsg(TEMPLATE_VERIFYCODE,"18283050411","123456");
        System.out.println(object);*/
//        JSONArray company = getCompany();
//        System.out.println(company);
        /*[{"com":"顺丰","no":"sf"},
        {"com":"申通","no":"sto"},
        {"com":"圆通","no":"yt"},
        {"com":"韵达","no":"yd"},
        {"com":"天天","no":"tt"},
        {"com":"EMS","no":"ems"},
        {"com":"中通","no":"zto"},
        {"com":"汇通","no":"ht"},
        {"com":"全峰","no":"qf"},
        {"com":"德邦","no":"db"},{"com":"国通","no":"gt"},{"com":"如风达","no":"rfd"},{"com":"京东快递","no":"jd"},{"com":"宅急送","no":"zjs"},{"com":"EMS国际","no":"emsg"},{"com":"Fedex国际","no":"fedex"},{"com":"邮政国内（挂号信）","no":"yzgn"},{"com":"UPS国际快递","no":"ups"},{"com":"中铁快运","no":"ztky"},{"com":"佳吉快运","no":"jiaji"},{"com":"速尔快递","no":"suer"},{"com":"信丰物流","no":"xfwl"},{"com":"优速快递","no":"yousu"},{"com":"中邮物流","no":"zhongyou"},{"com":"天地华宇","no":"tdhy"},{"com":"安信达快递","no":"axd"},{"com":"快捷速递","no":"kuaijie"},{"com":"AAE全球专递","no":"aae"},{"com":"DHL","no":"dhl"},{"com":"DPEX国际快递","no":"dpex"},{"com":"D速物流","no":"ds"},{"com":"FEDEX国内快递","no":"fedexcn"},{"com":"OCS","no":"ocs"},{"com":"TNT","no":"tnt"},{"com":"东方快递","no":"coe"},{"com":"传喜物流","no":"cxwl"},{"com":"城市100","no":"cs"},{"com":"城市之星物流","no":"cszx"},{"com":"安捷快递","no":"aj"},{"com":"百福东方","no":"bfdf"},{"com":"程光快递","no":"chengguang"},{"com":"递四方快递","no":"dsf"},{"com":"长通物流","no":"ctwl"},{"com":"飞豹快递","no":"feibao"},{"com":"马来西亚（大包EMS）","no":"malaysiaems"},{"com":"安能快递","no":"ane66"},{"com":"中通快运","no":"ztoky"},{"com":"远成物流","no":"ycgky"},{"com":"远成快运","no":"ycky"},{"com":"邮政快递","no":"youzheng"},{"com":"百世快运","no":"bsky"},{"com":"苏宁快递","no":"suning"},{"com":"安能物流","no":"anneng"},{"com":"九曳","no":"jiuye"}]
         */
//        JSONObject getExpDetails = getExpDetails("sto", "3708270965018");
//        System.out.println(getExpDetails);

    }
}
