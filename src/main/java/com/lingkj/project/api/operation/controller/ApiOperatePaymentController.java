package com.lingkj.project.api.operation.controller;

import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.utils.R;
import com.lingkj.project.api.operation.dto.ApiOperatePaymentDto;
import com.lingkj.project.operation.service.PaymentMethodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ApiOperatePaymentController
 *
 * @author chen yongsong
 * @className ApiOperatePaymentController
 * @date 2019/10/10 17:43
 */
@Api(value = "支付方式接口")
@RestController
@RequestMapping("/api/operate/payment")
public class ApiOperatePaymentController {
    @Autowired
    private PaymentMethodService paymentMethodService;

    @ApiOperation(value = "支付方式列表")
    @Login
    @GetMapping("/queryPayment")
    public R queryPayment() {
        List<ApiOperatePaymentDto> list = paymentMethodService.selectApiList();
        return R.ok().put("list", list);
    }
}
