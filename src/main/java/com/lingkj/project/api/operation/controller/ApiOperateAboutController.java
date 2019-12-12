package com.lingkj.project.api.operation.controller;

import com.lingkj.common.utils.R;
import com.lingkj.project.api.operation.dto.WorkUsDto;
import com.lingkj.project.operation.entity.OperatePartner;
import com.lingkj.project.operation.service.OperatePartnerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author XXX <XXX@163.com>
 * @version V1.0.0
 * @description
 * @date 2019/10/23 17:49
 */

@Api
@Slf4j
@RestController
@RequestMapping("/api/operate/operateAbout/")
public class ApiOperateAboutController {

    @Autowired
    private OperatePartnerService operatePartnerService;


    @ApiOperation(value = "我们是谁接口 ")
    @GetMapping("/list")
    public R queryList(@RequestParam String name) {
        WorkUsDto workUsDto = operatePartnerService.queryList(name);
        return R.ok().put("aboutList", workUsDto);
    }
}
