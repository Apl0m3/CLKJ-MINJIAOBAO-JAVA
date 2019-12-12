package com.lingkj.project.manage.adminuser.controller;

import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.project.integral.service.UserIntegralLogService;
import com.lingkj.project.user.dto.UpdateUserIntegralReqDto;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.service.AdminUserService;
import com.lingkj.project.user.service.UserCollectionCommodityService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:26
 */
@RestController
@RequestMapping("/manage/user")
public class UserController {
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private UserIntegralLogService userIntegralLogService;
    @Autowired
    private UserCollectionCommodityService collectionCommodityService;
    @Autowired
    private MessageUtils messageUtils;

    /**
     * 列表
     */
    @PostMapping("/list")
    @RequiresPermissions("manage:adminuser:list")
    public R list(@RequestBody Map<String, Object> params) {
        PageUtils page = adminUserService.queryPage(params);

        return R.ok().put("page", page);
    }
    /**
     * 获取下属用户积分列表
     */
    @PostMapping("/getInvitationUserLog")
    @RequiresPermissions("manage:adminuser:getInvitationUserLog")
    public R getInvitationUserLog(@RequestBody Map<String, Object> params) {
        PageUtils page = adminUserService.getInvitationUserLog(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("manage:adminuser:info")
    public R info(@PathVariable("id") Long id) {
        User adminUser = adminUserService.selectInfoById(id);

        return R.ok().put("adminUser", adminUser);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("manage:adminuser:save")
    public R save(@RequestBody(required = false) User adminUser) {
        adminUserService.saveOrUpdate(adminUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("manage:adminuser:update")
    public R update(@RequestBody(required = false) User adminUser) {
        adminUserService.updateById(adminUser);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("manage:adminuser:delete")
    public R delete(@RequestParam("ids") Long[] ids) {
        adminUserService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 启用 禁用
     */
    @PostMapping("/enableDisable")
    @RequiresPermissions("manage:adminuser:enableDisable")
    public R enableDisable(@RequestParam("id") Long id) {
        adminUserService.enableDisable(id);

        return R.ok();
    }

    /**
     * 重置密码
     */
    @PostMapping("/restPwd")
    @RequiresPermissions("manage:adminuser:restPwd")
    public R restPwd(@RequestParam("id") Long id, HttpServletRequest request) {
        if (id == null) {
            return R.error(messageUtils.getMessage("manage.choice.user", request));
        }
        String password=adminUserService.restPwd(id);

        return R.ok(password);
    }


    /**
     * 积分列表
     */
    @GetMapping("/userIntegralLogList")
    @RequiresPermissions("manage:user:userIntegralLogList")
    public R userIntegralLogList(@RequestParam Map<String, Object> params,Long userId) {
        PageUtils page = userIntegralLogService.queryPage(params, userId);

        return R.ok().put("page", page);
    }
    /**
     * 收藏列表
     */
    @GetMapping("/userCollectionList")
    @RequiresPermissions("manage:user:userCollectionList")
    public R userCollectionList(@RequestParam Map<String, Object> params) {
        Page page1=new Page();
        page1.setPage(Integer.valueOf(params.get("page").toString()));
        page1.setPageSize(Integer.valueOf(params.get("pageSize").toString()));
        page1.setLimit((page1.getPage()-1)*page1.getPageSize());
        PageUtils page = collectionCommodityService.selectPageByUserId(page1,Long.valueOf(params.get("userId").toString()));
        return R.ok().put("page", page);
    }

    @GetMapping("/getUserByPhone")
    public R getUserByPhone(@RequestParam("phone") String phone) {
        List<User> page = adminUserService.queryByMobileLike(phone);
        return R.ok().put("list", page);
    }
    /**
     * 修改分销用户积分
     */
    @PostMapping("/updateUserIntegral")
    @RequiresPermissions("manage:user:updateUserIntegral")
    public R updateUserIntegral(@RequestBody UpdateUserIntegralReqDto updateUserIntegralReqDto,HttpServletRequest request){
        adminUserService.UpdateUserIntegral(updateUserIntegralReqDto,request);
        return R.ok();
    }
}
