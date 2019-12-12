package com.lingkj.project.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.operation.entity.OperateRate;
import com.lingkj.project.operation.mapper.OperateRateMapper;
import com.lingkj.project.operation.service.OperateRateService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 费率表
 *
 * @author chenyongsong
 * @date 2019-10-10 09:15:52
 */
@Service
public class OperateRateServiceImpl extends ServiceImpl<OperateRateMapper, OperateRate> implements OperateRateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<OperateRate> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank((String)params.get("type"))){
            wrapper.eq("type",params.get("type"));
        }
        IPage<OperateRate> page = this.page(
                new Query<OperateRate>(params).getPage(),
                wrapper);


        return new PageUtils(page);
    }

    @Override
    public OperateRate selectByType(Integer type) {
        return baseMapper.selectByType(type);
    }

}
