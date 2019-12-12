package com.lingkj.project.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.api.operation.dto.WorkUsDto;
import com.lingkj.project.operation.entity.OperatePartner;
import com.lingkj.project.operation.mapper.OperatePartnerMapper;
import com.lingkj.project.operation.service.OperatePartnerService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.lingkj.common.utils.ShiroUtils.getSysUserId;

/**
 * 合作伙伴
 *
 * @author chenyongsong
 * @date 2019-09-20 14:44:16
 */
@Service
public class OperatePartnerServiceImpl extends ServiceImpl<OperatePartnerMapper, OperatePartner> implements OperatePartnerService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OperatePartner> page = this.page(
                new Query<OperatePartner>(params).getPage(),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveOrUpdatePartner(OperatePartner operatePartner) {
        Long id = operatePartner.getId();
        if (id != null) {
//            更新
            operatePartner.setUpdateBy(getSysUserId());
            operatePartner.setUpdateTime(new Date());
            baseMapper.updateById(operatePartner);
        }else {
            operatePartner.setCreateBy(getSysUserId());
            operatePartner.setCreateTime(new Date());
            baseMapper.insert(operatePartner);
        }



    }

    @Override
    public WorkUsDto queryList(String name) {
        return baseMapper.queryPartnerList(name);
    }

}
