package com.lingkj.project.api.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.lingkj.common.config.FaceBookConfig;
import com.lingkj.common.config.InstagramConfig;
import com.lingkj.common.config.TwitterConfig;
import com.lingkj.common.config.YouTuBeConfig;
import com.lingkj.common.utils.R;
import com.lingkj.common.utils.login.FaceBookUtil;
import com.lingkj.common.utils.login.InstagramUtil;
import com.lingkj.common.utils.login.YouTuBeUtil;
import com.lingkj.common.validator.Assert;
import com.lingkj.project.api.user.dto.AccountBindingDto;
import com.lingkj.project.user.dto.UserRespDto;
import com.lingkj.project.user.service.UserWorthMentioningService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 三方登录集成
 * */

@RestController
@RequestMapping("/api/public/worthMentioning/")
public class ApiWorthMentioningController extends HttpServlet {
    String loginUrl="https://web.a1publicidad.eu/#/home";
    @Autowired
    private UserWorthMentioningService userWorthMentioningService;

    /**
     *
     * @param code 第三方返回临时code用于获取access_token
     * @param type 区分第三方登录类型 1.faceBook 2.instagram 3.youtube
     */
    @GetMapping("getCode")
    public void getCode(@RequestParam("code")String code,@RequestParam("type") String type, HttpServletResponse response) throws IOException {

        String access_token=null;
        if(StringUtils.isEmpty(type)){
            System.out.println("参数错误:type不存在");
            return;
        }
        if(StringUtils.isEmpty(code)){
            String url=getUrl(type);
            System.out.println("根据type重定向到对应链接");
            response.sendRedirect(url);
        }
        String id="";
        JSONObject json;
        switch (type){
            case "1":
                json = FaceBookUtil.getFaceBookId(code);
                access_token=json.getString("access_token");
                id=json.getString("faceBookId");
                break;
            case "2":
                json = InstagramUtil.getInstagramId(code);
                access_token=json.getString("access_token");
                id=json.getString("instagramId");
                break;
            case "3":
                json = YouTuBeUtil.getYouTuBeId(code);
                access_token=json.getString("access_token");
                id=json.getString("youTubeId");
                break;
        }
        Map<String,Object> map=userWorthMentioningService.loginCheck(type,id);
        Long status=Long.valueOf(map.get("status").toString());
        //重定向首页
        if(status==2l){
            UserRespDto userRespDto= (UserRespDto) map.get("user");
            response.sendRedirect(loginUrl+"?type=4&Id="+id+"&status="+status+"&access_token="+map.get("token")+"&name="+userRespDto.getName()+"&avatar="+userRespDto.getAvatar()+"&userRoleId="+userRespDto.getUserRoleId());
        }else{
            response.sendRedirect(loginUrl+"?type="+type+"&Id="+id+"&status="+status);
        }
    }

    /**
     *
     * @param type 区分第三方登录类型 1.faceBook 2.instagram 3.youtube
     */
    @GetMapping("getWorthMentioning")
    public R getWorthMentioning(@RequestParam("type")String type, HttpServletResponse response) throws IOException {
//        response.sendRedirect(getUrl(type));
      return   R.ok().put("url",getUrl(type));
    }

