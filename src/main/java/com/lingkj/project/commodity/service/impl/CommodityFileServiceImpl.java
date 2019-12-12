package com.lingkj.project.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.api.commodity.dto.ApiCommodityFileDto;
import com.lingkj.project.commodity.entity.CommodityFile;
import com.lingkj.project.commodity.mapper.CommodityFileMapper;
import com.lingkj.project.commodity.service.CommodityFileService;
import com.lingkj.project.sys.entity.SysUserEntity;
import com.lingkj.project.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("commodityFileService")
public class CommodityFileServiceImpl extends ServiceImpl<CommodityFileMapper, CommodityFile> implements CommodityFileService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CommodityFileMapper commodityFileMapper;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CommodityFile> page = this.page(
                new Query<CommodityFile>(params).getPage(),
                new QueryWrapper<CommodityFile>()
        );

        for (CommodityFile methodEntity : page.getRecords()) {
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
    public List<ApiCommodityFileDto> selectByCommodityId(Long id) {
        return commodityFileMapper.selectByCommodityId(id);
    }

    @Override
    public CommodityFile[] selectEntityByCommodityId(Long commodityId) {
        return commodityFileMapper.selectEntityByCommodityId(commodityId);
    }

    @Override
    public void deleteNotInBatch(List<CommodityFile> fileUpdList, Long commodityId) {
        commodityFileMapper.deleteNotInBatch(fileUpdList, commodityId);
    }
}
