package com.lingkj.project.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.operation.entity.OperateAreasPostalCode;
import com.lingkj.project.operation.mapper.OperateAreasPostalCodeMapper;
import com.lingkj.project.operation.service.OperateAreasPostalCodeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author chenyongsong
 * @date 2019-09-20 14:43:23
 */
@Service
public class OperateAreasPostalCodeServiceImpl extends ServiceImpl<OperateAreasPostalCodeMapper, OperateAreasPostalCode> implements OperateAreasPostalCodeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OperateAreasPostalCode> page = this.page(
                new Query<OperateAreasPostalCode>(params).getPage(),
                new QueryWrapper<OperateAreasPostalCode>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<OperateAreasPostalCode> selectByPostalCode(String postalCode) {

        QueryWrapper<OperateAreasPostalCode> wrapper=new QueryWrapper<>();
        wrapper.eq("postal_code",postalCode);
        return this.baseMapper.selectList(wrapper);
    }

}
