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
import com.lingkj.project.user.entity.UserDesignerApplication;
import com.lingkj.project.user.service.AdminUserService;
import com.lingkj.project.user.service.UserApplicationFileService;
import com.lingkj.project.user.service.UserDesignerApplicationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 *
 *
 * @author chenyongsong
 * @date 2019-09-12 09:17:38
 */
@RestController
@RequestMapping("/manage/userdesignerapplication")
public class UserDesignerApplicationController {
    @Autowired
    private UserDesignerApplicationService userDesignerApplicationService;
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
    @RequiresPermissions("user:userdesignerapplication:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userDesignerApplicationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("user:userdesignerapplication:info")
    public R info(@PathVariable("id") Long id){
        Map<String, Object> map = userDesignerApplicationService.queryInfo(id);
        return R.ok(map);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("user:userdesignerapplication:save")
    public R save(@RequestBody UserDesignerApplication userDesignerApplication){
			userDesignerApplicationService.save(userDesignerApplication);

        return R.ok();
    }

    /**
     * 审批
     */
    @RequestMapping("/approval")
    @RequiresPermissions("user:userdesignerapplication:approval")
    public R update(@RequestBody UserDesignerApplication userDesignerApplication, HttpServletRequest request){
        userDesignerApplicationService.approval(userDesignerApplication,request);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("user:userdesignerapplication:delete")
    public R delete(@RequestBody Long[] ids){
			userDesignerApplicationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * commissionDto
     */
    @RequestMapping("/getTypeList")
//    @RequiresPermissions("user:userdesignerapplication:delete")
    public R getTypeList(){
        List<CommissionDto> commissionDto = userDesignerApplicationService.getTypeList();
        return R.ok().put("typeList", commissionDto);
    }
    /**
     * commissionDto
     */
    @RequestMapping("/getcommissionDto")
//    @RequiresPermissions("user:userdesignerapplication:delete")
    public R getcommissionDto(){
        List<CommissionDto> commissionDto = userDesignerApplicationService.getCommissionDto();
        return R.ok().put("commissionDto", commissionDto);
    }

}
