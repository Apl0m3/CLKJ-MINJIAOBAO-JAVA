package com.lingkj.project.api.user.controller;

import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.utils.R;
import com.lingkj.project.api.user.dto.ApiUserInvoiceDto;
import com.lingkj.project.user.entity.UserInvoice;
import com.lingkj.project.user.service.UserInvoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * ApiUserInvoiceController
 *
 * @author chen yongsong
 * @className ApiUserInvoiceController
 * @date 2019/9/30 16:38
 */
@Api("用户发票信息接口")
@RestController
@RequestMapping("/api/user/invoice")
public class ApiUserInvoiceController {

    @Autowired
    private UserInvoiceService userInvoiceService;

    @Login
    @ApiOperation(value = "查询用户发票信息接口")
    @GetMapping("/invoiceInfo")
    public R queryInvoice(@RequestAttribute("userId") Long userId) {
        ApiUserInvoiceDto userInvoiceDto = this.userInvoiceService.selectByUserId(userId);
        return R.ok().put("info", userInvoiceDto);
    }
    @Login
    @ApiOperation(value = "保存用户发票信息接口")
    @PostMapping("/saveOrUpdate")
    public R saveOrUpdate(@RequestBody UserInvoice userInvoice, @RequestAttribute("userId") Long userId) {
        if(userInvoice.getId()==null){
            userInvoice.setUserId(userId);
            userInvoice.setStatus(0);
            userInvoice.setCreateTime(new Date());
            userInvoiceService.save(userInvoice);
        }else {
            userInvoiceService.updateById(userInvoice);
        }
        return R.ok();
    }

}
