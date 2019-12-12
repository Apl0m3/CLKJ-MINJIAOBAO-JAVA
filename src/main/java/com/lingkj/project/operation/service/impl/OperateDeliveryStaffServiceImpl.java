package com.lingkj.project.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.operation.entity.OperateDeliveryStaff;
import com.lingkj.project.operation.mapper.OperateDeliveryStaffMapper;
import com.lingkj.project.operation.service.OperateDeliveryStaffService;
import com.lingkj.project.sys.entity.SysUserEntity;
import com.lingkj.project.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 配送员信息
 *
 * @author chenyongsong
 * @date 2019-07-19 11:59:54
 */
@Service
public class OperateDeliveryStaffServiceImpl extends ServiceImpl<OperateDeliveryStaffMapper, OperateDeliveryStaff> implements OperateDeliveryStaffService {
    @Autowired
    private SysUserService sysUserService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<OperateDeliveryStaff> deliveryStaffWrapper = new QueryWrapper<>();
        String phone= (String) params.get("phone");
        if(StringUtils.isNotBlank(phone)) deliveryStaffWrapper.like("phone",phone);
        deliveryStaffWrapper.ne("status", 1).like("name", String.valueOf(params.get("name")));
        IPage<OperateDeliveryStaff> page = this.page(
                new Query<OperateDeliveryStaff>(params).getPage(), deliveryStaffWrapper
        );
        for (OperateDeliveryStaff deliveryStaff : page.getRecords()) {
            SysUserEntity entity = sysUserService.getById(deliveryStaff.getCreateSysUserId());
            deliveryStaff.setCreateSysUserName(entity.getUsername());
            if (deliveryStaff.getUpdateSysUserId() != null) {
                entity = sysUserService.getById(deliveryStaff.getUpdateSysUserId());
                deliveryStaff.setCreateSysUserName(entity.getUsername());
            }

        }
        return new PageUtils(page);
    }

    @Override
    public void updateStatusBatchIds(List<Long> asList) {
        baseMapper.updateStatusBatchIds(asList);
    }

}
