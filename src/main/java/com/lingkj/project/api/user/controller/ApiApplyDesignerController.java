package com.lingkj.project.api.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.R;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.common.validator.Assert;
import com.lingkj.project.operation.dto.OperateTypeAndUserDto;
import com.lingkj.project.operation.entity.OperateType;
import com.lingkj.project.operation.service.OperateTypeService;
import com.lingkj.project.user.dto.UserDesignerApplicationDto;
import com.lingkj.project.user.dto.UserRespDto;
import com.lingkj.project.user.entity.*;
import com.lingkj.project.user.service.AdminUserService;
import com.lingkj.project.user.service.UserApplicationFileService;
import com.lingkj.project.user.service.UserBankService;
import com.lingkj.project.user.service.UserDesignerApplicationService;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(value = "设计师申请接口")
@RestController
@RequestMapping("/api/applyDesigner")
public class ApiApplyDesignerController {
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private UserApplicationFileService userApplicationFileService;
    @Autowired
    private OperateTypeService operateTypeService;
    @Autowired
    private UserDesignerApplicationService userDesignerApplicationService;
    @Autowired
    private UserBankService userBankService;
    @Autowired
    private MessageUtils messageUtils;

    //申请设计师相关技能
    @GetMapping("/queryOperateType")
    @Login
    public R queryOperateType(@RequestAttribute("userId")Long userId){
        List<OperateTypeAndUserDto> types=new ArrayList<>();
        List<OperateType> operateTypes = operateTypeService.selectType(3);
        UserDesignerApplication userDesignerApplication = userDesignerApplicationService.getUserDesignerApplicationOne(userId);
        if(userDesignerApplication!=null){
            if(StringUtils.isNotBlank(userDesignerApplication.getTypeIds())){
                String[] strings = userDesignerApplication.getTypeIds().split(",");
                for (OperateType operateType: operateTypes){
                    OperateTypeAndUserDto operateTypeAndUserDto=new OperateTypeAndUserDto();
                    operateTypeAndUserDto.setId(operateType.getId());
                    operateTypeAndUserDto.setName(operateType.getName());
                    operateTypeAndUserDto.setStatus(0);
                    for (String string:strings){
                        if(operateType.getId().equals(Long.valueOf(string))){
                            operateTypeAndUserDto.setStatus(1);
                        }
                    }
                    types.add(operateTypeAndUserDto);
                }
            }else {
                for (OperateType operateType: operateTypes) {
                    OperateTypeAndUserDto operateTypeAndUserDto = new OperateTypeAndUserDto();
                    operateTypeAndUserDto.setId(operateType.getId());
                    operateTypeAndUserDto.setName(operateType.getName());
                    operateTypeAndUserDto.setStatus(0);
                    types.add(operateTypeAndUserDto);
                }
            }
        }else {
            for (OperateType operateType: operateTypes) {
                OperateTypeAndUserDto operateTypeAndUserDto = new OperateTypeAndUserDto();
                operateTypeAndUserDto.setId(operateType.getId());
                operateTypeAndUserDto.setName(operateType.getName());
                operateTypeAndUserDto.setStatus(0);
                types.add(operateTypeAndUserDto);
            }
        }

        return R.ok().put("operateTypes",types);
    }


    /**
     * 查询申请相关信息
     * @param userId
     * @return
     */
    @GetMapping("/queryUserDesigner")
    @Login
    public  R queryUserDesigner(@RequestAttribute("userId")Long userId){
        UserDesignerApplicationDto userDesignerApplicationDto=new UserDesignerApplicationDto();
        UserRespDto userRespDto = adminUserService.queryPersonInfo(userId);
        UserDesignerApplication userDesignerApplication = userDesignerApplicationService.getUserDesignerApplicationOne(userId);
        if(userDesignerApplication!=null){
            List<UserApplicationFile> userApplicationFiles = userApplicationFileService.getUserAndUserApplicationFileList(userDesignerApplication.getId(),1);
             UserBank userBank= userBankService.getUserBank(userId,userDesignerApplication.getId(),1);
            userDesignerApplicationDto.setUserApplicationFiles(userApplicationFiles);
            userDesignerApplicationDto.setUserDesignerApplication(userDesignerApplication);
            userDesignerApplicationDto.setUserBank(userBank);
        }
        userDesignerApplicationDto.setUserRespDto(userRespDto);
        return R.ok().put("userDesignerApplicationDto",userDesignerApplicationDto);
    }
    /**
     * 申请设计师保存与修改信息
     * @param userDesignerApplicationDto
     * @param userId
     * @return
     */
    @PostMapping("/saveOrUpdateDesigner")
    @Login
    public R saveOrUpdateDesigner(@RequestBody UserDesignerApplicationDto userDesignerApplicationDto, @RequestAttribute("userId")Long userId, HttpServletRequest request){
        UserDesignerApplication userDesignerApplication = userDesignerApplicationDto.getUserDesignerApplication();
        List<UserApplicationFile> userApplicationFiles = userDesignerApplicationDto.getUserApplicationFiles();
        Assert.isBlank(userDesignerApplication.getName(),messageUtils.getMessage("api.user.apply.designer.name", request),400);
        Assert.isBlank(userDesignerApplication.getResumeUrl(),messageUtils.getMessage("api.user.apply.designer.url", request),400);
        Assert.isBlank(userDesignerApplication.getTypeIds(),messageUtils.getMessage("api.user.apply.designer.type", request),400);
        if(userApplicationFiles==null||userApplicationFiles.size()<=0){
            throw new RRException(messageUtils.getMessage("api.user.apply.designer.file", request),400);
        }

        userDesignerApplicationService.saveDesigner(userDesignerApplicationDto,userId,request);
        return R.ok();
    }




}
