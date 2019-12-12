package com.lingkj.project.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.DateUtils;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.api.operation.dto.ConcatUsDto;
import com.lingkj.project.operation.entity.OperateProject;
import com.lingkj.project.operation.mapper.ProjectMapper;
import com.lingkj.project.operation.service.ProjectService;
import com.lingkj.project.sys.entity.SysUserEntity;
import com.lingkj.project.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 */
@Service("projectService")
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, OperateProject> implements ProjectService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        params.put("sidx","create_time");
        params.put("order","DESC");
        IPage<OperateProject> page = this.page(
                new Query<OperateProject>(params).getPage(),
                new QueryWrapper<OperateProject>()
        );
        for (OperateProject methodEntity : page.getRecords()) {
            SysUserEntity entity = sysUserService.getById(methodEntity.getCreateSysUserId());
            methodEntity.setCreateSysUserName(entity.getUsername());
            if (methodEntity.getUpdateSysUserId() != null) {
                entity = sysUserService.getById(methodEntity.getUpdateSysUserId());
                methodEntity.setCreateSysUserName(entity.getUsername());
            }

        }
        return new PageUtils(page);
    }

    @Override
    public void saveOrUpdate(OperateProject project, Long sysUserId) {
        Date current = DateUtils.current();
        if (project.getId() == null) {
            project.setCreateSysUserId(sysUserId);
            project.setCreateTime(current);
            this.save(project);
        } else {
            project.setUpdateSysUserId(sysUserId);
            project.setUpdateTime(current);
            this.updateById(project);
        }
    }

    @Override
    public List<ConcatUsDto> queryList() {
        List<ConcatUsDto> concatUsDtos = baseMapper.queryProjectList();
        return concatUsDtos;
    }

}
