package com.lingkj.project.api.user.controller;


import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.utils.R;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.common.validator.Assert;
import com.lingkj.project.operation.entity.OperateType;
import com.lingkj.project.operation.service.OperateTypeService;
import com.lingkj.project.user.dto.UserAndAgentApplicationDto;
import com.lingkj.project.user.dto.UserRespDto;
import com.lingkj.project.user.entity.UserAgentApplication;
import com.lingkj.project.user.service.AdminUserService;
import com.lingkj.project.user.service.UserAgentApplicationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Api(value = "代理商申请接口")
@RestController
@RequestMapping("/api/applyAgent")
public class ApiApplyAgentController {
    @Autowired
    private OperateTypeService operateTypeService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private UserAgentApplicationService userAgentApplicationService;
    @Autowired
    private MessageUtils messageUtils;
    @GetMapping("/queryOperateTypeList")
    @Login
    public R queryOperateTypeList(){
        List<OperateType> operateTypes = operateTypeService.selectType(1);
       return  R.ok().put("operateTypes",operateTypes);
    }



    /**
     * 查询当前用户申请信息
     * @param userId
     * @return
     */
    @GetMapping("/queryUserAgent")
    @Login
    public R queryUserAgent(@RequestAttribute("userId") Long userId){
        UserAndAgentApplicationDto userAndAgentApplicationDto =new UserAndAgentApplicationDto();
        UserRespDto userRespDto = adminUserService.queryPersonInfo(userId);

        UserAgentApplication userAgentApplication = userAgentApplicationService.getUserAgentApplicationOne(userId);
        if(userAgentApplication!=null){
            userAndAgentApplicationDto.setUserAgentApplication(userAgentApplication);
        }
        userAndAgentApplicationDto.setUserRespDto(userRespDto);
        return  R.ok().put("userAndAgentApplicationDto",userAndAgentApplicationDto);
    }



    /**
     * 保存或修改申请信息  申请代理商保存与修改信息
     * @param userAndAgentApplicationDto
     * @param userId
     * @return
     */
    @PostMapping("/saveOrUpdateAgent")
    @Login
    public  R saveOrUpdateAgent(@RequestBody UserAndAgentApplicationDto userAndAgentApplicationDto, @RequestAttribute("userId")Long userId, HttpServletRequest request){
        UserAgentApplication userAgentApplication = userAndAgentApplicationDto.getUserAgentApplication();
        Assert.isNull(userAgentApplication.getType(),messageUtils.getMessage("api.user.apply.agent.type", request),400);
        Assert.isBlank(userAgentApplication.getBusinessLicenseUrl(),messageUtils.getMessage("api.user.apply.agent.url", request),400);
        userAgentApplicationService.saveAgent(userAndAgentApplicationDto,userId,request);
        return R.ok();
    }



}
