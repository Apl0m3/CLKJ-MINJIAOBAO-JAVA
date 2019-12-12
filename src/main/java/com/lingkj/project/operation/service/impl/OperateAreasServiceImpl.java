package com.lingkj.project.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.project.api.operation.dto.AreasDto;
import com.lingkj.project.operation.dto.AreasReqDto;
import com.lingkj.project.operation.entity.OperateAreas;
import com.lingkj.project.operation.entity.OperateAreasPostalCode;
import com.lingkj.project.operation.mapper.OperateAreasMapper;
import com.lingkj.project.operation.service.OperateAreasPostalCodeService;
import com.lingkj.project.operation.service.OperateAreasService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 */
@Service
public class OperateAreasServiceImpl extends ServiceImpl<OperateAreasMapper, OperateAreas> implements OperateAreasService {
    @Autowired
    private OperateAreasPostalCodeService postalCodeService;
    @Autowired
    private MessageUtils messageUtils;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OperateAreas> page = this.page(
                new Query<OperateAreas>(params).getPage(),
                new QueryWrapper<OperateAreas>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<OperateAreas> selectListById(AreasReqDto areasReqDto) {
        return this.baseMapper.selectListById(areasReqDto.getParentId(), areasReqDto.getLevel());
    }

    @Override
    public String getAreaName(String areasCode) {
        StringBuilder areaCodeName = new StringBuilder();
        if (StringUtils.isNotBlank(areasCode)) {
            String[] strings = areasCode.split("_");
            for (String string : strings) {
                if (StringUtils.isNotBlank(string)) {
                    OperateAreas operateAreas = this.getById(string);
                    if (operateAreas == null) {
                        return null;
                    }
                    areaCodeName.append(operateAreas.getName());
                }
            }
        }
        return areaCodeName.toString();
    }

    @Override
    public List<AreasDto> queryByPostalCode(String postalCode, HttpServletRequest request) {
        List<AreasDto> areasDtoList=new ArrayList<>();
        AreasDto areasDto = new AreasDto();
        List<OperateAreasPostalCode> areasPostalCodeList = this.postalCodeService.selectByPostalCode(postalCode);
        for (OperateAreasPostalCode areasPostalCode : areasPostalCodeList) {
            if (areasPostalCode == null) {
                throw new RRException(messageUtils.getMessage("api.operate.areas.postalCode.isEmpty", request));
            }
            OperateAreas city = this.baseMapper.selectById(areasPostalCode.getAreasId());
            if (city == null) {
                throw new RRException(messageUtils.getMessage("api.operate.areas.isEmpty", request));
            }
            OperateAreas province = this.baseMapper.selectById(city.getParentId());
            if (province == null) {
                throw new RRException(messageUtils.getMessage("api.operate.areas.isEmpty", request));
            }
            areasDtoList.add(areasDto);
        }

        return areasDtoList;
    }

    @Override
    public List<OperateAreas> selectParentId(Long ParentId) {
        QueryWrapper<OperateAreas> wrapper=new QueryWrapper<>();
        wrapper.eq("parent_id",ParentId);
        return  this.list(wrapper);
    }


}
