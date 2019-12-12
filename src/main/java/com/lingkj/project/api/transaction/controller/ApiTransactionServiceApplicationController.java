package com.lingkj.project.api.transaction.controller;

import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.api.transaction.dto.*;
import com.lingkj.project.transaction.service.TransactionCommodityService;
import com.lingkj.project.transaction.service.TransactionRecordService;
import com.lingkj.project.transaction.service.TransactionServiceApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * ApiTransactionRecordController
 *
 * @author chen yongsong
 * @className ApiTransactionRecordController
 * @date 2019/9/19 11:22
 */
@Api("订单记录")
@Slf4j
@RestController
@RequestMapping("/api/transaction/service")
public class ApiTransactionServiceApplicationController {


    @Autowired
    private TransactionServiceApplicationService transactionServiceApplicationService;

    @Login
    @ApiOperation(value = "用户申请售后接口")
    @PostMapping("/afterSale")
    public R save(@RequestBody ApiTransactionServiceAndFileDto apiTransactionServiceAndFileDto,@RequestAttribute("userId")Long userId,HttpServletRequest request){
         transactionServiceApplicationService.saveService(apiTransactionServiceAndFileDto,userId,request);
        return  R.ok();
    }
}
