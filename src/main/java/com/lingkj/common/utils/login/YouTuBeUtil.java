package com.lingkj.common.utils.login;

import com.alibaba.fastjson.JSONObject;
import com.lingkj.common.config.YouTuBeConfig;
import com.lingkj.common.utils.HttpClientUtil;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;

public class YouTuBeUtil {

    /**
     * 获取faceBookId
     * @param code
     * @return
     */
    public static JSONObject getYouTuBeId(String code){
        JSONObject json=new JSONObject();
        String access_token=getToken(code);
        JSONObject userInfo;
        String youTubeId;
        if (StringUtils.isNotBlank(access_token)) {
            //第四步，用accessToken获取用户信息
            userInfo = getYouTuBeInfo(access_token);
            youTubeId=userInfo.getString("id");
            json.put("access_token",access_token);
            json.put("youTubeId",youTubeId);
        } else {
            System.out.println("accessToken is null");
        }
        return json;
    }

    /**
     * 回调获取code地址,根据code获取access_token
     * @param code
     */
    public static String getToken(String code){
        HashMap<String,Object> map=new HashMap<>();
        map.put("client_id", YouTuBeConfig.client_id);
        map.put("client_secret",YouTuBeConfig.client_secret);
        map.put("grant_type",YouTuBeConfig.authorization_code);
        map.put("redirect_uri",YouTuBeConfig.redirect_uri);
        map.put("code",code);
        String accessToken = null;
        String responseResult;
        try {
            responseResult = HttpClientUtil.getStringByGet(YouTuBeConfig.get_token_url, map);
            if(responseResult!=null){
                JSONObject jsonObject=JSONObject.parseObject(responseResult);
                accessToken=jsonObject.getString("access_token");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }
    public static JSONObject getYouTuBeInfo(String access_token){
        String getUserUrl=YouTuBeConfig.get_user_id_url;
        getUserUrl=getUserUrl.replace("#access_token",access_token);
        String responseResult = null;
        JSONObject userInfo = null;
        try {
            responseResult = HttpClientUtil.getStringByGet( getUserUrl, null);
            if(StringUtils.isNotBlank(responseResult)){
                JSONObject json=JSONObject.parseObject(responseResult);
                if(json!=null){
                    userInfo=json.getJSONArray("items").getJSONObject(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfo;
    }
}
