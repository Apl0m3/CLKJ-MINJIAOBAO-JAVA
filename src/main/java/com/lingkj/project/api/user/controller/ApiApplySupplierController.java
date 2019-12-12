package com.lingkj.project.api.user.controller;

import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.utils.R;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.common.validator.Assert;
import com.lingkj.project.operation.entity.OperateType;
import com.lingkj.project.operation.service.OperateTypeService;
import com.lingkj.project.user.dto.UserAndSupplierDto;
import com.lingkj.project.user.dto.UserRespDto;
import com.lingkj.project.user.entity.UserApplicationFile;
import com.lingkj.project.user.entity.UserBank;
import com.lingkj.project.user.entity.UserSupplierApplication;
import com.lingkj.project.user.service.AdminUserService;
import com.lingkj.project.user.service.UserApplicationFileService;
import com.lingkj.project.user.service.UserBankService;
import com.lingkj.project.user.service.UserSupplierApplicationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


import java.util.Date;
import java.util.List;

@Api(value = "供应商申请接口")
@RestController
@RequestMapping("/api/applySupplier")
public class ApiApplySupplierController {
    @Autowired
    private OperateTypeService operateTypeService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private UserSupplierApplicationService userSupplierApplicationService;
    @Autowired
    private UserApplicationFileService userApplicationFileService;
    @Autowired
    private UserBankService userBankService;
    @Autowired
    private MessageUtils messageUtils;

    @GetMapping("/queryOperateTypesId")
    @Login
    private R queryOperateTypesId() {
        List<OperateType> operateTypes = operateTypeService.selectType(2);
        return R.ok().put("operateTypes", operateTypes);
    }

    /**
     * 查询当前用户申请信息
     * @param userId
     * @return
     */
    @GetMapping("/queryUserSupplier")
    @Login
    public R queryUserSupplier(@RequestAttribute("userId") Long userId) {
        UserAndSupplierDto userAndSupplierDto = new UserAndSupplierDto();
        UserRespDto userRespDto = adminUserService.queryPersonInfo(userId);
        UserSupplierApplication userSupplierApplication = userSupplierApplicationService.selectByUserId(userId);
        if (userSupplierApplication != null) {
            UserApplicationFile userApplicationFile = userApplicationFileService.getUserApplicationFileOne(userSupplierApplication.getId(),2);
            UserBank userBank = userBankService.getUserBank(userId, userSupplierApplication.getId(),2);
            userAndSupplierDto.setUserSupplierApplication(userSupplierApplication);
            userAndSupplierDto.setUserApplicationFile(userApplicationFile);
            userAndSupplierDto.setUserBank(userBank);
        }
        userAndSupplierDto.setUserRespDto(userRespDto);
        return R.ok().put("userAndSupplierDto", userAndSupplierDto);
    }

    //保存或修改申请信息
    //申请供应商保存与修改信息
    @PostMapping("/saveOrUpdateSupplier")
    @Login
    public R saveOrUpdateSupplier(@RequestBody UserAndSupplierDto userAndSupplierDto, @RequestAttribute("userId") Long userId, HttpServletRequest request) {
        UserSupplierApplication userSupplierApplication = userAndSupplierDto.getUserSupplierApplication();
        UserApplicationFile applicationFile = userAndSupplierDto.getUserApplicationFile();
        Assert.isBlank(userSupplierApplication.getCompanyName(),messageUtils.getMessage("api.user.apply.supplier.companyName", request),400);
        Assert.isBlank(userSupplierApplication.getName(),messageUtils.getMessage("api.user.apply.supplier.name", request),400);
        Assert.isBlank(userSupplierApplication.getTypeIds(),messageUtils.getMessage("api.user.apply.supplier.type", request),400);
        Assert.isBlank(applicationFile.getUrl(),messageUtils.getMessage("api.user.apply.supplier.url", request),400);
        userSupplierApplicationService.saveSupplier(userAndSupplierDto,userId,request);
        return R.ok();

    }


}
