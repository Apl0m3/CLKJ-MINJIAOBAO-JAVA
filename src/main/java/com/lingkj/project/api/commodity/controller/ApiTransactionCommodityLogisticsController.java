package com.lingkj.project.api.commodity.controller;


import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.utils.R;
import com.lingkj.project.transaction.entity.TransactionCommodityLogistics;
import com.lingkj.project.transaction.service.TransactionCommodityLogisticsService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("物流查询")
@Slf4j
@RestController
@RequestMapping("/api/commodity/logistics")
public class ApiTransactionCommodityLogisticsController {

    @Autowired
    private TransactionCommodityLogisticsService transactionCommodityLogisticsService;


    @GetMapping("/queryLogistices")
    public R  querylogistics(Long trCommodityId, Long recordId,Integer type){
        TransactionCommodityLogistics logisticsOne = transactionCommodityLogisticsService.getLogisticsOne( trCommodityId, recordId, type);
        return  R.ok().put("logistics",logisticsOne);
    }

}
