package com.lingkj.common.utils.i18n;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CustomMvcConfig
 *
 * @author chen yongsong
 * @className CustomMvcConfig
 * @date 2019/9/15 17:02
*/


@Configuration
public class CustomMvcConfig implements WebMvcConfigurer {

/**
     * 配置自己的国际化语言解析器
     *
     * @return
     */


    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }

/**
     * 配置自己的拦截器
      */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //super.addInterceptors(registry);
    }

}
