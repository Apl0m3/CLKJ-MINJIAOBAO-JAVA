package com.lingkj.common.utils.login;

import com.alibaba.fastjson.JSONObject;
import com.lingkj.common.config.FaceBookConfig;
import com.lingkj.common.utils.HttpClientUtil;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;

public class FaceBookUtil {

    /**
     * 获取faceBookId
     * @param code
     * @return
     */
    public static JSONObject getFaceBookId(String code){
        JSONObject json=new JSONObject();
        String access_token=getFacebookAccessToken(code);
        JSONObject userInfo;
        String faceBookId;
        if (StringUtils.isNotBlank(access_token)) {
            //第四步，用accessToken获取用户信息
            userInfo = getUserInfo(access_token);
            faceBookId=userInfo.getString("id");
            json.put("access_token",access_token);
            json.put("faceBookId",faceBookId);
        } else {
            System.out.println("accessToken is null");
        }
        System.out.println("JSON"+json);
        return json;
    }

    /**
     * 根据code获取token
     * @param code
     * @return
     */
    public static String getFacebookAccessToken(String code) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("client_id", FaceBookConfig. client_id);
        params.put("redirect_uri",  FaceBookConfig.redirect_url);
        params.put("client_secret",  FaceBookConfig.client_secret);
        params.put("code", code);
        String responseResult = null;
        String accessToken = null;
        try {
            responseResult = HttpClientUtil.getStringByPost(FaceBookConfig.token_url, params);
            JSONObject jsonObject=JSONObject.parseObject(responseResult);
            if(jsonObject!=null){
                accessToken=jsonObject.getString("access_token");
            }
            System.out.println(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    /**
     * 根据token获取用户信息
     * @param accessToken
     * @return
     */
    public static JSONObject getUserInfo(String accessToken) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        String fields = "id,name,birthday,gender,hometown,email,devices";
        params.put("access_token", accessToken);
        params.put("fields", fields);
        String responseResult = null;
        JSONObject userInfo = null;
        try {
            responseResult = HttpClientUtil.getStringByGet( FaceBookConfig.user_url, params);
            if(StringUtils.isNotBlank(responseResult)){
                userInfo=JSONObject.parseObject(responseResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userInfo;
    }


}
