package com.lingkj.project.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.api.operation.dto.AdvertisementDto;
import com.lingkj.project.operation.dto.AdvertisementRespDto;
import com.lingkj.project.operation.entity.OperateAdvertisement;
import com.lingkj.project.operation.mapper.OperateAdvertisementMapper;
import com.lingkj.project.operation.service.OperateAdvertisementService;
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
public class OperateAdvertisementServiceImpl extends ServiceImpl<OperateAdvertisementMapper, OperateAdvertisement> implements OperateAdvertisementService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String title = (String) params.get("title");
        Integer position=null;
        if(StringUtils.isNotBlank((String)params.get("position"))){
            position=Integer.valueOf((String)params.get("position"));
        }
        QueryWrapper wrapper = new QueryWrapper<OperateAdvertisement>();
        if(position!=null){
            wrapper.eq("position", position);
        }
        if (StringUtils.isNotBlank(title)) {
            wrapper.like("title", title);
        }
        wrapper.eq("status", 0);
        wrapper.orderByDesc("create_time");
        IPage<OperateAdvertisement> page = this.page(
                new Query<OperateAdvertisement>(params).getPage(),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void updateStatusByIds(List<Long> asList) {
        baseMapper.updateStatusByIds(asList);
    }

    @Override
    public List<AdvertisementRespDto> selectListByStatus() {
        return baseMapper.selectListByStatus();
    }

    @Override
    public List<AdvertisementDto> queryAdvertisementRespDtoList(Integer position,Integer type) {
        return baseMapper.selectListByTypeAndPosition(position,type);
    }

    @Override
    public List<Map<String, Object>> getTypeList() {
        return baseMapper.getTypeList();
    }

}
