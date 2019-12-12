package com.lingkj.project.manage.sys.controller;

import com.lingkj.common.annotation.SysLog;
import com.lingkj.common.utils.Constant;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.common.validator.Assert;
import com.lingkj.common.validator.ValidatorUtils;
import com.lingkj.common.validator.group.AddGroup;
import com.lingkj.common.validator.group.UpdateGroup;
import com.lingkj.project.sys.entity.SysUserEntity;
import com.lingkj.project.sys.form.PasswordForm;
import com.lingkj.project.sys.service.SysUserRoleService;
import com.lingkj.project.sys.service.SysUserService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author admin
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private MessageUtils messageUtils;


    /**
     * 所有用户列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:user:list")
    public R list(@RequestParam Map<String, Object> params) {
        //只有超级管理员，才能查看所有管理员列表
        if (getSysUserId() != Constant.SUPER_ADMIN) {
            params.put("createUserId", getSysUserId());
        }
        PageUtils page = sysUserService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    public R info() {
        return R.ok().put("user", getSysUser());
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @PostMapping("/password")
    public R password(@RequestBody PasswordForm form, HttpServletRequest request) {
        Assert.isBlank(form.getNewPassword(),  messageUtils.getMessage("api.user.validate.newPassword.null", request));

        //sha256加密
        String password = new Sha256Hash(form.getPassword(), getSysUser().getSalt()).toHex();
        //sha256加密
        String newPassword = new Sha256Hash(form.getNewPassword(), getSysUser().getSalt()).toHex();

        //更新密码
        boolean flag = sysUserService.updatePassword(getSysUserId(), password, newPassword);
        if (!flag) {
            return R.error( messageUtils.getMessage("api.user.validate.password.error", request));
        }

        return R.ok();
    }

    /**
     * 修改前端语言
     */
    @SysLog("修改前端语言")
    @PostMapping("/locale")
    public R changeLocale(@RequestBody SysUserEntity userEntity, HttpServletRequest request) {
        Assert.isBlank(userEntity.getLocale(), messageUtils.getMessage("manage.local.null", request));
        sysUserService.updateLocale(getSysUserId(), userEntity.getLocale());
        return R.ok();
    }

    /**
     * 重置密码
     */
    @SysLog("重置密码")
    @GetMapping("/resetPassword/{id}")
    @RequiresPermissions("sys:user:resetPassword")
    public R resetPassword(@PathVariable("id") Long id) {

        sysUserService.resetPassword(id);

        return R.ok();
    }


    /**
     * 用户信息
     */
    @GetMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public R info(@PathVariable("userId") Long userId) {
        SysUserEntity user = sysUserService.getById(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return R.ok().put("user", user);
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @PostMapping("/save")
    @RequiresPermissions("sys:user:save")
    public R save(@RequestBody SysUserEntity user,HttpServletRequest request) {
        ValidatorUtils.validateEntity(user, AddGroup.class);

        user.setCreateUserId(getSysUserId());
        sysUserService.saveSysUser(user,request);

        return R.ok();
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @PostMapping("/update")
    @RequiresPermissions("sys:user:update")
    public R update(@RequestBody SysUserEntity user,HttpServletRequest request) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);

        user.setCreateUserId(getSysUserId());
        sysUserService.update(user,request);

        return R.ok();
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public R delete(@RequestBody Long[] userIds,HttpServletRequest request) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return R.error(messageUtils.getMessage("manage.delete.system.administrator.error", request));
        }

        if (ArrayUtils.contains(userIds, getSysUserId())) {
            return R.error(messageUtils.getMessage("manage.delete.user.error", request));
        }

        sysUserService.deleteBatch(userIds);

        return R.ok();
    }

    /**
     * 禁用 / 启用
     */
    @SysLog("禁用 / 启用")
    @GetMapping("/onOrOff/{id}")
    @RequiresPermissions("sys:user:onOrOff")
    public R onOrOff(@PathVariable("id") Long id,HttpServletRequest request) {

        sysUserService.updateBatchStatus(id,request);

        return R.ok();
    }
}
