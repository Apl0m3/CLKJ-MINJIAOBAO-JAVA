package com.lingkj.project.api.operation.controller;

import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.R;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.project.commodity.entity.Commodity;
import com.lingkj.project.commodity.service.CommodityService;
import com.lingkj.project.user.entity.UserAgentRate;
import com.lingkj.project.operation.entity.OperateRate;
import com.lingkj.project.user.service.UserAgentRateService;
import com.lingkj.project.operation.service.OperateRateService;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.entity.UserAgentApplication;
import com.lingkj.project.user.service.AdminUserService;
import com.lingkj.project.user.service.UserAgentApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * ApiOperateRateController
 *
 * @author chen yongsong
 * @className ApiOperateRateController
 * @date 2019/10/10 10:08
 */
@Api(value = "费率")
@RestController
@RequestMapping("/api/operate/rate")
public class ApiOperateRateController {

    @Autowired
    private OperateRateService operateRateService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private UserAgentRateService userAgentRateService;
    @Autowired
    private UserAgentApplicationService userAgentApplicationService;
    @Autowired
    private MessageUtils messageUtils;

    @ApiOperation(value = "查询费率")
    @GetMapping("/queryRate")
    @Login
    public R queryRate(@RequestAttribute("userId") Long userId, Long commodityId, HttpServletRequest request) {
        User byId = adminUserService.getById(userId);
        UserAgentRate userAgentRate = null;
        if (byId.getUserRoleId().equals(User.member_role_agent)) {
            UserAgentApplication userAgentApplicationOne = userAgentApplicationService.getUserAgentApplicationOne(userId);
            Commodity commodity = commodityService.getById(commodityId);
            if(commodity!=null){
                userAgentRate = userAgentRateService.selectByUserIdAndAgentId(userId,commodity.getCommodityTypeId());
            }else {
                throw new RRException(messageUtils.getMessage("api.commodity.commodity.isEmpty", request),400);
            }
//            rateAgent = operateRateService.selectByType(2);
        }
        OperateRate rate = operateRateService.selectByType(1);

        return R.ok().put("rate", rate.getRate()).put("Discount", userAgentRate);

    }
}
