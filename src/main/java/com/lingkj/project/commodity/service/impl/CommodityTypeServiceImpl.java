package com.lingkj.project.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.api.commodity.dto.ApiCommodityTypeDto;
import com.lingkj.project.commodity.entity.CommodityType;
import com.lingkj.project.commodity.mapper.CommodityTypeMapper;
import com.lingkj.project.commodity.service.CommodityTypeService;
import com.lingkj.project.sys.entity.SysUserEntity;
import com.lingkj.project.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 */
@Service
public class CommodityTypeServiceImpl extends ServiceImpl<CommodityTypeMapper, CommodityType> implements CommodityTypeService {
    @Autowired
    private SysUserService sysUserService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 获取参数
        String name = (String) params.get("name");
        // 条件
        QueryWrapper<CommodityType> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0);
        if (StringUtils.isNotBlank(name)) {
            wrapper.like("name", name);
        }

        IPage<CommodityType> page = this.page(
                new Query<CommodityType>(params).getPage(),
                wrapper.orderByDesc("create_time"));
        for (CommodityType methodEntity : page.getRecords()) {
            SysUserEntity sysUserEntity = sysUserService.getById(methodEntity.getCreateSysUserId());
            if (sysUserEntity !=null) {
                methodEntity.setCreateSysUserName(sysUserEntity.getUsername());
                if (methodEntity.getUpdateSysUserId() != null) {
                    sysUserEntity = sysUserService.getById(methodEntity.getUpdateSysUserId());
                    methodEntity.setCreateSysUserName(sysUserEntity.getUsername());
                }
            }
        }
        return new PageUtils(page);
    }

    /**
     * 逻辑删除
     *
     * @param ids ids
     */
    @Override
    public void updateBatchIds(Long[] ids) {
        this.baseMapper.updateBatchIds(ids);
    }

    /**
     * 查询所有分类
     *
     * @return
     */
    @Override
    public List<CommodityType> queryList() {
        // 条件
        QueryWrapper<CommodityType> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0);
        return this.list(wrapper.orderByAsc("name"));
    }

    @Override
    public List<CommodityType> selectBatchIds(List<String> asList) {
        return  this.baseMapper.selectBatchIds(asList);
    }

    @Override
    public List<ApiCommodityTypeDto> queryListApi() {
        return this.baseMapper.queryListApi();
    }

    @Override
    public ApiCommodityTypeDto selectTypeDtoById(Long commodityTypeId) {
        return this.baseMapper.selectTypeDtoById(commodityTypeId);
    }
}
