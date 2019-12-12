package com.lingkj.project.manage.adminuser.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingkj.common.utils.Constant;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.operation.entity.OperateType;
import com.lingkj.project.operation.service.OperateTypeService;
import com.lingkj.project.user.dto.CommissionDto;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.entity.UserApplicationFile;
import com.lingkj.project.user.entity.UserSupplierApplication;
import com.lingkj.project.user.service.AdminUserService;
import com.lingkj.project.user.service.UserApplicationFileService;
import com.lingkj.project.user.service.UserSupplierApplicationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;



/**
 * 用户供应商 申请  表
 *
 * @author chenyongsong
 * @date 2019-09-12 09:17:38
 */
@RestController
@RequestMapping("/manage/usersupplierapplication")
public class UserSupplierApplicationController {
    @Autowired
    private UserSupplierApplicationService userSupplierApplicationService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private OperateTypeService operateTypeService;
    @Autowired
    private UserApplicationFileService applicationFileService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("user:usersupplierapplication:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userSupplierApplicationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("user:usersupplierapplication:info")
    public R info(@PathVariable("id") Long id){

        return R.ok(userSupplierApplicationService.queryInfo(id));
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("user:usersupplierapplication:save")
    public R save(@RequestBody UserSupplierApplication userSupplierApplication){
			userSupplierApplicationService.save(userSupplierApplication);

        return R.ok();
    }

    /**
     * 审批
     */
    @RequestMapping("/approval")
    @RequiresPermissions("user:usersupplierapplication:approval")
    public R update(@RequestBody UserSupplierApplication userSupplierApplication, HttpServletRequest request){
        userSupplierApplicationService.approval(userSupplierApplication,request);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("user:usersupplierapplication:delete")
    public R delete(@RequestBody Long[] ids){
			userSupplierApplicationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 获取供应商产品 没有带有Commission属性
     */
    @RequestMapping("/getProList")
    @RequiresPermissions("user:usersupplierapplication:approval")
    public R getProList(@RequestBody long id){
        List<CommissionDto> typeList = userSupplierApplicationService.getCommissionDtoList(id);
        return R.ok().put("typeList",typeList);
    }
    /**
     * 获取供应商产品 带有Commission属性
     */
    @RequestMapping("/getProListCommission")
    @RequiresPermissions("user:usersupplierapplication:approval")
    public R getProListCommission(@RequestBody long id){
        List<CommissionDto> typeList = userSupplierApplicationService.getCommissionList(id);
        return R.ok().put("proListCommission",typeList);
    }

}
