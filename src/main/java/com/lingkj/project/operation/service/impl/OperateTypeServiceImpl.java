package com.lingkj.project.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.operation.entity.OperateType;
import com.lingkj.project.operation.mapper.OperateTypeMapper;
import com.lingkj.project.operation.service.OperateTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 供应商 供应产品 类型
 *
 * @author chenyongsong
 * @date 2019-09-12 11:54:40
 */
@Service
public class OperateTypeServiceImpl extends ServiceImpl<OperateTypeMapper, OperateType> implements OperateTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OperateType> page = this.page(
                new Query<OperateType>(params).getPage(),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<OperateType> selectBatchIds(List<String> asList) {
        return this.baseMapper.selectBatchIds(asList);
    }

    @Override
    public List<OperateType> selectType(Integer type) {
        QueryWrapper<OperateType> wrapper=new QueryWrapper<>();
        wrapper.eq("type",type);
       return  this.list(wrapper);

    }

}
