package com.lingkj.project.api.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.lingkj.common.config.FaceBookConfig;
import com.lingkj.common.utils.HttpClientUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * ApiFaceBookLoginController
 *
 * @author chen yongsong
 * @className ApiFaceBookLoginController
 * @date 2019/9/16 14:34
 */
@RestController
@RequestMapping("/public/faceBook/")
public class ApiFaceBookLoginController {

    /**
     * @return void 这个就是在应用中定义的跳转网址，也就是重定向第二步之后回调的地址，并且带上了code参数
     * @throws IOException
     * @throws
     * @Title: doLogin
     * @Description: 调用“登录”对话框和设置重定向网址
     * @date Mar 17, 2017 9:29:03 AM
     */
    @RequestMapping(value = "/doLogin")
    @ResponseBody
    public Object doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //第二步获取code，迷糊的同学往下看，方法上也有对应的步骤
        String code = request.getParameter("code");
        if (StringUtils.isNotBlank(code)) {
            //第三步，用code（临时口令）换取accessToken
            String accessToken = getFacebookAccessToken(code);
            JSONObject userInfo = null;
            if (StringUtils.isNotBlank(accessToken)) {
                //第四步，用accessToken获取用户信息
                userInfo = getUserInfo(accessToken);
            } else {
                System.out.println("accessToken is null");
            }
            //对用户信息进行处理
            System.out.println(userInfo);
            return userInfo;
        } else {
            return "/code";
        }
    }


    /**
     * @return String
     * @throws ServletException
     * @throws IOException
     * @throws
     * @Title: getAuthorizationCode
     * @Description: 获取 Authorization Code（临时口令）
     * @author 第二步，在index.jsp中用户登录成功后就是跳转到这里，重定向此地址会在回调地址中的参数带上code
     * @date Mar 17, 2017 9:30:38 AM
     */
    @RequestMapping(value = "/code")
    public static void getAuthorizationCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect( FaceBookConfig.code_url + "?client_id=" +  FaceBookConfig.client_id + "&redirect_uri=" +  FaceBookConfig.redirect_url);
    }

    /**
     * @param code“登录”对话框重定向接收的参数。
     * @return String
     * @throws IOException
     * @throws
     * @Title: getFacebookAccessToken
     * @Description:用临时口令获取访问口令 access_token
     * @author 第三步用code换取accessToken（调用的接口和参数在代码里找就能看明白了）
     * @date Mar 15, 2017 11:36:11 AM
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
            System.out.println(responseResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
       /* JSONObject parse = JSONObject.parseObject(responseResult);
        if (null != responseResult && responseResult[0].equals("200")) {
            String result = responseResult[1];
            JSONObject jsonObject = JSONObject.parseObject(result);
            accessToken = jsonObject.getString("access_token");
        }*/
        return accessToken;
        //获取,然后返回access_token
		/*{
		  "access_token": {access-token},
		  "token_type": {type},
		  "expires_in":  {seconds-til-expiration}
		}*/
    }

    /**
     * @return Map<String, String>
     * @throws
     * @throws
     * @Title: getUserInfo
     * @Description:根据 token口令获取用户信息
     * @author 第四步用accessToken获取用户信息（调用的接口和参数在代码里找就能看明白了）
     * @date Mar 15, 2017 6:04:31 PM
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
        } catch (Exception e) {
            e.printStackTrace();
        }
       /* if (null != responseResult && responseResult[0].equals("200")) {
            String result = responseResult[1];
            userInfo = JSONObject.parseObject(result);
        }*/
        return userInfo;
    }

    /**
     * @return String
     * @throws
     * @Title: verifyToken
     * @Description: 调用图谱API，验证口令  app_id 和 user_id 字段将帮助您的应用确认访问口令对用户和您的应用有效。
     * @author 第五步验证访问的用户是否来自你的应用，防刷功能，防止恶意注册
     * @date Mar 17, 2017 9:50:38 AM
     */
    @RequestMapping("/verify")
    @ResponseBody
    public Object verifyToken(String accessToken) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        //检验口令
        accessToken = "EAATb6fZCbwXgBAFlUThSX7xWMcwfVhpT8A9szvYkWsTqhJDjcILOLkTPReDYHx6BfWl67MXA2ZApPyc7FEDJGJ1bIrM0u8zQI6nszrcnzULDRuUG2gBWIjuZAe6CPZCYXBHClpsL8zhZAK4gVZC4N27ZAkZBPDscRJW0bRS05LisJAZDZD";
        //应用口令
        String access_token = getAppToken();
        params.put("input_token", accessToken);
        params.put("access_token", access_token);
        String responseResult = null;
        String data = null;
        try {
            responseResult = HttpClientUtil.getStringByGet( FaceBookConfig.verify_url, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*if (null != responseResult && responseResult[0].equals("200")) {
            String result = responseResult[1];
            JSONObject jsonObject = JSONObject.parseObject(result);
            data = jsonObject.getString("data");
            System.out.println(data);
        }*/
//		{
//		    "data": {
//		        "app_id": 138483919580948,
//		        "application": "Social Cafe",
//		        "expires_at": 1352419328,
//		        "is_valid": true,
//		        "issued_at": 1347235328,
//		        "metadata": {
//		            "sso": "iphone-safari"
//		        },
//		        "scopes": [
//		            "email",
//		            "publish_actions"
//		        ],
//		        "user_id": 1207059
//		    }
//		}
        return data;
    }

    /**
     * @return String
     * @throws
     * @Title: getAppToken
     * @Description: 获取应用口令（用来验证口令是否来自我的应用）
     * @author gaona
     * @date Mar 20, 2017 3:16:26 PM
     */
    public String getAppToken() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("client_id",  FaceBookConfig.client_id);
        params.put("client_secret",  FaceBookConfig.client_secret);
        params.put("grant_type", "client_credentials");
        String responseResult = null;
        String appToken = null;
        try {
            responseResult = HttpClientUtil.getStringByGet(FaceBookConfig.app_url, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
       /* if (null != responseResult && responseResult[0].equals("200")) {
            String result = responseResult[1];
            JSONObject jsonObject = JSONObject.parseObject(result);
            appToken = jsonObject.getString("access_token");
            System.out.println(appToken);
        }*/
        return appToken;
    }
}
