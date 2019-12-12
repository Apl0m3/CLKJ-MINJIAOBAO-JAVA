package com.lingkj.project.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.operation.entity.OperateRules;
import com.lingkj.project.operation.mapper.OperateRulesMapper;
import com.lingkj.project.operation.service.OperateRulesService;
import com.lingkj.project.sys.entity.SysUserEntity;
import com.lingkj.project.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 */
@Service("integralRulesService")
public class OperateRulesServiceImpl extends ServiceImpl<OperateRulesMapper, OperateRules> implements OperateRulesService {
    @Autowired
    private SysUserService sysUserService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        params.put("sidx", "create_time");
        params.put("order", "DESC");
        IPage<OperateRules> page = this.page(
                new Query<OperateRules>(params).getPage(),
                new QueryWrapper<>()
        );
        for (OperateRules methodEntity : page.getRecords()) {
            SysUserEntity sysUserEntity = sysUserService.getById(methodEntity.getCreateSysUserId());
            methodEntity.setCreateSysUserName(sysUserEntity.getUsername());
            if (methodEntity.getUpdateSysUserId() != null) {
                sysUserEntity = sysUserService.getById(methodEntity.getUpdateSysUserId());
                methodEntity.setCreateSysUserName(sysUserEntity.getUsername());
            }
        }
        return new PageUtils(page);
    }

    @Override
    public void updateStatusByIds(List<Long> asList) {
        baseMapper.updateStatusByIds(asList);
    }

    @Override
    public OperateRules selectByType(Integer ruleType, Integer type) {
        QueryWrapper<OperateRules> wrapper = new QueryWrapper<OperateRules>();
        wrapper.eq("type", type).eq("rule_type", ruleType);
        return this.getOne(wrapper);
    }

    @Override
    public OperateRules selectOneByType(Integer type, Integer ruleType) {
        return this.baseMapper.getOneByType(type,ruleType);
    }

}
