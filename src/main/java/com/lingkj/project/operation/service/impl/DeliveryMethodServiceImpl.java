package com.lingkj.project.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.DateUtils;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.operation.mapper.DeliveryMethodMapper;
import com.lingkj.project.operation.entity.OperateDeliveryMethod;
import com.lingkj.project.operation.service.DeliveryMethodService;
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
@Service("deliveryMethodService")
public class DeliveryMethodServiceImpl extends ServiceImpl<DeliveryMethodMapper, OperateDeliveryMethod> implements DeliveryMethodService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private DeliveryMethodMapper deliveryMethodMapper;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        params.put("sidx","create_time");
        params.put("order","DESC");
        IPage<OperateDeliveryMethod> page = this.page(
                new Query<OperateDeliveryMethod>(params).getPage(),
                new QueryWrapper<OperateDeliveryMethod>()
        );
        for (OperateDeliveryMethod methodEntity : page.getRecords()) {
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
    public void saveOrUpdate(OperateDeliveryMethod deliveryMethod, Long sysUserId) {
        Date currentTime = DateUtils.current();
        if (deliveryMethod.getId() == null) {
            deliveryMethod.setCreateSysUserId(sysUserId);
            deliveryMethod.setCreateTime(currentTime);
            deliveryMethod.setStatus(0);
            save(deliveryMethod);
        } else {
            deliveryMethod.setUpdateSysUserId(sysUserId);
            deliveryMethod.setUpdateTime(currentTime);
            updateById(deliveryMethod);
        }
    }

    @Override
    public void updateStatusByIds(List<Long> asList) {
        deliveryMethodMapper.updateStatusByIds(asList);
    }

}