    public String getUrl(String type){
        String url="";
        try {
            switch (type){
                case "1":
                    url=FaceBookConfig.get_code_url.replace("#client_id",FaceBookConfig.client_id).replace("#redirect_uri",URLEncoder.encode(FaceBookConfig.redirect_url, "GBK"));;
                    break;
                case "2":
                    url=InstagramConfig.get_code_url.replace("#client_id",InstagramConfig.client_id).replace("#redirect_uri",InstagramConfig.redirect_uri);
                    break;
                case "3":
                    url= YouTuBeConfig.get_code_url.replace("#client_id",YouTuBeConfig.client_id).replace("#redirect_uri",YouTuBeConfig.redirect_uri);
                    break;
                case "4": //twitter 重定向系统地址
                    url=TwitterConfig.get_token_url;
                    break;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * 获取twitter token
     * @param request
     * @param response
     */
    @GetMapping("getTwitterToken")
    public void getTwitterToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            System.out.println("应用用户哪定认证");
            ServletContext application = request.getServletContext();
//        Twitter tuitter = TwitterFactory - getsingleton()
//        twitter.setOAuthConsumer("7FVIREOKCuQLzukcsSdQdYhbO", "9PIodcMsv71HveGjwNIr3vDmuXHe4HxSEBvUjZZKgchhetHHr3-)↓
//           传入 CONSUMER_KEY、CONSUMER_SECRET 两个参数
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(TwitterConfig.CONSUMER_KEY);
            builder.setOAuthConsumerSecret(TwitterConfig.CONSUMER_SECRET);
            Configuration configuration = builder.build();
            TwitterFactory factory = new TwitterFactory(configuration);
            Twitter twitter = factory.getInstance();
            application.setAttribute("twitter", twitter);
            RequestToken requestToken = null;
            // 拿到 requestToken
            requestToken = twitter.getOAuthRequestToken();
            application.setAttribute("requestToken", requestToken);
            System.out.println("open the following URL and grant acces to your account:");
            System.out.println(requestToken.getAuthenticationURL());
            // 跳转到授权页面
            response.sendRedirect(requestToken.getAuthenticationURL() + "&force_login=true");
        } catch (Exception el) {
            // TODO Auto -generated catchblock
            el.printStackTrace();
        }

    }

    /**
     * twitter重定向地址
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @GetMapping("callback")
    public void callback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext application = request.getServletContext();
        RequestToken requestToken = (RequestToken) application.getAttribute("requestToken");
        Twitter twitter = (Twitter) application.getAttribute("twitter");
        String pin = request.getParameter("oauth_verifier");
        AccessToken accessToken = (AccessToken) request.getAttribute("accessToken");
        String id="";
        Map map=new HashedMap();
        long status=0;
        if (accessToken == null) {
            try {
                System.out.println("pin:"+pin);
                System.out.println("requestToken:"+requestToken);
                if (StringUtils.isNotBlank(pin)) {
                    accessToken = twitter.getOAuthAccessToken(requestToken, pin);

                } else {
                    accessToken = twitter.getOAuthAccessToken();
                }
                System.out.println("accessToken:"+accessToken.toString());
                if (accessToken != null) {;
                    map=userWorthMentioningService.loginCheck("4",id);
                    status=Long.valueOf(map.get("status").toString());
                    id=accessToken.getUserId()+"";
                    System.out.println("认证成功完成");
                }else{
                    System.out.println("认证失败");
                }
            } catch (TwitterException e) {
                if (401 == e.getStatusCode()) {
                    System.out.println("unable to get the access token.");
                } else {
                    e.printStackTrace();
                }
            }
        }
        //重定向 首页
//        request.getRequestDispatcher("/index.jsp").forward(request, response);
        //重定向首页
        if(status==2l){
            UserRespDto userRespDto= (UserRespDto) map.get("user");
            response.sendRedirect(loginUrl+"?type=4&Id="+id+"&status="+status+"&access_token="+map.get("token")+"&name="+userRespDto.getName()+"&avatar="+userRespDto.getAvatar()+"&userRoleId="+userRespDto.getUserRoleId());
        }else{
            response.sendRedirect(loginUrl+"?type=4&Id="+id+"&status="+status);
        }
    }

    /**
     * 绑定第三方账号
     */
    @PostMapping("accountBinding")
    public Map<String,Object> accountBinding(@RequestBody AccountBindingDto accountBindingDto){
        Assert.isBlank(accountBindingDto.getType().toString(),"绑定类型不能为空",400);
        Assert.isBlank(accountBindingDto.getTripartiteId(),"三方id不能为空",400);
        Assert.isBlank(accountBindingDto.getUserEmail(),"绑定账号不能为空",400);
        Assert.isBlank(accountBindingDto.getPassWord(),"密码不能为空",400);
        Assert.isBlank(accountBindingDto.getCode(),"验证码不能为空",400);
        Map<String, Object> map = userWorthMentioningService.accountBinding(accountBindingDto);
        return  map;
    }

    public static void main(String[] args) {
        Map<String,Object> map =new HashedMap();
        UserRespDto userRespDto=new UserRespDto();
        userRespDto.setAddress("123");
        map.put("user",userRespDto);
        System.out.println(map);
    }
}
