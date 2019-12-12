package com.lingkj.project.message.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.authentication.paramsvalidation.SendCodeForm;
import com.lingkj.common.bean.ObtainRedisKey;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.common.utils.RedisUtils;
import com.lingkj.common.utils.juhe.JhTemplateValue;
import com.lingkj.common.utils.juhe.JuHeUtil;
import com.lingkj.common.validator.Assert;
import com.lingkj.project.message.entity.MessageEmailCodeLog;
import com.lingkj.project.message.mapper.MessageEmailCodeLogMapper;
import com.lingkj.project.message.service.MessageSmsLogService;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;


/**
 * @author chen yongsong
 */
@Service("sysSendSmsLogService")
public class MessageSmsLogServiceImpl extends ServiceImpl<MessageEmailCodeLogMapper, MessageEmailCodeLog> implements MessageSmsLogService {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private AdminUserService userService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<MessageEmailCodeLog> wrapper = new QueryWrapper<>();
        if (params.get("phone") != null && !params.get("phone").equals("")) {
            wrapper.like("phone", params.get("phone").toString());
        }
        wrapper.orderByDesc("create_time");
        IPage<MessageEmailCodeLog> page = this.page(
                new Query<MessageEmailCodeLog>(params).getPage(),
                wrapper
        );
        if (page.getRecords() == null || page.getRecords().size() == 0) {
            params.put("page", 1);
            page = this.page(  new Query<MessageEmailCodeLog>(params).getPage(),
                    wrapper
            );
        }

        return new PageUtils(page);
    }

    @Override
    public void sendCode(SendCodeForm form) {
        User user = userService.queryByMobile(form.getMobile());
        if (form.getType() != 1) {
            Assert.isNull(user, "账号未注册");

        } else {
            Assert.isNotNull(user, "该用户已注册");
        }
        //生成验证码
        ObtainRedisKey obtainRedisKey = new ObtainRedisKey(form.getMobile(), form.getType()).invoke();
        String verifyCode = obtainRedisKey.getVerifyCode();
        String redisKey = obtainRedisKey.getRedisKey();
        //保存消息记录
        MessageEmailCodeLog messageEmailCodeLog = new MessageEmailCodeLog();
        messageEmailCodeLog.setCode(verifyCode);
        messageEmailCodeLog.setCreateTime(new Date());
        messageEmailCodeLog.setType(form.getType());
        messageEmailCodeLog.setPhone(form.getMobile());
        String logisticsKey = "logistics_sms_num_" + 1;
        if (!redisUtils.exists(logisticsKey) || (redisUtils.get(logisticsKey)!=null && Integer.valueOf(redisUtils.get(logisticsKey)) <= 0)) {
            throw new RRException("短信发送失败");
        }
        JhTemplateValue jhTemplateValue=new JhTemplateValue();
        jhTemplateValue.setType(form.getType());
        jhTemplateValue.setPhone(form.getMobile());
        jhTemplateValue.setCode(verifyCode);
        //调用第三方发送短信验证码
        JSONObject jsonObject = JuHeUtil.sendMsg(jhTemplateValue);
        Assert.isNull(jsonObject, "验证码发送失败");
        redisUtils.decr(logisticsKey, 1l);

        if (jsonObject.getInteger("error_code") == 0) {
            messageEmailCodeLog.setStatus(0);
        } else {
            messageEmailCodeLog.setStatus(1);
        }
        messageEmailCodeLog.setMsg(jsonObject.toString());
        this.save(messageEmailCodeLog);

        //redis 1分钟重复请求
        redisUtils.set(redisKey + "_flag", verifyCode, 60);
        //redis 3分钟有效
        redisUtils.set(redisKey, verifyCode, 3 * 60);
    }


}
