package com.lingkj.project.api.operation.controller;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.operation.entity.ReturnReasons;
import com.lingkj.project.operation.service.ReturnReasonsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@Api(value = "退货原因")
@RestController
@RequestMapping("/api/operate/returnReasons")
public class ApiOperateReturnReasonsController {

    @Autowired
    private ReturnReasonsService returnReasonsService;
    @ApiOperation("退货原因列表")
    @GetMapping("/getList")
    public R returnReasonsList(){
        PageUtils pageUtils = returnReasonsService.queryPage(new HashMap<>());
        return R.ok().put("list",pageUtils);
    }
}
