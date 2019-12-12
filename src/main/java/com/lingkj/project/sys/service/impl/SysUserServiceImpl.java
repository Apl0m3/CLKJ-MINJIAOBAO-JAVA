package com.lingkj.project.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.Constant;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.project.sys.dao.SysUserDao;
import com.lingkj.project.sys.entity.SysUserEntity;
import com.lingkj.project.sys.service.SysRoleService;
import com.lingkj.project.sys.service.SysUserRoleService;
import com.lingkj.project.sys.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 * @author admin
 *
 * @date 2016年9月18日 上午9:46:09
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private MessageUtils messageUtils;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String username = (String)params.get("username");
		Long createUserId = (Long)params.get("createUserId");

		IPage<SysUserEntity> page = this.page(
			new Query<SysUserEntity>(params).getPage(),
			new QueryWrapper<SysUserEntity>()
				.like(StringUtils.isNotBlank(username),"username", username)
				.eq(createUserId != null,"create_user_id", createUserId)
		);

		return new PageUtils(page);
	}

	@Override
	public List<String> queryAllPerms(Long userId) {
		return baseMapper.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return baseMapper.queryAllMenuId(userId);
	}

	@Override
	public SysUserEntity queryByUserName(String username) {
		return baseMapper.queryByUserName(username);
	}

	@Override
	@Transactional
	public void saveSysUser(SysUserEntity user, HttpServletRequest request) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
		user.setSalt(salt);
		user.setLocale("zh-CN");
		this.save(user);

		//检查角色是否越权
		checkRole(user,request);

		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void update(SysUserEntity user,HttpServletRequest request) {
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
		}
		this.updateById(user);

		//检查角色是否越权
		checkRole(user,request);

		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	public void deleteBatch(Long[] userId) {
		this.removeByIds(Arrays.asList(userId));
	}

	@Override
	public boolean updatePassword(Long userId, String password, String newPassword) {
		SysUserEntity userEntity = new SysUserEntity();
		userEntity.setPassword(newPassword);
		return this.update(userEntity,
				new QueryWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
	}

	@Override
	public void resetPassword(Long userId) {
		SysUserEntity sysUserEntity=this.getById(userId);
		String password = new Sha256Hash("admin", sysUserEntity.getSalt()).toHex();
		SysUserEntity userEntity = new SysUserEntity();
		userEntity.setPassword(password);
		this.update(userEntity,
				new QueryWrapper<SysUserEntity>().eq("user_id", userId));
	}

	@Override
	public void updateBatchStatus(Long id,HttpServletRequest request) {
		if(id==1l){
			throw new RRException(messageUtils.getMessage("manage.disabled.users", request));
		}
		this.baseMapper.updateBatchStatus(id);
	}

	/**
	 * 检查角色是否越权
	 */
	private void checkRole(SysUserEntity user,HttpServletRequest request){
		if(user.getRoleIdList() == null || user.getRoleIdList().size() == 0){
			return;
		}
		//如果不是超级管理员，则需要判断用户的角色是否自己创建
		if(user.getCreateUserId() == Constant.SUPER_ADMIN){
			return ;
		}

		//查询用户创建的角色列表
		List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

		//判断是否越权
		if(!roleIdList.containsAll(user.getRoleIdList())){
			throw new RRException(messageUtils.getMessage("manage.exceeding.error", request));
		}
	}

	/**
	 * 修改前端语言
	 *
	 * @param sysUserId
	 * @param locale
	 */
	@Override
	public void updateLocale(Long sysUserId, String locale) {
		SysUserEntity userEntity = new SysUserEntity();
		userEntity.setUserId(sysUserId);
		userEntity.setLocale(locale);
		this.updateById(userEntity);
	}
}
