package com.lingkj.project.manage.adminuser.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.commodity.entity.Commodity;
import com.lingkj.project.manage.sys.controller.AbstractController;
import com.lingkj.project.user.dto.CommissionDto;
import com.lingkj.project.user.dto.DiscountDto;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.entity.UserMemberApplication;
import com.lingkj.project.user.service.AdminUserService;
import com.lingkj.project.user.service.UserMemberApplicationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 用户分销会员申请
 *
 * @author chenyongsong
 * @date 2019-08-16 09:31:05
 */
@RestController
@RequestMapping("/manage/usermemberapplication")
public class UserMemberApplicationController extends AbstractController {
    @Autowired
    private UserMemberApplicationService userMemberApplicationService;
    @Autowired
    private AdminUserService adminUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("user:usermemberapplication:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = userMemberApplicationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("user:usermemberapplication:info")
    public R info(@PathVariable("id") Long id) {
        Map<String, Object> map = userMemberApplicationService.selectByUserId(id);

        return R.ok().put("map", map);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("user:usermemberapplication:save")
    public R save(@RequestBody UserMemberApplication userMemberApplication, HttpServletRequest request) {
        Long sysUserId = getSysUserId();
        userMemberApplication.setApplicationBy(sysUserId);

        userMemberApplicationService.verify(userMemberApplication,request);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("user:usermemberapplication:update")
    public R update(@RequestBody UserMemberApplication userMemberApplication,HttpServletRequest request) {
        Long sysUserId = getSysUserId();
        userMemberApplication.setApplicationBy(sysUserId);
        userMemberApplicationService.verify(userMemberApplication,request);
        return R.ok();
    }

    /**
     * 保存折扣率
     */
    @RequestMapping("/saveDiscount")
    public R delete(@RequestBody UserMemberApplication userMemberApplication ) {
        userMemberApplicationService.saveDiscount(userMemberApplication);
        return R.ok();
    }

    /**
     * 根据商品名称查询
     */
    @GetMapping("/getUser")
    public R getCommodityByName(@RequestParam("name") String name) {
        List<User> list = adminUserService.list(new QueryWrapper<User>().like("name",name));
        return R.ok().put("list", list);
    }

    /**
     * DiscountDto
     */
    @RequestMapping("/getTypeList")
    public R getTypeList(@RequestParam Map<String, Object> params){
        String userId = (String)params.get("userId");
        List<DiscountDto> commissionDto = userMemberApplicationService.getTypeList(Long.valueOf(userId));
        return R.ok().put("typeList", commissionDto);
    }
}
