package com.lingkj.project.api.user.controller;


import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.DateUtils;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.common.validator.Assert;
import com.lingkj.project.integral.entity.IntegralUser;
import com.lingkj.project.integral.service.UserIntegralService;
import com.lingkj.project.transaction.service.TransactionRecordService;
import com.lingkj.project.user.dto.ApplyCountDto;
import com.lingkj.project.user.dto.UpdateUserPwdReqDto;
import com.lingkj.project.user.dto.UserRespDto;
import com.lingkj.project.user.entity.*;
import com.lingkj.project.user.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lingkj.project.api.user.controller.ApiUserLogonController.passwordVerification;

@Api
@RestController
@RequestMapping("/api/user/information")
public class ApiUserController {


    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private UserMessageService userMessageService;
    @Autowired
    private UserIntegralService userIntegralService;
    @Autowired
    private TransactionRecordService transactionRecordService;
    @Autowired
    private UserCollectionCommodityService userCollectionCommodityService;
    @Autowired
    private UserCouponMapService userCouponMapService;


    @Autowired
    private UserApplicationFileService userApplicationFileService;
    @Autowired
    private UserDesignerApplicationService userDesignerApplicationService;
    @Autowired
    private UserAgentApplicationService userAgentApplicationService;
    @Autowired
    private UserSupplierApplicationService userSupplierApplicationService;
    @Autowired
    private MessageUtils messageUtils;


    // 修改个人资料
    @ApiOperation(value = "修改个人资料")
    @PostMapping("/updateInformation")
    @Login
    public R updateInformation(@RequestBody User user, @RequestAttribute("userId") Long userId,HttpServletRequest request) {
        Assert.isBlank(user.getName(), messageUtils.getMessage("api.user.name.null", request), 400);
        user.setId(userId);
        adminUserService.updateById(user);
        return R.ok();
    }

    // 用户信息
    @ApiOperation(value = "个人资料")
    @GetMapping("/info")
    @Login
    public R info(@RequestAttribute("userId") Long userId) {
        UserRespDto userRespDto = adminUserService.queryPersonInfo(userId);
        return R.ok().put("user", userRespDto);
    }

    //用户申请查询
    @GetMapping("/applyCount")
    @Login
    public R applyCount(@RequestAttribute("userId") Long userId) {
        ApplyCountDto applyCountDto = new ApplyCountDto();
        applyCountDto.setAgentCount(userAgentApplicationService.getUserAgentApplicationCount(userId));
        applyCountDto.setDesignerCount(userDesignerApplicationService.getUserDesignerApplicationCount(userId));
        applyCountDto.setSupplierCount(userSupplierApplicationService.getUserSupplierApplicationCount(userId));
        return R.ok().put("applyCountDto", applyCountDto);
    }

    //统计信息
    @ApiOperation(value = "统计")
    @GetMapping("/count")
    @Login
    public R informationCount(@RequestAttribute("userId") Long userId) {
        IntegralUser integralUser = userIntegralService.selectByUserIdForUpdate(userId);
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("readStatus", 0);


        Integer userTransactionCount = transactionRecordService.getUserCount(userId);

        Integer userCollectionCount = userCollectionCommodityService.getUserCollectionIdsCount(userId);

//        String date = DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN);
        Integer userCouponCount = userCouponMapService.getUserCouponMapCount(userId, new Date());
        return R.ok().put("userTotalIntegral", integralUser.getIntegral())
                .put("transactionCount", userTransactionCount)
                .put("userCollectionCount", userCollectionCount)
                .put("couponCount", userCouponCount);
    }

    //立即回复功能
    @PostMapping("/immediateReply")
    public R reply(@RequestBody UserMessage userMessage) {
        userMessage.setCreateTime(new Date());
        userMessage.setReadStatus(0);
        userMessageService.save(userMessage);
        return R.ok();
    }

    /**
     * 修改成已读状态
     *
     * @param id
     * @return
     */
    @Login
    @PostMapping("/updateStatus")
    public R updateStatus(@RequestParam("id") Long id, HttpServletRequest request) {
        Assert.isNull(id,  messageUtils.getMessage("api.user.message.null", request));
        UserMessage userMessage = userMessageService.getById(id);
        userMessage.setReadStatus(1);
        userMessageService.updateById(userMessage);
        return R.ok();
    }

    /**
     * 设计师用户作品
     * @param page
     * @param userId
     * @return
     */
    @PostMapping("/userApplicationFile")
    @Login
    public R getUserApplicationFiles(@RequestBody Page page, @RequestAttribute("userId") Long userId) {
        Integer start = page.getLimit();
        Integer end = page.getPageSize();
        List<UserApplicationFile> userApplicationFiles = userApplicationFileService.getUserApplicationFileList(userId, start, end);
        Integer userApplicationFileCount = userApplicationFileService.getUserApplicationFileCount(userId);
        PageUtils pageUtils = new PageUtils(userApplicationFiles, userApplicationFileCount, page.getPageSize(), page.getPage());
        return R.ok().put("page", pageUtils);
    }


    /**
     * 用户修改密码
     */
    @PostMapping("/updatePassword")
    @Login
    public R resetPassword(@RequestBody UpdateUserPwdReqDto updateUserPwdReqDto, @RequestAttribute("userId") Long userId, HttpServletRequest request) {
        User user = adminUserService.selectInfoById(userId);
        Assert.isNull(updateUserPwdReqDto.getPassword(), messageUtils.getMessage("api.user.validate.password.null", request), 400);
        Assert.isNull(updateUserPwdReqDto.getNewPassword(),  messageUtils.getMessage("api.user.validate.newPassword.null", request), 400);
        Assert.isNull(updateUserPwdReqDto.getAffirmPassword(),  messageUtils.getMessage("api.user.validate.confirmPassword.null", request), 400);
        if (!user.getPassword().equals(DigestUtils.sha256Hex(updateUserPwdReqDto.getPassword()))) {
            return R.error(400, messageUtils.getMessage("api.user.validate.password.error", request));
        }
        if (!updateUserPwdReqDto.getNewPassword().matches(passwordVerification)) {
            return R.error(400, messageUtils.getMessage("api.user.validate.password.verification", request));
        }
        if (!updateUserPwdReqDto.getNewPassword().equals(updateUserPwdReqDto.getAffirmPassword())) {
            return R.error(400, messageUtils.getMessage("api.user.validate.password.confirmPassword", request));
        }

        user.setPassword(DigestUtils.sha256Hex(updateUserPwdReqDto.getNewPassword()));
        adminUserService.updateById(user);
        return R.ok();
    }


}
