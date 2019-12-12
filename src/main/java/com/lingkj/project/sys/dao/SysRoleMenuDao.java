package com.lingkj.project.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.sys.entity.SysRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色与菜单对应关系
 *
 * @author admin
 *
 * @date 2016年9月18日 上午9:33:46
 */
@Mapper
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenuEntity> {

	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);

	/**
	 * 根据角色ID，获取菜单ID列表（不查询父菜单）
	 *
	 * @param roleId 角色Id
	 * @return List
	 */
	List<Long> querySubMenuIdList(Long roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}
