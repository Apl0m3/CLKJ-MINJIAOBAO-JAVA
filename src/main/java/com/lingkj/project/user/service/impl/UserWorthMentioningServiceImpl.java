package com.lingkj.project.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.common.validator.Assert;
import com.lingkj.project.api.user.dto.AccountBindingDto;
import com.lingkj.project.message.dto.MessageRespDto;
import com.lingkj.project.message.service.MessageService;
import com.lingkj.project.sys.service.SysCaptchaService;
import com.lingkj.project.user.dto.UserRespDto;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.entity.UserWorthMentioning;
import com.lingkj.project.user.mapper.UserMapper;
import com.lingkj.project.user.mapper.UserWorthMentioningMapper;
import com.lingkj.project.user.service.AdminUserService;
import com.lingkj.project.user.service.UserWorthMentioningService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * @author Administrator
 */
@Service
public class UserWorthMentioningServiceImpl extends ServiceImpl<UserWorthMentioningMapper, UserWorthMentioning> implements UserWorthMentioningService {

    @Autowired
    private AdminUserService userService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private SysCaptchaService sysCaptchaService;

    @Override
    public Map<String,Object> accountBinding(AccountBindingDto accountBindingDto) {
       User user = userService.queryByMail(accountBindingDto.getUserEmail());
       Assert.isNull(user,"该邮箱不存在,请重新输入邮箱账号");
        if(!user.getPassword().equals(DigestUtils.sha256Hex(accountBindingDto.getPassWord()))){
            throw new RRException("密码不正确，请输入正确密码");
        }
        if(StringUtils.isNotBlank(accountBindingDto.getCode())){
            boolean captcha = sysCaptchaService.validate(accountBindingDto.getUuid(), accountBindingDto.getCode());
            if (!captcha) {
                throw new RRException("验证码不正确");
            }
        }
       UserWorthMentioning userWorthMentioning=this.getOne(new QueryWrapper<UserWorthMentioning>().eq("user_id",user.getId()));
       if(userWorthMentioning==null){
           userWorthMentioning=new UserWorthMentioning();
           userWorthMentioning.setUserId(user.getId());
       }
       switch (accountBindingDto.getType()){
           case 1:
               userWorthMentioning.setFacebookId(accountBindingDto.getTripartiteId());
               break;
           case 2:
               userWorthMentioning.setInstagramId(accountBindingDto.getTripartiteId());
               break;
           case 3:
               userWorthMentioning.setYoutubeId(accountBindingDto.getTripartiteId());
               break;
           case 4:
               userWorthMentioning.setTwitterId(accountBindingDto.getTripartiteId());
               break;
       }
       this.saveOrUpdate(userWorthMentioning);
        UserRespDto userRespDto = userService.queryPersonInfo(userWorthMentioning.getUserId());
        Map<String, Object> loginMap = userService.getLoginMap(user);
        loginMap.put("user",userRespDto);
        loginMap.put("status",2);
        loginMap.put("code",200);
        return  loginMap;
    }

    @Override
    public Map<String,Object> loginCheck(String type,String tripartiteId){
        QueryWrapper<UserWorthMentioning> queryWrapper=new QueryWrapper<>();
        switch (type){
            case "1":
                queryWrapper.eq("faceBook_id",tripartiteId);
                break;
            case "2":
                queryWrapper.eq("instagram_id",tripartiteId);
                break;
            case "3":
                queryWrapper.eq("youTuBe_id",tripartiteId);
                break;
            case "4":
                queryWrapper.eq("twitter_id",tripartiteId);
                break;
        }
        UserWorthMentioning  userWorthMentioning=this.getOne(queryWrapper);
        if(userWorthMentioning!=null){
            User user=userService.selectInfoById(userWorthMentioning.getUserId());
            UserRespDto userRespDto = userService.queryPersonInfo(user.getId());
            Map<String,Object> map = userService.getLoginMap(user);
//            MessageRespDto dto = messageService.queryNewMessage(user.getId());
            Page page=new Page();
            PageUtils pageUtils = messageService.queryMessagePage(page, user.getId(), null);
            if(pageUtils.getList()!=null && pageUtils.getList().size()>0){
                MessageRespDto dto = (MessageRespDto) pageUtils.getList().get(0);
                if (dto != null) {
                    map.put("sysMessage", dto);
                }
            }
            map.put("user",userRespDto);
            map.put("status",2l);
            return map;
        }else{
            Map<String,Object> map=new HashedMap();
            map.put("status",1l);
            return map;
        }

    }


}
