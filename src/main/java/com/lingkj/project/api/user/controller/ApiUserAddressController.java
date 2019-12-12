package com.lingkj.project.api.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.utils.R;
import com.lingkj.project.api.operation.dto.AreasAddressDto;
import com.lingkj.project.operation.entity.OperateAreas;
import com.lingkj.project.operation.entity.OperateAreasPostalCode;
import com.lingkj.project.operation.service.OperateAreasPostalCodeService;
import com.lingkj.project.operation.service.OperateAreasService;
import com.lingkj.project.user.dto.ReceivingAddressRespDto;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.entity.UserReceivingAddress;
import com.lingkj.project.user.service.AdminUserService;
import com.lingkj.project.user.service.UserReceivingAddressService;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Api(value = "用户收货地址接口")
@RestController
@RequestMapping("/api/user/address")
public class ApiUserAddressController {
    @Autowired
    private OperateAreasPostalCodeService operateAreasPostalCodeService;
    @Autowired
    private OperateAreasService operateAreasService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private UserReceivingAddressService userReceivingAddressService;

    /**
     * 邮政编码查询地址列表
     * @param params
     * @return
     */
    @PostMapping("/queryPostalCode")
    public R queryPostalCode(@RequestBody Map<String, Object> params){
        List<AreasAddressDto> provinceList=new ArrayList<>();
        String code = (String) params.get("code");
        if(StringUtils.isNotBlank(code)){
            List<OperateAreasPostalCode> operateAreasPostalCodes = operateAreasPostalCodeService.selectByPostalCode(code);
            for (OperateAreasPostalCode operateAreasPostalCode : operateAreasPostalCodes){
                OperateAreas operateAreas = operateAreasService.getById(operateAreasPostalCode.getAreasId());
                setList(provinceList, operateAreas);
            }
        }else {
            List<OperateAreas> operateAreas = operateAreasService.selectParentId(Long.valueOf(1));
            for (OperateAreas operateAreas1 : operateAreas){
                setList(provinceList, operateAreas1);
            }
        }

        return R.ok().put("provinceList",provinceList);
    }

    //判断当前对象是省级 还是城市  为集合赋值

    private void setList(List<AreasAddressDto> provinceList, OperateAreas operateAreas) {
        if(operateAreas.getLevel()==2){
            AreasAddressDto areasAddressDto=new AreasAddressDto();
            areasAddressDto.setProvinceId(operateAreas.getId());
            areasAddressDto.setProvinceName(operateAreas.getName());
            List<OperateAreas> children =operateAreasService.selectParentId(operateAreas.getId());
            areasAddressDto.setCity(children);
            provinceList.add(areasAddressDto);
        }else if(operateAreas.getLevel()!=1){
            if(operateAreas.getParentId()!=null ){
                if(provinceList.size()>0){
                    for (AreasAddressDto areasAddressDto1: provinceList){
                        if( areasAddressDto1.getProvinceId()!=operateAreas.getParentId()){
                            getChildren(provinceList, operateAreas);
                        }else {
                            areasAddressDto1.getCity().add(operateAreas);
                        }
                    }
                }else {
                    getChildren(provinceList, operateAreas);
                }
            }else {
                setNoProvince(provinceList);
            }
        }
    }


    //通过城市id  找到相对应的省级信息
    private void getChildren(List<AreasAddressDto> provinceList, OperateAreas operateAreas) {
        AreasAddressDto areasAddressDto = new AreasAddressDto();
        OperateAreas operateAreas1 = operateAreasService.getById(operateAreas.getParentId());
        areasAddressDto.setProvinceId(operateAreas1.getId());
        areasAddressDto.setProvinceName(operateAreas1.getName());
        List<OperateAreas> provinceCity = new ArrayList<>();
        provinceCity.add(operateAreas);
        areasAddressDto.setCity(provinceCity);
        provinceList.add(areasAddressDto);
    }
    //如何没有省级 则创建单个城市集合
    private void setNoProvince(List<AreasAddressDto> provinceList) {
        AreasAddressDto a1=new AreasAddressDto();
        List<OperateAreas> noProvince=new ArrayList<>();
        a1.setCity(noProvince);
        provinceList.add(a1);
    }


    //增加或修改用户收货地址
    @PostMapping("/saveOruUpdateAddress")
    @Login
    public R saveDesigner(@RequestBody UserReceivingAddress userReceivingAddress, @RequestAttribute("userId") Long userId) {
        User byId = adminUserService.getById(userId);
        if (userReceivingAddress.getId() == null) {
            userReceivingAddress.setUserId(userId);
            userReceivingAddress.setStatus(0);
            userReceivingAddress.setIsDefault(1);
            userReceivingAddress.setCreateTime(new Date());
            userReceivingAddress.setEmail(byId.getUserName());
            userReceivingAddressService.save(userReceivingAddress);
        } else {
            userReceivingAddressService.updateById(userReceivingAddress);
        }
        return R.ok();
    }

    //查询用所有可用收货地址
    @GetMapping("/queryUserAddress")
    @Login
    public R queryUserAddress(@RequestAttribute("userId") Long userId) {
        List<ReceivingAddressRespDto> receivingAddressRespDtos = userReceivingAddressService.queryList(userId);
        return R.ok().put("userAddress", receivingAddressRespDtos);
    }

    //查询收货地址详情
    @GetMapping("/queryUserAddressOne")
    public R queryUserAddressOne(@RequestParam("id") Long id) {
        UserReceivingAddress userReceivingAddress = userReceivingAddressService.getById(id);
        return R.ok().put("userReceivingAddress", userReceivingAddress);
    }

    //修改地址状态，默认或者非默认
    @GetMapping("/updateAddressDefault")
    @Login
    public R updateAddressDefault(@RequestParam("id") Long id, @RequestAttribute("userId") Long userId) {
        userReceivingAddressService.updateDefault(id, userId);
        return R.ok();
    }

    //删除地址
    @GetMapping("/deleteUserAddress")
    public R deleteUserAddress(@RequestParam("id") Long id) {
        userReceivingAddressService.removeById(id);
        return R.ok();
    }
    //查询默认地址
    @GetMapping("/isDefaultAddress")
    @Login
    public R isDefaultAddress(@RequestAttribute("userId") Long userId){
        ReceivingAddressRespDto dto = userReceivingAddressService.queryDefault(userId);
        return R.ok().put("address",dto);

    }

}
