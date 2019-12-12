package com.lingkj.project.manage.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingkj.common.annotation.SysLog;
import com.lingkj.common.utils.Constant;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.common.validator.ValidatorUtils;
import com.lingkj.project.sys.entity.SysRoleEntity;
import com.lingkj.project.sys.service.SysRoleMenuService;
import com.lingkj.project.sys.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author admin
 *
 * @date 2016年11月8日 下午2:18:33
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;

	/**
	 * 角色列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:role:list")
	public R list(@RequestParam Map<String, Object> params){
		//如果不是超级管理员，则只查询自己创建的角色列表
		if(getSysUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", getSysUserId());
		}

		PageUtils page = sysRoleService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 角色列表
	 */
	@GetMapping("/select")
	@RequiresPermissions("sys:role:select")
	public R select(){
		Map<String, Object> map = new HashMap<>();

		QueryWrapper<SysRoleEntity> wrapper=new QueryWrapper<>();
		//如果不是超级管理员，则只查询自己所拥有的角色列表
		if(getSysUserId() != Constant.SUPER_ADMIN){
			wrapper.eq("create_user_id",getSysUserId());
		}

		List<SysRoleEntity> list = sysRoleService.list(wrapper);

		return R.ok().put("list", list);
	}

	/**
	 * 角色信息
	 */
	@GetMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public R info(@PathVariable("roleId") Long roleId){
		SysRoleEntity role = sysRoleService.getById(roleId);

		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);

		return R.ok().put("role", role);
	}

	/**
	 * 保存角色
	 */
	@SysLog("保存角色")
	@PostMapping("/save")
	@RequiresPermissions("sys:role:save")
	public R save(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);

		role.setCreateUserId(getSysUserId());
		sysRoleService.save(role);

		return R.ok();
	}

	/**
	 * 修改角色
	 */
	@SysLog("修改角色")
	@PostMapping("/update")
	@RequiresPermissions("sys:role:update")
	public R update(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);

		role.setCreateUserId(getSysUserId());
		sysRoleService.update(role);

		return R.ok();
	}

	/**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@PostMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public R delete(@RequestBody Long[] roleIds){
		sysRoleService.deleteBatch(roleIds);

		return R.ok();
	}
}
