package com.lingkj.project.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.api.user.dto.ApiUserInvoiceDto;
import com.lingkj.project.operation.entity.OperateAreas;
import com.lingkj.project.operation.service.OperateAreasService;
import com.lingkj.project.user.entity.UserInvoice;
import com.lingkj.project.user.mapper.UserInvoiceMapper;
import com.lingkj.project.user.service.UserInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 订单 用户发票信息
 *
 * @author chenyongsong
 * @date 2019-09-30 16:44:13
 */
@Service
public class UserInvoiceServiceImpl extends ServiceImpl<UserInvoiceMapper, UserInvoice> implements UserInvoiceService {
    @Autowired
    private OperateAreasService operateAreasService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserInvoice> page = this.page(
                new Query<UserInvoice>(params).getPage(),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public ApiUserInvoiceDto selectByUserId(Long userId) {
        ApiUserInvoiceDto userInvoiceDto = this.baseMapper.selectByUserId(userId);
        if (userInvoiceDto != null) {
            if (userInvoiceDto.getProvince() != null) {
                OperateAreas operateAreas = operateAreasService.getById(userInvoiceDto.getProvince());
                userInvoiceDto.setProvinceStr(operateAreas.getName());
            }
            if (userInvoiceDto.getCity() != null) {
                OperateAreas operateAreas = operateAreasService.getById(userInvoiceDto.getCity());
                userInvoiceDto.setCityStr(operateAreas.getName());
            }
        }
        return userInvoiceDto;
    }

}
