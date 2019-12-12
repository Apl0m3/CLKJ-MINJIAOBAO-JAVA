package com.lingkj.project.manage.adminuser.controller;

import com.lingkj.common.utils.Constant;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.entity.UserMessage;
import com.lingkj.project.user.service.AdminUserService;
import com.lingkj.project.user.service.UserMessageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 *
 *
 * @author chenyongsong
 * @date 2019-09-11 14:22:51
 */
@RestController
@RequestMapping("/manage/usermessage")
public class UserMessageController {
    @Autowired
    private UserMessageService userMessageService;
    @Autowired
    AdminUserService adminUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("user:usermessage:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userMessageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("user:usermessage:info")
    public R info(@PathVariable("id") Long id){
        UserMessage userMessage = userMessageService.getById(id);
        User acceptUser = adminUserService.selectInfoById(userMessage.getUserId());
        userMessage.setAcceptUserName(acceptUser.getUserName());
        User senderUser = adminUserService.selectInfoById(userMessage.getSenderId());
        userMessage.setSenderUserName(senderUser.getUserName());
        return R.ok().put("userMessage", userMessage);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("commodity:usermessage:save")
    public R save(@RequestBody UserMessage userMessage){
			userMessageService.save(userMessage);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody UserMessage userMessage){
        // 未读修改为已读
        if (userMessage.getReadStatus() == Constant.UserMessageReadStatus.UNREAD.getReadStatus()) {
            userMessage.setReadStatus(Constant.UserMessageReadStatus.READ.getReadStatus());
        }
        userMessageService.updateById(userMessage);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("user:usermessage:delete")
    public R delete(@RequestBody Long[] ids){
			userMessageService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
