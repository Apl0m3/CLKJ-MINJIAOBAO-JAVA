package com.lingkj.common.utils.payment.visa;
import com.alibaba.fastjson.JSONObject;
import sis.redsys.api.ApiMacSha256;

import java.io.UnsupportedEncodingException;

public class VisaUtils {
    public static String MerchantCode="336458021";
    public static String Terminal="2";
    public static String key="8TK7Ta8n5j7huazKE9ZXGodaYpRj/dT1";
    public static String UrlOK="https://web.a1publicidad.eu/#/home?name=ok";
    public static String UrlKO="https://web.a1publicidad.eu/#/home?name=ko";
    public static String MerchantURL="https://web.a1publicidad.eu/api/callback/getCallback";


    public static JSONObject getParamsAndSign(String amount,String payOrderNo){
        JSONObject json=new JSONObject();
        ApiMacSha256 apiMacSha256=new ApiMacSha256();
        apiMacSha256.setParameter("Ds_Merchant_MerchantCode",MerchantCode);
        apiMacSha256.setParameter("Ds_Merchant_Terminal",Terminal);
        apiMacSha256.setParameter("Ds_Merchant_TransactionType","0");
        apiMacSha256.setParameter("Ds_Merchant_Amount",amount);
        apiMacSha256.setParameter("Ds_Merchant_Currency","978");
        apiMacSha256.setParameter("DS_MERCHANT_ORDER",payOrderNo);
        apiMacSha256.setParameter("Ds_Merchant_MerchantURL",MerchantURL);
        apiMacSha256.setParameter("Ds_Merchant_UrlOK",UrlOK);
        apiMacSha256.setParameter("Ds_Merchant_UrlKO",UrlKO);
        apiMacSha256.setParameter("Ds_Merchant_SumTotal",amount);
//        apiMacSha256.setParameter("DS_MERCHANT_PAN","5540014436395013");
//        apiMacSha256.setParameter("DS_MERCHANT_EXPIRYDATE","10/22");
//        apiMacSha256.setParameter("DS_MERCHANT_CVV2","664");

       try{
           String params=apiMacSha256.createMerchantParameters();
           json.put("params",params);
           String sign=apiMacSha256.createMerchantSignature(key);
           json.put("sign",sign);
           System.out.println(apiMacSha256.decodeMerchantParameters(params));
       }catch (Exception e){
           e.printStackTrace();
       }
       return json;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        //生成
//        JSONObject jsonObject=getParamsAndSign("1","39100023");
//        System.out.println(jsonObject.toJSONString());
        //解析
//        ApiMacSha256 apiMacSha256=new ApiMacSha256();
//        System.out.println(apiMacSha256.decodeMerchantParameters("eyJEc19NZXJjaGFudF9NZXJjaGFudFVSTCI6Imh0dHBzOi8vd3d3LmExcHVibGljaWRhZC5ldS9hcGkvY2F" +
//                "sbGJhY2svZ2V0Q2FsbGJhY2siLCJEc19NZXJjaGFudF9VcmxLTyI6Imh0dHBzOi8vd3d3LmExcHVibGljaWRhZC5ldS8jL2hvbWU/bmFtZT1rbyIsIkRz" +
//                "X01lcmNoYW50X0N1cnJlbmN5IjoiOTc4IiwiRHNfTWVyY2hhbnRfU3VtVG90YWwiOiIyMzA0LjAwIiwiRHNfTWVyY2hhbnRfTWVyY2hhbnRDb2RlIjoiMzM2NDU4MDIxIiwiR" +
//                "HNfTWVyY2hhbnRfVHJhbnNhY3Rpb25UeXBlIjoiMSIsIkRzX01lcmNoYW50X1Rlcm1pbmFsIjoiMiIsIkRzX01lcmNoYW50X0Ftb3VudCI6IjIzMDQuMDAiLCJEc19NZXJjaGFudF9VcmxP" +
//                "SyI6Imh0dHBzOi8vd3d3LmExcHVibGljaWRhZC5ldS8jL2hvbWU/bmFtZT1vayIsIkRTX01FUkNIQU5UX09SREVSIjoiNzkxMDA2NzgwNDk5In0="));
        //获取今天0点时间戳
//        Long currentTimestamps=System.currentTimeMillis();
//        Long oneDayTimestamps= Long.valueOf(60*60*24*1000);
//        System.out.println(currentTimestamps-(currentTimestamps+60*60*8*1000)%oneDayTimestamps+(24*60*60*1000));
//        JSONObject j=new JSONObject();
//        j.put("status",1);
//        System.out.println(j.get("name"));
    }
}
