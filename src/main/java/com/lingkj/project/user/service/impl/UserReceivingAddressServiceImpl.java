package com.lingkj.project.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.authentication.paramsvalidation.ReceivingAddressForm;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.operation.entity.OperateAreas;
import com.lingkj.project.operation.service.OperateAreasService;
import com.lingkj.project.user.dto.ReceivingAddressRespDto;
import com.lingkj.project.user.entity.UserReceivingAddress;
import com.lingkj.project.user.mapper.UserReceivingAddressMapper;
import com.lingkj.project.user.service.UserReceivingAddressService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 */
@Service
public class UserReceivingAddressServiceImpl extends ServiceImpl<UserReceivingAddressMapper, UserReceivingAddress> implements UserReceivingAddressService {
    @Autowired
    private OperateAreasService operateAreasService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        params.put("sidx", "create_time");
        params.put("order", "DESC");
        IPage<UserReceivingAddress> page = this.page(
                new Query<UserReceivingAddress>(params).getPage(),
                new QueryWrapper<UserReceivingAddress>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<ReceivingAddressRespDto> queryList(Long userId) {
        List<ReceivingAddressRespDto> addressRespDtoList = baseMapper.queryAddressList(userId);
        addressRespDtoList.forEach(receivingAddressRespDto -> {
            if (areaName(receivingAddressRespDto)) return;
        });
        return addressRespDtoList;
    }

    @Override
    public void saveOrUpdate(ReceivingAddressForm form, Long userId) {
        if (form.getIsDefault() == 2) {
            baseMapper.updateDefaultByUserId(userId);
        }
        UserReceivingAddress userReceivingAddress = new UserReceivingAddress();
        BeanUtils.copyProperties(form, userReceivingAddress);
        userReceivingAddress.setIsDefault(form.getIsDefault());
        userReceivingAddress.setAddress(form.getAddress());
        userReceivingAddress.setName(form.getReceiveName());
        userReceivingAddress.setPhone(form.getReceivePhone());
        userReceivingAddress.setUserId(userId);
        if (form.getId() == null) {
            userReceivingAddress.setCreateTime(new Date());
            userReceivingAddress.setStatus(0);
            this.save(userReceivingAddress);
            return;
        }

        this.updateById(userReceivingAddress);


    }

    @Override
    public void updateStatus(Long id, Long userId) {
        UserReceivingAddress userReceivingAddress = new UserReceivingAddress();
        userReceivingAddress.setId(id);
        userReceivingAddress.setUserId(userId);
        userReceivingAddress.setStatus(1);
        this.updateById(userReceivingAddress);
    }

    @Override
    public void updateDefault(Long id, Long userId) {
        UserReceivingAddress userReceivingAddress = this.getById(id);
        // IsDefault=1时 设置当前地址为默认收货地址
        if (userReceivingAddress.getIsDefault() == 1) {
            baseMapper.updateDefaultByUserId(userId);
            userReceivingAddress.setIsDefault(2);
        } else {
            userReceivingAddress.setIsDefault(1);
        }
        this.updateById(userReceivingAddress);
    }

    @Override
    public ReceivingAddressRespDto selectRespDtoById(Long userId, Long addressId) {
        ReceivingAddressRespDto addressRespDtoList = baseMapper.queryRespDtoById(userId, addressId);
        if (areaName(addressRespDtoList)) return null;
        return addressRespDtoList;
    }

    @Override
    public ReceivingAddressRespDto queryDefault(Long userId) {
        return  this.baseMapper.queryDefault(userId);
    }

//    @Override
//    public ReceivingAddressRespDto queryDefault(Long userId, Long addressId) {
//        ReceivingAddressRespDto addressRespDtoList;
//        if (addressId == null) {
//            addressRespDtoList = baseMapper.queryDefault(userId);
//        } else {
//            addressRespDtoList = baseMapper.queryRespDtoById(addressId, userId);
//        }
//        if (areaName(addressRespDtoList)) return null;
//        return addressRespDtoList;
//    }

    @Override
    public Integer queryAddressCount(Long userId) {
        QueryWrapper<UserReceivingAddress> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id ", userId);
        wrapper.eq("status ", 0);
        return this.baseMapper.selectCount(wrapper);
    }

    private boolean areaName(ReceivingAddressRespDto addressRespDto) {
        if (addressRespDto == null) return false;
        if (addressRespDto.getProvince() != null && addressRespDto.getProvince() != 1) {
            OperateAreas operateAreas = operateAreasService.getById(addressRespDto.getProvince());
            if (operateAreas == null) {
                addressRespDto.setAreaCodeName((StringUtils.isBlank(addressRespDto.getAreaCodeName()) ? "" : addressRespDto.getAreaCodeName()) + "");
            } else {
                addressRespDto.setProvinceStr(operateAreas.getName());
                addressRespDto.setAreaCodeName((StringUtils.isBlank(addressRespDto.getAreaCodeName()) ? "" : addressRespDto.getAreaCodeName()) + operateAreas.getName());
            }
        }
        if (addressRespDto.getCity() != null && addressRespDto.getCity() != 1) {
            OperateAreas operateAreas = operateAreasService.getById(addressRespDto.getCity());
            if (operateAreas == null) {
                addressRespDto.setAreaCodeName((StringUtils.isBlank(addressRespDto.getAreaCodeName()) ? "" : addressRespDto.getAreaCodeName()) + "");
            } else {
                addressRespDto.setCityStr(operateAreas.getName());
                addressRespDto.setAreaCodeName((StringUtils.isBlank(addressRespDto.getAreaCodeName()) ? "" : addressRespDto.getAreaCodeName()) + "-" + operateAreas.getName());
            }
        }
        return false;
    }

}
