package com.lingkj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
 * @author Administrator
 */
@SpringBootApplication
public class DingZhiApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(DingZhiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  辰领科技启动成功   ლ(´ڡ`ლ)ﾞ");
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(DingZhiApplication.class);
//    }
}
