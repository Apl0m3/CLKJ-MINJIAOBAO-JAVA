package com.lingkj.common.utils.login;

import com.alibaba.fastjson.JSONObject;
import com.lingkj.common.config.InstagramConfig;
import com.lingkj.common.utils.HttpClientUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class InstagramUtil {
    /**
     * 回调获取code地址,根据code获取access_token
     * @param code
     */
    public static JSONObject getInstagramId(String code){
        JSONObject json=new JSONObject();
        HashMap<String,Object> map=new HashMap<>();
        map.put("client_id", InstagramConfig.client_id);
        map.put("client_secret",InstagramConfig.client_secret);
        map.put("grant_type",InstagramConfig.authorization_code);
        map.put("redirect_uri",InstagramConfig.redirect_uri);
        map.put("code",code);
        String accessToken;
        JSONObject user;
        String instagramId;
        String responseResult;
        try {
            responseResult = HttpClientUtil.getStringByPost(InstagramConfig.url, map);
            if(responseResult!=null){
                System.out.println("responseResult:"+responseResult);
                JSONObject jsonObject=JSONObject.parseObject(responseResult);
                System.out.println("instsgram:"+jsonObject.toJSONString());
                accessToken=jsonObject.getString("access_token");
                user=jsonObject.getJSONObject("user");
                instagramId=user.getString("id");
                json.put("access_token",accessToken);
                json.put("instagramId",instagramId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
