package com.lingkj.project.transaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.common.validator.Assert;
import com.lingkj.project.api.transaction.dto.ApiTransactionReceivingAddressDto;
import com.lingkj.project.operation.entity.OperateAreas;
import com.lingkj.project.operation.service.OperateAreasService;
import com.lingkj.project.transaction.entity.TransactionReceivingAddress;
import com.lingkj.project.transaction.mapper.TransactionReceivingAddressMapper;
import com.lingkj.project.transaction.service.TransactionReceivingAddressService;
import com.lingkj.project.user.service.impl.UserReceivingAddressServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
@Service
public class TransactionReceivingAddressServiceImpl extends ServiceImpl<TransactionReceivingAddressMapper, TransactionReceivingAddress> implements TransactionReceivingAddressService {
    @Autowired
    private OperateAreasService operateAreasService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TransactionReceivingAddress> page = this.page(
                new Query<TransactionReceivingAddress>(params).getPage(),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public ApiTransactionReceivingAddressDto selectByRecordId(Long recordId) {
        ApiTransactionReceivingAddressDto addressDto = this.baseMapper.selectByRecordId(recordId);
        Assert.isNull(addressDto,"该订单不存在",400);
        if (addressDto.getProvince() != null && addressDto.getProvince() != 1) {
            OperateAreas operateAreas = operateAreasService.getById(addressDto.getProvince());
                addressDto.setProvinceStr(operateAreas.getName());
        }
        if (addressDto.getCity() != null && addressDto.getCity() != 1) {
            OperateAreas operateAreas = operateAreasService.getById(addressDto.getCity());
                addressDto.setCityStr(operateAreas.getName());
        }

        return addressDto;
    }


}
