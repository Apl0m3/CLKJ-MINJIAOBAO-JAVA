package com.lingkj.project.api.user.controller;

import com.lingkj.common.config.InstagramConfig;
import com.lingkj.common.utils.HttpClientUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;


/**
 * Instagram 授权
 * */
@RestController
public class ApiInstagramLoginController {


    /**
     * 回调获取code地址,根据code获取access_token
     * @param code
     * @param response
     */
    @GetMapping("getCode")
    public void getCode(@RequestParam("code") String code,HttpServletResponse response){
        HashMap<String,Object> map=new HashMap<>();
        map.put("client_id", InstagramConfig.client_id);
        map.put("client_secret",InstagramConfig.client_secret);
        map.put("grant_type",InstagramConfig.authorization_code);
        map.put("redirect_uri",InstagramConfig.redirect_uri);
        map.put("code",code);
        String responseResult = null;
        try {
            responseResult = HttpClientUtil.getStringByGet(InstagramConfig.url, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
