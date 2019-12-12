package com.lingkj.project.api.user.controller;

import com.lingkj.common.config.TwitterConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;
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

/**
 * ApiTwitterLoginController
 *
 * @author chen yongsong
 * @className ApiTwitterLoginController
 * @date 2019/9/17 16:33
 */
@RestController
@Slf4j
@RequestMapping("/api/login/twitter")
public class ApiTwitterLoginController extends HttpServlet {
    @GetMapping("getTwitterToken")
    public void getTwitterToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            System.out.println("应用用户哪定认证");
            ServletContext application = getServletContext();
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

    @GetMapping("callback")
    public void callback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext application = getServletContext();
        RequestToken requestToken = (RequestToken) application.getAttribute("requestToken");
        Twitter twitter = (Twitter) application.getAttribute("twitter");
        String pin = request.getParameter("oath_verifier");
        AccessToken accessToken = (AccessToken) request.getAttribute("accessToken");
        if (accessToken == null) {
            try {
                if (StringUtils.isNotBlank(pin)) {
                    accessToken = twitter.getOAuthAccessToken(requestToken, pin);

                } else {
                    accessToken = twitter.getOAuthAccessToken();
                }
                if (accessToken != null) {
                    System.out.println("认证成功完成");
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
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
