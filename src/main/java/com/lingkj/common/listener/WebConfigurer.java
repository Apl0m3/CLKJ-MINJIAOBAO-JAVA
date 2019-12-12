package com.lingkj.common.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @version V1.0
 * @ClassName:WebConfigurer
 * @Description:
 * @author:HuangJin
 * @date 2019/06/24 15:55
 */

@Configuration
public class WebConfigurer implements WebMvcConfigurer {
//    @Autowired
//    private LoginInterceptor loginInterceptor;

    /**
     * 这个方法是用来配置静态资源的，比如html，js，css，等等
      * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //需要配置1：----------- 需要告知系统，这是要被当成静态文件的！
        //第一个方法设置访问路径前缀，第二个方法设置资源路径

        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    /**
     * 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor).addPathPatterns("/manage/**").excludePathPatterns("/login");
////        registry.addInterceptor(loginInterceptor).addPathPatterns("/manag/**").excludePathPatterns("/login");
////        //较新Spring Boot的版本中这里可以直接去掉，否则会报错
////        super.addInterceptors(registry);
//    }
}
