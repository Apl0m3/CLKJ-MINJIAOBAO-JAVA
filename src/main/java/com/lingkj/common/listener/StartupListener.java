package com.lingkj.common.listener;

import com.lingkj.common.utils.RedisUtils;
import com.lingkj.common.utils.SpringContextUtil;
import com.lingkj.common.utils.payment.wxpay.WeChatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 启动项目时把常用参数放到application里
 *
 * @author zhangxy
 * @ClassName: StartupListener.java
 * @date 2016-4-25 上午10:25:40
 */
@Component
public class StartupListener implements ApplicationRunner {

    @Autowired
    private RedisUtils redisUtils;

    public void jSApiTicket() {
        //设置微信公众平台 jsapiTicket
        if (redisUtils.exists("jsApi_ticket")) return;
        String jsApiTicket = WeChatUtils.getJSApiTicket();
        redisUtils.set("jsApi_ticket", jsApiTicket, 115 * 60);
    }

    public void startMonitoring() {
        if (!SpringContextUtil.getActiveProfile().equals("dev")) {
            String redisKey = "run_order";
            if (!redisUtils.exists(redisKey)) {
                throw new RuntimeException();
            }
        }
    }

    @Override
    public void run(ApplicationArguments args) {

        startMonitoring();
//        jSApiTicket();
    }
}
