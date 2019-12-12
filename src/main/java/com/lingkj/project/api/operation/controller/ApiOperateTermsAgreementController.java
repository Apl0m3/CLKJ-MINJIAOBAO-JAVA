package com.lingkj.project.api.operation.controller;

import com.lingkj.common.utils.R;
import com.lingkj.project.api.operation.dto.CommonProblemDto;
import com.lingkj.project.api.operation.dto.OperateTermsAgreementDto;
import com.lingkj.project.operation.service.CommonProblemService;
import com.lingkj.project.operation.service.OperateTermsAgreementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ApiOperateAreasController
 *
 * @author chen yongsong
 * @className ApiOperateAreasController
 * @date 2019/9/23 10:53
 */
@Api
@Slf4j
@RestController
@RequestMapping("/api/operate/termsAgreement/")
public class ApiOperateTermsAgreementController {

    @Autowired
    OperateTermsAgreementService operateTermsAgreementService;

    @ApiOperation(value = "查询条款与协议,根据type获得列表 类型 1注册协议 2设计师申请协议 3代理商协议 4 供应商协议 ", httpMethod = "GET")
    @GetMapping("/list")
    public R queryList(Integer type) {
        List<OperateTermsAgreementDto> termsAgreementDtoLists = operateTermsAgreementService.getTermsAgreementDtoDtoLists(type);
        return R.ok().put("list", termsAgreementDtoLists);
    }
}
